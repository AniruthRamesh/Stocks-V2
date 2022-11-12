package Command;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import Model.Model;
import View.View;

/**
 * This class handles the total stock value display command.
 */
public class HandleTotalStockValueDisplay implements Command {
  Model model;
  View view;
  Scanner sc;

  public HandleTotalStockValueDisplay(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    boolean continueLoop = false;
    String name = "";
    int num = model.getPortfolioSize();
    if (num == 0) {
      view.displayNoPortfolio();
    } else {
      while (!continueLoop) {
        int choice;
        view.displayStockValueMenu();
        try {
          choice = sc.nextInt();
        } catch (InputMismatchException e) {
          view.displayOnlyIntegers();
          sc.next();
          continue;
        }
        switch (choice) {
          case 1:
            if (name.length() != 0) {
              view.displayPortfolioNameAlreadyEntered();
              break;
            }
            //check if we need to add try catch
            view.displayEnterNameForPortfolio();
            sc.nextLine();
            name = sc.nextLine();


            if (!model.portfolioContainsCertainKey(name)) {
              view.displayNoPortfolio();
              name = "";
            }
            break;
          case 2:
            if (name.length() == 0) {
              view.displayNameCannotBeEmpty();
              break;
            }
            String result = handleTotalStockOnCurrentDate(name);
            if (Objects.equals(result, "Failure")) {
              name = "";
              view.displayNoSuchPortfolio();
            }
            break;
          case 3:
            if (name.length() == 0) {
              view.displayNameCannotBeEmpty();
              break;
            }
            handleTotalStockOnDifferentDate(name);
            break;
          case 4:
            continueLoop = true;
            break;
          default:
            view.displaySwitchCaseDefault();
            break;
        }
      }
    }
    return model;
  }


  public String handleTotalStockOnCurrentDate(String portfolioName) {
    double totalValue = model.getTotalStockValue(portfolioName, model.getCurrentDate());
    if (totalValue == -1) {
      return "Failure";
    }
    view.displayTotalStockValue(portfolioName, model.getCurrentDate(),
            new BigDecimal(totalValue)
                    .toPlainString());
    return "Success";
  }

  public void handleTotalStockOnDifferentDate(String portfolioName) {
    int choice;
    view.displaySelectDateOption(model.getCurrentDate());
    try {
      choice = sc.nextInt();
    } catch (InputMismatchException e) {
      view.displayOnlyIntegers();
      sc.next();
      return;
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

      String dateWishToChange = model.makeStringDate(day, month, year);

      boolean checker1 = model.isValidDate(dateWishToChange);
      if (checker1) {
        //check if date exist
        boolean checker = model.setContainsGivenDate(dateWishToChange);
        if (checker) {
          double amount = model.getTotalStockValue(portfolioName, dateWishToChange);
          view.displayTotalStockValue(portfolioName, dateWishToChange, new BigDecimal(amount)
                  .toPlainString());
        } else {
          view.displayNoStockDataForGivenDate();
        }
      } else {
        view.displayDateIsNotValid();
      }
    } else if (choice == 2) {
      //
    } else {
      view.displaySwitchCaseDefault();
    }
  }
}
