package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is an interface for stock model.
 * Contains all the data-related logic that the user requires.
 */
public interface Model {

  /**
   * This method returns a list of Strings which is used for displaying Inital options that a user
   * sees when using the applications. If new Features are added, Editing this list alone is enough.
   *
   * @return List of Strings, contains all the options.
   */
  List<String> getInitialOptions();


  /**
   * This function returns the current date.
   *
   * @return A string of the current date.
   */
  String getCurrentDate();

  /**
   * Sets the current date.
   *
   * @param currentDate The current date in the format of "yyyy-MM-dd"
   */
  void setCurrentDate(String currentDate);


  /**
   * Given a portfolio, return a map of the portfolio's holdings, where the key is the company
   * name and the value is a list of lists, where each inner list is a list of the holding's
   * attributes.
   *
   * @return A HashMap with a String as the key and a List of List of Strings as the value.
   */
  Map<String, List<List<String>>> getInflexiblePortfolio();

  /**
   * It sets the portfolio of the user.
   *
   * @param inflexiblePortfolio A HashMap of the portfolio. The key is the name of the portfolio,
   *                            and the
   *                            value is a list of lists.
   */
  void setInflexiblePortfolio(Map<String, List<List<String>>> inflexiblePortfolio);


  /**
   * This function returns a list of strings that represent the company names of the stocks in the
   * database.
   *
   * @return A list of all the company names in the database.
   */
  List<String> getStockCompanyName();

  /**
   * This function reads the contents of a file and stores it in a string.
   */
  void getContentsFromFile();

  /**
   * It takes a string of the form "key1=value1,key2=value2,key3=value3" and returns a HashMap with
   * the keys and values.
   *
   * @param data The string that you want to convert to a HashMap.
   * @return A HashMap of String and String
   */
  HashMap<String, String> convertingStringToHashMap(String data);

  /**
   * Returns true if there is another portfolio with the same name as the one passed in.
   *
   * @param name The name of the portfolio.
   * @return A boolean value.
   */
  boolean hasAnotherPortfolioWithSameName(String name);

  /**
   * This function takes in a list of lists of strings, a string, and a string, and adds the data to
   * the portfolio.
   *
   * @param dataToAdd   A list of lists of strings. Each list of strings represents a row of data.
   * @param name        The name of the portfolio you want to add data to.
   * @param currentDate The current date in the format of "MM/dd/yyyy"
   */
  void addsFinalDataToPortfolio(List<List<String>> dataToAdd, String name, String currentDate);

  /**
   * Check if a company exists in the database.
   *
   * @param name The name of the company
   * @return boolean
   */
  boolean checkIfCompanyExists(String name);

  /**
   * Given a string, return true if it's a valid date in the format YYYY-MM-DD,
   * otherwise return false.
   *
   * @param date a string representing a date in the format "YYYY-MM-DD"
   * @return A boolean value.
   */
  boolean isValidDate(String date);

  /**
   * "Given a portfolio name and a date, return the total value of all the stocks in the
   * portfolio on that date."
   * The function is a bit more complicated than the previous ones. It takes two parameters: a
   * portfolio name and a date. It returns a double, which is the total value of all the stocks
   * in the portfolio on that date
   *
   * @param portfolioName The name of the portfolio for which the total stock value is to be
   *                      calculated.
   * @param currentDate   The date for which the total stock value is to be calculated.
   * @return The total value of the stocks in the portfolio.
   */
  double getTotalStockValue(String portfolioName, String currentDate);

  /**
   * Gets the size of portfolio.
   *
   * @return the size of portfolio
   */
  int getPortfolioSize();

  /**
   * Returns true if the portfolio contains a stock with the given name.
   *
   * @param name The name of the portfolio you want to check.
   * @return A boolean value.
   */
  boolean portfolioContainsCertainKey(String name);

  /**
   * Given a day, month, and year, return a string representing the date in the format 'dd/mm/yyyy'.
   *
   * @param day   The day of the month.
   * @param month The month of the year (1-12)
   * @param year  The year of the date.
   * @return A string of the date in the format of dd/mm/yyyy
   */
  String makeStringDate(int day, int month, int year);

  /**
   * This function creates a list of particular company's dates from the start date to the end date.
   */
  void makeListOfDates();

  /**
   * Given a date, return true if the set contains the date, false otherwise.
   *
   * @param date The date to check for in the set.
   * @return A boolean value.
   */
  boolean setContainsGivenDate(String date);

  /**
   * Returns an array of all the keys in the portfolio.
   *
   * @return An ArrayList of Strings.
   */
  ArrayList<String> getPortfolioKeys();

  /**
   * It takes a string, and returns a LocalDate object.
   *
   * @param currentDate The date you want to parse.
   * @return local date in yyyy-mm-dd format
   */
  LocalDate localDateParser(String currentDate);


  /**
   * Save the portfolio to a file.
   */
  void savePortfolio();


  /**
   * It takes a JSON string and returns a map of strings to lists of lists of strings.
   *
   * @param data The JSON data to parse.
   * @return A HashMap with a String as the key and a List of List of Strings as the value.
   */
  Map<String, List<List<String>>> parseJson(String data);


  /**
   * Reads the contents of a file and returns it as a string.
   *
   * @param path The path to the file to read from.
   * @return A string
   */
  String readFromFile(String path);


  /**
   * Given a map of lists of lists of strings, return true if the map is a valid portfolio, and
   * false
   * otherwise.
   *
   * @param parsedPortfolio The portfolio that was parsed from the input file.
   * @return A boolean value.
   */
  boolean checkParsedPortfolio(Map<String, List<List<String>>> parsedPortfolio);

  /**
   * creates a List of all the files in the src\\portfolios\\ directory.
   *
   * @return List of Strings containing the files in the mentioned directory.
   */
  List<String> getListOfPortfolio();

  /**
   * A helper method to round off a number.
   *
   * @param val A double value.
   * @return Double value rounded to nearest decimal place.
   */
  Double helper(Double val);

  /**
   * Creates a directory in the location of current Working directory.
   */
  void createDirectory();

  /**
   * This method creates an Object of InputDataSource(with the source which we are using,
   * currently we are using AlphaVantageAPI)
   *
   * @param companyTicker String, company ticker symbol to add.
   * @return String, containing a message "Failure" if the ticker is invalid or the stock data.
   */
  String addApiCompanyStockData(String companyTicker);

  /**
   * This method is used to find, if in the current session we have already bought stocks of that
   * company
   *
   * @param ticker String, company's ticker symbol to find.
   * @return boolean, true if we have that company's stock in the current session.
   */
  boolean checkIfTickerExists(String ticker);

  /**
   * This method is to find if a portfolio is present in the current session.
   *
   * @param name String, name of the portfolio.
   * @return boolean, true if list of portfolios has a certain name.
   */
  boolean flexiblePortfolioContainsCertainKey(String name);


  /**
   * Given a Hashmap containing a company's stock data, add it to the list containing all the
   * data.
   *
   * @param stockData Hashmap of strings containing the company's stock data.
   */
  void addStockDataToFlexibleList(HashMap<String, String> stockData);

  /**
   * This method returns the size of the List of hashmap which contains stock data of companies.
   *
   * @return Integer, size of the List.
   */
  int getApiStockDataSize();

  /**
   * This is a setter for the field tickerFinder.
   *
   * @param name   String, key of the Map.
   * @param number Integer, value of the map.
   */
  void putCompanyNameInTickerFinder(String name, int number);


  /**
   * Getter for tickerFinder.
   *
   * @return Map, String as key and value as Integer representing index in apiStockData.
   */
  Map<String, Integer> getTickerFinder();

  /**
   * Getter for apiStockData.
   *
   * @return List of Hashmap of strings containing stock data of companies.
   */
  List<HashMap<String, String>> getApiStockData();


  /**
   * Getter for flexiblePortfolio hashmap.
   *
   * @return Map of String, list of list of strings containing portfolio data.
   */
  Map<String, List<List<String>>> getFlexiblePortfolio();

  /**
   * Setter for adding a particular company stock details to a portfolio.
   *
   * @param name           String,Portfolio name.
   * @param companyDetails List of List of Strings, containing company stock details.
   */
  void setterForFlexiblePortfolio(String name, List<List<String>> companyDetails);

  /**
   * Saves all the flexible Portfolios created
   */
  void saveFlexiblePortfolios();

  /**
   * Setter for the companyNameInPortfolio,adds the parameter to the set.
   *
   * @param name String, name of the company to add.
   */
  void putNameInCompanyInPortfolio(String name);

  /**
   * Getter for flexiblePort.
   *
   * @return Map of String,and value containing map of string and list.
   */
  Map<String, Map<String, List<String>>> getFlexiblePort();

  /**
   * Setter for flexiblePort.
   *
   * @param name           String, containing name of the portfolio.
   * @param companyDetails Map of String and List of strings containing unique key,company stock.
   */
  void setFlexibleNewPortfolio(String name, Map<String, List<String>> companyDetails);

  /**
   * Another setter for flexiblePort given different Set of arguments.
   *
   * @param portfolioName String, portfolio name.
   * @param keyName       String, unique key to identify company stocks.
   * @param val           List of Strings containing company StockValue.
   */
  void setFlexiblePortfolioWith(String portfolioName, String keyName, List<String> val);

  /**
   * Checker to find if flexiblePort contains certain key.
   *
   * @param name String, portfolio name.
   * @return boolean, True or false representing if portfolio contains a key or not.
   */
  boolean flexiblePortContainsCertainKey(String name);

  /**
   * This function returns a list of lists of strings, where each list of strings represents a
   * particular flexible portfolio.
   *
   * @param name The name of the portfolio you want to get.
   * @return A list of lists of strings.
   */
  List<List<String>> getParticularFlexiblePortfolio(String name);
}
