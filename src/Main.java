import Controller.Controller;
import Controller.ControllerImpl;
import Model.Model;
import Model.ModelImpl;
import View.View;

/**
 * This is the class which contains main method. Creates an object for view with PrintStream
 * as System.out. Creates an object for Model.Model Interface and provides all these objects
 * as parameter to controller. Calls the controller's start method, which is the starting point
 * in the application.
 */
public class Main {
  /**
   * It creates a new Model.Model, View.View, and Controller.Controller, and then starts the controller.
   *
   * @param args accepts a single argument of type String array
   */
  public static void main(String[] args) {
    Model model = new ModelImpl();
    View view = new View(System.out);
    Controller controller = new ControllerImpl(model, view, System.in);
    controller.start();

    //change model.getContentsFromFile when building jar
    //handle saving uploaded files(while uploading ask if its immutable or not)

    //show list of portfolios shows both flexible and inflexible
  }

}
