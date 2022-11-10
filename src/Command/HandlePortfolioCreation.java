package Command;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Model.Model;
import View.View;

public class HandlePortfolioCreation implements Command{
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

  public void handleShowCompanies() {
    List<String> stockCompanyName = model.getStockCompanyName();
    for (int i = 0; i < stockCompanyName.size(); i++) {
      view.displayCompanies(stockCompanyName.get(i), i + 1);
    }
    view.displayEmptyLine();
  }

  public List<String> handleAddACompanyStock() {
    sc.nextLine();
    String companyName;
    double numberOfStocks = 0;
    view.askForCompanyName();
    companyName = sc.nextLine();
    if(!model.checkIfCompanyExists(companyName)){
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

  public void addPortfolioData(List<List<String>> dataToAdd, String name, String currentDate) {
    model.addsFinalDataToPortfolio(dataToAdd, name, currentDate);
  }
}
