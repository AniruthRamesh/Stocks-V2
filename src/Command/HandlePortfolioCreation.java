package Command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Model.Model;
import View.View;

public class HandlePortfolioCreation implements Command {
  Model model;
  View view;
  Scanner sc;

  public HandlePortfolioCreation(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    boolean portfolioOptionExit = false;
    boolean nameEntered = false;
    String name = "";
    List<List<String>> dataToAdd = new ArrayList<>();
    String currentDate = model.getCurrentDate();
    view.displayWarning();
    while (!portfolioOptionExit) {
      int ans;
      view.portfolioCreation();
      try {
        ans = sc.nextInt();
      } catch (InputMismatchException e) {
        view.displayOnlyIntegers();
        sc.next();
        continue;
      }

      switch (ans) {
        case 1:
          if (nameEntered && dataToAdd.size() != 0) {
            view.displayCannotEnterNameAgain();
            break;
          }
          name = handleGetPortfolioName();
          if (name.length() != 0) {
            nameEntered = true;
          }
          break;
        case 2:
          handleShowCompanies();
          break;
        case 3:
          if (!nameEntered) {
            view.displayCannotProceedWithoutName();
            break;
          }
          List<String> newData = handleAddACompanyStock();
          if (newData == null) {
            break;
          }
          //check if the company name exists
          if (model.checkIfCompanyExists(newData.get(0))) {
            dataToAdd.add(newData);
          } else {
            view.displayNoSuchCompanyExist();
          }
          break;
        case 4:
          portfolioOptionExit = true;
          if (dataToAdd.size() != 0) {
            addPortfolioData(dataToAdd, name, currentDate);
            model.savePortfolio();
            break;
          } else {
            name = "";
            nameEntered = false;
          }
          //sanity check
          break;
        default:
          view.displaySwitchCaseDefault();
          break;
      }
    }
    return model;
  }

  /**
   * It asks the user to enter a name for the portfolio, and if the name is empty or if the
   * portfolio already contains a portfolio with the same name, it asks the user to enter a name
   * again.
   *
   * @return The name of the portfolio.
   */
  public String handleGetPortfolioName() {
    String name;
    view.displayEnterNameForPortfolio();
    //check if we need to dd try catch
    sc.nextLine();
    name = sc.nextLine();

    if (name.length() == 0) {
      view.displayNameCannotBeEmpty();
      return "";
    }
    boolean alreadyContainsTheName = model.hasAnotherPortfolioWithSameName(name);
    if (alreadyContainsTheName) {
      view.displayAlreadyHaveAnotherPortfolioWithSameName();
      return "";
    }
    return name;
  }

  /**
   * This function displays the list of stock companies in the stock market in our project.
   */
  public void handleShowCompanies() {
    List<String> stockCompanyName = model.getStockCompanyName();
    for (int i = 0; i < stockCompanyName.size(); i++) {
      view.displayCompanies(stockCompanyName.get(i), i + 1);
    }
    view.displayEmptyLine();
  }

  /**
   * It asks the user for a company name and a number of stocks, and returns a list of strings
   * containing the company name and the number of stocks.
   *
   * @return A list of strings.
   */
  public List<String> handleAddACompanyStock() {
    sc.nextLine();
    String companyName;
    double numberOfStocks = 0;
    view.askForCompanyName();
    companyName = sc.nextLine();
    if (!model.checkIfCompanyExists(companyName)) {
      return null;
    }
    view.askForNumberOfStocks();
    try {
      numberOfStocks = sc.nextDouble();
      numberOfStocks = model.helper(numberOfStocks);
    } catch (InputMismatchException e) {
      view.displayOnlyIntegers();
      sc.next();
    }
    List<String> dataToAdd = List.of(companyName, new BigDecimal(numberOfStocks)
            .toPlainString());
    if (dataToAdd.get(0).length() == 0) {
      view.displayNameCannotBeEmpty();
      dataToAdd = null;
    } else {
      double stockNumber = Double.parseDouble(dataToAdd.get(1));
      if (stockNumber <= 0) {
        view.displayStockNumberCannotBeLessThanOrEqualToZero();
        dataToAdd = null;
      }
    }
    return dataToAdd;
  }

  /**
   * This function takes in a list of lists of strings, a string, and a string, and adds the data to
   * the portfolio.
   *
   * @param dataToAdd a list of lists of strings, where each list of strings is a row of data.
   * @param name The name of the portfolio
   * @param currentDate the date of the portfolio added
   */
  public void addPortfolioData(List<List<String>> dataToAdd, String name, String currentDate) {
    model.addsFinalDataToPortfolio(dataToAdd, name, currentDate);
  }
}
