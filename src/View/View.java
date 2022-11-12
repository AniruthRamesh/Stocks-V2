package View;

import java.io.PrintStream;

/**
 * This the view class for Stocks.
 * It represents the visualization of the data that model contains.
 */
public class View {
  private final PrintStream out;

  /**
   * Constructor for the View.View Class.
   *
   * @param out PrintStream object, required way ex. main method sends System.out.
   */
  public View(PrintStream out) {
    this.out = out;
  }

  /**
   * This function displays the option number and the string that is passed as a parameter.
   *
   * @param optionNumber  This is the number of the option that will be displayed.
   * @param needToDisplay This is the string that you want to display.
   */
  public void displayWhatIsInParameter(int optionNumber, String needToDisplay) {
    this.out.println(optionNumber + "." + " " + needToDisplay);
  }

  /**
   * Displays only integers are valid values.
   */
  public void displayOnlyIntegers() {
    this.out.println("Enter only valid Integers");
    this.out.println();
  }

  /**
   * Tells the user to enter a valid option.
   */
  public void displaySwitchCaseDefault() {
    this.out.println("Enter an valid option.");
    this.out.println();
  }

  /**
   * Displays options menu for creating a new Portfolio.
   */
  public void portfolioCreation() {
    this.out.println("1.Give a name for the current Portfolio");
    this.out.println("2.Show list of Companies");
    this.out.println("3.Add a company Stock");
    this.out.println("4.Exit");
    this.out.println();
  }


  /**
   * Displays the name of companies with number of stock.
   *
   * @param stockCompanyName company name
   * @param number           number of stocks.
   */
  public void displayCompanies(String stockCompanyName, int number) {
    this.out.println(number + "." + stockCompanyName);
  }

  /**
   * Display an empty line.
   */
  public void displayEmptyLine() {
    this.out.println();
  }


  /**
   * Display message that user cant change the name of portfolio.
   */
  public void displayCannotEnterNameAgain() {
    this.out.println("You already entered Name for this current Portfolio,cannot change Name.");
    this.out.println();
  }

  /**
   * Display message that name is mandatory for portfolio.
   */
  public void displayCannotProceedWithoutName() {
    this.out.println("Please Enter a name for the portfolio first.");
    this.out.println();
  }

  /**
   * Asks the user to enter name for portfolio.
   */
  public void displayEnterNameForPortfolio() {
    this.out.println("Enter Name of the Portfolio:");
    this.out.println();
  }

  /**
   * Displays that name cannot be empty.
   */
  public void displayNameCannotBeEmpty() {
    this.out.println("Name cannot be empty. Enter at least one character");
    this.out.println();
  }

  /**
   * Tells the user that he has created another portfolio with same name.
   */
  public void displayAlreadyHaveAnotherPortfolioWithSameName() {
    this.out.println("You already have another Portfolio with the same name.");
    this.out.println();
  }

  /**
   * Asks the user to enter company name.
   */
  public void askForCompanyName() {
    this.out.println("Enter the company name:");
    this.out.println();
  }


  /**
   * Display message for user to enter number of stocks of company.
   */
  public void askForNumberOfStocks() {
    this.out.println("Enter number of stocks:");
    this.out.println();
  }

  /**
   * Display message for the value of stock cannot be less than or equal 0.
   */
  public void displayStockNumberCannotBeLessThanOrEqualToZero() {
    this.out.println("Stock value cannot be less than or equal to zero. Please enter a valid" +
            "number");
    this.out.println();
  }

  /**
   * Displays message that no such company exists.
   */
  public void displayNoSuchCompanyExist() {
    this.out.println("There is no such company, enter using the list of companies available");
    this.out.println();
  }

  /**
   * Displays current portfolio name.
   *
   * @param currPortfolio name of current portfolio
   */
  public void displayPortfolioName(String currPortfolio) {
    this.out.println("Portfolio: " + currPortfolio);
  }

  /**
   * Displays data in table layout.
   */
  public void displayTableLayout() {
    this.out.println("Company\t\tStock-Numbers\t\tDate-obtained");
  }

  /**
   * Displays content of portfolio.
   *
   * @param contents content in string form.
   */
  public void displayContentsOfPortfolio(String contents) {
    this.out.print(contents + "\t\t\t");
  }

  /**
   * Display message if portfolio is empty.
   */
  public void displayPortfolioIsEmpty() {
    this.out.println("Portfolio is empty, cannot view composition");
    this.out.println();
  }

  /**
   * Display options of select date.
   *
   * @param currentDate Date entered.
   */
  public void displaySelectDateOption(String currentDate) {
    this.out.println("Current Date: " + currentDate);
    this.out.println("1.Select Date");
    this.out.println("2.Exit");

    this.out.println();
  }

  /**
   * This function asks the user to enter the day of the month in number.
   */
  public void askForDayOfTheMonth() {
    this.out.println("Enter the day of the month in number(1-31):");
  }

  /**
   * Display message to input month.
   */
  public void askForMonth() {
    this.out.println("Enter the month in number(1-12):");
  }

  /**
   * Display message to input year.
   */
  public void askForYear() {
    this.out.println("Enter the year you wish to jump to(2001 - 2022):");
  }

  /**
   * Display message to input valid inputs.
   */
  public void displayEnterValidDetailsForDate() {
    this.out.println("Enter valid details.");
  }

  /**
   * Display message to show that entered date is invalid.
   */
  public void displayDateIsNotValid() {
    this.out.println("The entered date is not a valid date.");
    this.out.println();
  }

  /**
   * Display message options for stock values.
   */
  public void displayStockValueMenu() {
    this.out.println("1.Enter the portfolio name to get value for");
    this.out.println("2.Get total Stock Value on current Date");
    this.out.println("3.Change the date and get Stock Value");
    this.out.println("4.Exit");
    this.out.println();
  }

  /**
   * Displays if the user has already entered name for portfolio.
   */
  public void displayPortfolioNameAlreadyEntered() {
    this.out.println("You already entered name for portfolio. Choose option 2 or 3 or exit.");
    this.out.println();
  }

  /**
   * Displays data of a particular portfolio.
   *
   * @param portfolioName name of the portfolio
   * @param currentDate   date of creation of portfolio
   * @param totalValue    total value of stocks
   */

  public void displayTotalStockValue(String portfolioName, String currentDate, String totalValue) {
    this.out.println("Portfolio: " + portfolioName);
    this.out.println("Date: " + currentDate);
    this.out.println("Total Value: " + totalValue);
    this.out.println();
  }

  /**
   * Displays message if no portfolio exists for the name entered.
   */
  public void displayNoSuchPortfolio() {
    this.out.println("No such portfolio exists, enter name again");
    this.out.println();
  }

  /**
   * Display message to create portfolio and then check value.
   */
  public void displayNoPortfolio() {
    this.out.println("Create portfolio first.");
    this.out.println();
  }

  /**
   * Display is no stock is present for the entered day.
   */
  public void displayNoStockDataForGivenDate() {
    this.out.println("There is no stock data for given date, please enter another date");
    this.out.println();
  }


  /**
   * This function prints out the path of the file where the portfolio is saved.
   *
   * @param path The path where the portfolio is saved.
   */
  void displayAllPortfolioSaved(String path) {
    this.out.println("All the portfolio created have been saved and It is in the location: " + path);
    this.out.println();
  }


  /**
   * This function asks the user for the file path.
   */
  public void askForFilePath() {
    this.out.println("Enter the file path:");
    this.out.println();
  }

  /**
   * This function displays a message to the user that the file path does not exist.
   */
  public void displayTheFilePathDoesNotExist() {
    this.out.println("The file path does not exist, Please Enter a valid path");
    this.out.println();
  }


  /**
   * This function prints out a message to the user that the portfolio provided in the text file
   * is not
   * in proper format.
   */
  public void displayDataNotInProperFormat() {
    this.out.println("The portfolio provided in the text file is not in proper format," + "please"
            + " look at the documentation");
    this.out.println();
  }

  /**
   * Displays a method to the user saying there is a new version released.
   */
  public void displayWarning() {
    this.out.println("Warning: You are using older feature of Stocks");
    this.out.println("If you wish to use Mutable portfolios, have options to use multiple " +
            "companies, choose the newer version");
  }

  /**
   * It prints out the menu for the user to enter the name of the portfolio.
   */
  public void displayPortfolioNameMenu() {
    this.out.println("1. Enter the name of the portfolio");
    this.out.println("2. Exit");
    this.out.println();
  }

  /**
   * It prints out the menu for creating a flexible portfolio.
   */
  public void displayCreateFlexiblePortfolioMenu() {
    this.out.println("1. Create new flexible portfolio");
    this.out.println("2. Edit the existing flexible portfolio");
    this.out.println("3. Add the company's stock");
    this.out.println("4. Exit");
    this.out.println();
  }

  /**
   * This function displays the menu for adding a company stock to the portfolio.
   */
  public void displayAddCompanyStockMenu() {
    this.out.println("1. Enter the ticker symbol of the desired company.");
    this.out.println("2. Exit");
    this.out.println();
  }

  /**
   * This function displays a message to the user that the company associated with the ticker
   * symbol they inputted doesn't exist.
   */
  public void displayCompanyTickerSymbolIsNotValid() {
    this.out.println("Company associated with this ticker symbol doesn't exists. Please input " +
            "the" + " correct ticker symbol.");
    this.out.println();
  }

  // Asking the user to enter the ticker symbol of the company.
  public void askForTickerSymbol() {
    this.out.println("Enter ticker symbol for the company:");
    this.out.println();
  }
  public void dateSelectionMenu(){
    this.out.println("1. Enter the Date");
    this.out.println("2. Exit");
  }
}

