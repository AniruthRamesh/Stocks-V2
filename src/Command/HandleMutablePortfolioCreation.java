package Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Model.Model;
import View.View;

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
          handleAddApiCompanyStock(name);
          break;
        case 4:
          initialOptions = true;
          model.saveFlexiblePortfolios();
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

  void handleAddApiCompanyStock(String portfolioName){
    boolean initialOptions = false;
    int choice;
    HashMap<String, String> stockData;
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
            stockData = model.convertingStringToHashMap(mission);

            model.addStockDataToFlexibleList(stockData);
            int num = model.getApiStockDataSize();
            model.putCompanyNameInTickerFinder(companyName,num);
          }
          else{
            int ind = model.getTickerFinder().get(companyName);
            stockData = model.getApiStockData().get(ind);
          }
          DateHelper date = new DateHelper(view,model,sc);
          String dateVal = date.helper();
          if(dateVal.length()==0){
            continue;
          }
          view.askForNumberOfStocks();
          Double numberOfStocks = 0.0;
          try{
            numberOfStocks = sc.nextDouble();
          }
          catch (InputMismatchException e){
            view.displayOnlyIntegers();
            sc.next();
            continue;
          }
          if(numberOfStocks<=0){
            view.displayStockNumberCannotBeLessThanOrEqualToZero();
            break;
          }
          numberOfStocks = model.helper(numberOfStocks);
          if(stockData.containsKey(dateVal)){
            Map<String, List<List<String>>> flexiblePortfolio = model.getFlexiblePortfolio();
            if(flexiblePortfolio.containsKey(portfolioName)){
              List<List<String>> existing = new ArrayList<>();
              List<List<String>> anotherExisting = flexiblePortfolio.get(portfolioName);
              for(int i=0;i<anotherExisting.size();i++){
                existing.add(anotherExisting.get(i));
              }
              existing.add(List.of(companyName,
                      String.valueOf(numberOfStocks),dateVal));
              model.setterForFlexiblePortfolio(portfolioName,existing);
            }
            else{
              model.setterForFlexiblePortfolio(portfolioName,List.of(List.of(companyName,
                      String.valueOf(numberOfStocks),dateVal)));
            }
          }
          else{
           view.displayNoStockDataForGivenDate();
          }
          break;
        case 2:
          initialOptions = true;
          break;
        default:
          view.displaySwitchCaseDefault();
          break;
      }
    }
  }
}
