package Command;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Model.Model;
import View.View;

public class HandleUploadFile implements Command {
  final Model model;
  final View view;
  final Scanner sc;

  public HandleUploadFile(Model model, View view, Scanner sc) {
    this.model = model;
    this.view = view;
    this.sc = sc;
  }

  @Override
  public Model execute() {
    view.askForFilePath();
    sc.nextLine();
    String filepath = sc.nextLine();
    String isFileReadSuccessFull = model.readFromFile(filepath);
    if (isFileReadSuccessFull.equals("Failure")) {
      view.displayTheFilePathDoesNotExist();
      return model;
    }
    Map<String, List<List<String>>> parsedPortfolio = model.parseJson(isFileReadSuccessFull);
    if (parsedPortfolio == null) {
      view.displayDataNotInProperFormat();
      return model;
    }
    boolean checker = model.checkParsedPortfolio(parsedPortfolio);
    if (!checker) {
      view.displayDataNotInProperFormat();
      return model;
    }
    model.setPortfolio(parsedPortfolio);
    model.savePortfolio();
    return model;
  }
}
