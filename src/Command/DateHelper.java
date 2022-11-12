package Command;

import java.util.InputMismatchException;
import java.util.Scanner;

import Model.Model;
import View.View;

public class DateHelper {
  View view;
  Model model;
  Scanner sc;

  public DateHelper(View view, Model model, Scanner sc) {
    this.model = model;
    this.sc = sc;
    this.view = view;
  }

  String helper() {
    view.dateSelectionMenu();
    String date = "";
    int choice;
    try {
      choice = sc.nextInt();
    } catch (InputMismatchException e) {
      view.displayOnlyIntegers();
      sc.next();
      return "";
    }
    if (choice == 1) {
      int day;
      int month;
      int year;
      view.askForDayOfTheMonth();
      try {
        day = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        return "";
      }
      if (day > 31 || day == 0) {
        view.displayEnterValidDetailsForDate();
        return "";
      }
      view.askForMonth();
      try {
        month = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        return "";
      }
      if (month > 12 || month == 0) {
        view.displayEnterValidDetailsForDate();
        return "";
      }
      view.askForYear();
      try {
        year = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        return "";
      }
      if (year > 2022 || year < 2001) {
        view.displayEnterValidDetailsForDate();
        return "";
      }

      String dateWishToChange = model.makeStringDate(day, month, year);
      if (model.isValidDate(dateWishToChange)) {
        date = dateWishToChange;
      }
    } else if (choice == 2) {
      //
    } else {
      view.displaySwitchCaseDefault();
    }

    return date;
  }
}
