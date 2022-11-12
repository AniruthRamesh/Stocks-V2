package Command;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import Model.Model;
import View.View;

/**
 * It handles the creation of a mutable portfolio. Stocks can be added or deleted from this
 * portfolio.
 */
public class HandleMutablePortfolioCreation implements Command {
  Model model;
  View view;
  Scanner sc;

  public HandleMutablePortfolioCreation(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    boolean initialOptions = false;
    String name = "";
    while(!initialOptions){
      view.displayCreateFlexiblePortfolioMenu();
      int choice;
      try{
        choice = sc.nextInt();
      }
      catch (InputMismatchException e){
        view.displayOnlyIntegers();
        sc.next();
        continue;
      }
      switch (choice){
        case 1:
          name = handleFlexiblePortfolioMenu();
          //System.out.println(name);
          break;
        case 2:
          name = handleFlexiblePortfolioMenu();
          boolean checker = false;
          checker = model.flexiblePortfolioContainsCertainKey(name);
          if(!checker&&name.length()!=0){
            name = "";
            view.displayNoSuchPortfolio();
          }
          break;
        case 3:
          if(name.length()==0){
            view.displayNameCannotBeEmpty();
            break;
          }
          handleAddApiCompanyStock();
          break;
        case 4:
          initialOptions = true;
          //save immutable portfolio (add this in model Interface also)
          break;
        default:
          view.displaySwitchCaseDefault();
          break;
      }

    }

    return model;
  }

  String handleFlexiblePortfolioMenu(){
    String name = "";
    view.displayPortfolioNameMenu();
    int choice;
    try{
      choice = sc.nextInt();
    }
    catch (InputMismatchException e){
      view.displayOnlyIntegers();
      sc.next();
      return "";
    }
    switch (choice){
      case 1:
        sc.nextLine();
        name = sc.nextLine();
        break;
      case 2:
        break;
      default:
        view.displaySwitchCaseDefault();
        break;
    }
    return name;
  }

  void handleAddApiCompanyStock(){
    boolean initialOptions = false;
    int choice;
    while(!initialOptions){
      view.displayAddCompanyStockMenu();
      try{
        choice = sc.nextInt();
      }
      catch (InputMismatchException e){
        view.displayOnlyIntegers();
        sc.next();
        continue;
      }
      switch (choice){
        case 1:
          sc.nextLine();
          String companyName = sc.nextLine();
          if(!model.checkIfTickerExists(companyName)){
            String mission = model.addApiCompanyStockData(companyName);
            if(mission.equals("failure")){
              view.displayCompanyTickerSymbolIsNotValid();
              break;
            }
            HashMap<String, String> stockData = model.convertingStringToHashMap(mission);
            model.addStockDataToFlexibleList(stockData);
            int num = model.getApiStockDataSize();
            model.putCompanyNameInTickerFinder(companyName,num);
          }

          break;
        case 2:
          initialOptions = true;
          break;
      }
    }
  }
}
