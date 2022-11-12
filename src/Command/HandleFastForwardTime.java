package Command;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import Model.Model;
import View.View;

/**
 * It takes in a date from the user, checks if it's valid, and if it is, it sets the current date to
 * that date and show value of stocks on that date.
 */
public class HandleFastForwardTime implements Command {
  final Model model;
  final View view;
  final Scanner sc;

  public HandleFastForwardTime(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    boolean continueLoop = false;
    while (!continueLoop) {
      int choice;
      view.displaySelectDateOption(model.getCurrentDate());
      try {
        choice = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        continue;
      }
      switch (choice) {
        case 1:
          handleDateSelection();
          break;
        case 2:
          continueLoop = true;
          break;
        default:
          view.displaySwitchCaseDefault();
          break;
      }
    }
    return model;
  }

  /**
   * It takes in a date from the user, checks if it's valid, and if it is, it sets the current
   * date to that date.
   */
  public void handleDateSelection() {
    LocalDate currentDate = model.localDateParser(model.getCurrentDate());

    String dateChange;
    int day;
    int month;
    int year;
    view.askForDayOfTheMonth();
    try {
      day = sc.nextInt();
    } catch (InputMismatchException e) {
      view.displayOnlyIntegers();
      sc.next();
      return;
    }
    if (day > 31 || day == 0) {
      view.displayEnterValidDetailsForDate();
      return;
    }
    view.askForMonth();
    try {
      month = sc.nextInt();
    } catch (InputMismatchException e) {
      view.displayOnlyIntegers();
      sc.next();
      return;
    }
    if (month > 12 || month == 0) {
      view.displayEnterValidDetailsForDate();
      return;
    }
    view.askForYear();
    try {
      year = sc.nextInt();
    } catch (InputMismatchException e) {
      view.displayOnlyIntegers();
      sc.next();
      return;
    }
    if (year > 2022 || year < 2001) {
      view.displayEnterValidDetailsForDate();
      return;
    }

    dateChange = model.makeStringDate(day, month, year);

    if (dateChange.length() != 0) {
      boolean checker = model.isValidDate(dateChange);
      if (checker) {
        boolean anotherChecker = model.setContainsGivenDate(dateChange);
        if (anotherChecker) {
          currentDate = LocalDate.parse(dateChange);
          model.setCurrentDate(currentDate.toString());
        } else {
          view.displayNoStockDataForGivenDate();
        }
      } else {
        view.displayDateIsNotValid();
      }
    }
  }
}
