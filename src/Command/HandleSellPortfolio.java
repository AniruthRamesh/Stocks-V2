package Command;

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
    String currentDate = model.getCurrentDate();


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
          if(nameEntered) {
            handleSellPortfolioOptions();
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
    if (!model.portfolioContainsCertainKey(name)) {
      view.displayNoPortfolio();
      name = "";
    }
    return name;
  }
  public void handleSellPortfolioOptions(){
    view.displayAddCompanyStockMenu();
  }
}
