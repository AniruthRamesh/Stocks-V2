package Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Model.Model;
import View.View;

/**
 * It displays the composition of the portfolio.
 */
public class HandlePortfolioComposition implements Command {
  final Model model;
  final View view;
  final Scanner sc;

  public HandlePortfolioComposition(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;

  }

  @Override
  public Model execute() {
    Map<String, List<List<String>>> portfolio = model.getInflexiblePortfolio();

    if (portfolio.size() == 0) {
      view.displayPortfolioIsEmpty();
    } else {
      ArrayList<String> portfolioNames = model.getPortfolioKeys();
      for (String curr : portfolioNames) {
        view.displayPortfolioName(curr);
        List<List<String>> contents = portfolio.get(curr);
        view.displayTableLayout();
        for (List<String> content : contents) {
          for (String s : content) {
            view.displayContentsOfPortfolio(s);
          }
          view.displayEmptyLine();
        }
      }
      view.displayEmptyLine();
    }
    return model;
  }
}
