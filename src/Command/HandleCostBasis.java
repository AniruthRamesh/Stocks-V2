package Command;

import java.util.Scanner;

import Model.Model;
import View.View;

public class HandleCostBasis implements Command {
  Model model;
  View view;
  Scanner sc;
  public HandleCostBasis(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }
  @Override
  public Model execute() {
    return model;
  }
}
