package Command;

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
    while(!initialOptions){
      //view.displayCreateFlexiblePortfolioMenu();
      int choice;
      try{
        choice = sc.nextInt();
      }
      catch (InputMismatchException e){
        view.displayOnlyIntegers();
        sc.next();
        continue;
      }
      String name = ""; //change the method signature of case 1 and 2.
      switch (choice){
        case 1:
          handleFlexiblePortfolioMenu();
          break;
        case 2:
          handleEditPortfolio();
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
      }

    }

    return model;
  }

  void handleFlexiblePortfolioMenu(){

  }

  void handleEditPortfolio(){

  }

  void handleAddApiCompanyStock(){

  }
}
