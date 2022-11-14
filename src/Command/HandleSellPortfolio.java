package Command;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Model.Model;
import View.View;

public class HandleSellPortfolio implements Command {
  Model model;
  View view;
  Scanner sc;

  public HandleSellPortfolio(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    boolean portfolioOptionExit = false;
    boolean nameEntered = false;
    String name = "";

    while (!portfolioOptionExit) {
      int ans;
      view.displayPortfolioNameMenu();
      ans = sc.nextInt();

      switch (ans) {
        case 1:
          name = handleGetPortfolioName();
          if (name.length() != 0) {
            nameEntered = true;
          }
          if (nameEntered) {
            handleSellPortfolioOptions(name);
            portfolioOptionExit = true;
            break;
          }
          break;
        case 2:
          portfolioOptionExit = true;
          break;
        default:
          view.displaySwitchCaseDefault();
          break;
      }
    }
    return model;
  }

  public String handleGetPortfolioName() {
    String name;
    view.displayEnterNameForPortfolio();
    //check if we need to add try catch
    sc.nextLine();
    name = sc.nextLine();

    if (name.length() == 0) {
      view.displayNameCannotBeEmpty();
      name = "";
    }
    if (!model.flexiblePortfolioContainsCertainKey(name)) {
      view.displayNoPortfolio();
      name = "";
    }
    return name;
  }

  public void handleSellPortfolioOptions(String portfolioName) {
    String currentDate = model.getCurrentDate();
    boolean optionExit = false;

    while (!optionExit) {
      int ans;
      view.displayAddCompanyStockMenu();
      ans = sc.nextInt();

      switch (ans) {
        case 1:
          String name;
          view.askForTickerSymbol();
          sc.nextLine();
          name = sc.nextLine();
          if (handleEnterTickerSymbol(name)) {
            handleDateSelection(portfolioName);
          } else {
            view.displayCompanyTickerSymbolIsNotValid();
          }
          break;
        case 2:
          optionExit = true;
          break;
        default:
          view.displaySwitchCaseDefault();
          break;
      }
    }


  }

  public boolean handleEnterTickerSymbol(String name) {

    return model.checkIfTickerExists(name);
  }

  public void handleDateSelection(String portfolioName) {
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
          System.out.println("sssssssssssssssssssssssssss");
          List<List<String>> vv =model.getParticularFlexiblePortfolio(portfolioName);
          System.out.println(vv);
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
