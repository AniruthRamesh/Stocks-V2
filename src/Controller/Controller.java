package Controller;

/**
 * This is an interface for controller for stock.
 * It controls the data flow into model object and updates the view whenever data changes.
 * It keeps view and model separate.
 */
public interface Controller {
  /**
   * This method acts as a starting point for the application, it tells the view to display
   * initial options available for the user, based on the input value, using Command.Command design pattern
   * rest of the function is carried out.
   */
  void start();
}
