package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import InputData.AlphaVantageAPI;
import InputData.InputDataSource;
import OutputDataSource.Json;

public class ModelImpl implements Model {
  List<String> stockCompanies = List.of("AAPL.txt", "AMZN.txt", "ATVI.txt", "BCS.txt",
          "CAJ.txt", "CSCO.txt", "DIS.txt", "JPM.txt", "MCD.txt", "MSFT.txt", "ORCL.txt", "SBUX.txt"
          , "WFC.txt");

  List<String> stockCompanyName = List.of("APPLE", "AMAZON", "ACTIVISION", "BARCLAYS"
          , "CANON INC", "CISCO SYSTEMS", "DISNEY", "JP MORGAN", "MCDONALD", "MICROSOFT"
          , "ORACLE", "STARBUCKS", "WELLS FARGO");

  String apiErrorMessage = "{\n" +
          "    \"Error Message\": \"Invalid API call. Please retry or visit the documentation " +
          "(https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.\"\n" +
          "}";
  //ArrayList of HashMap containing StockData of companies with date as key and stock value on
  //that date as value.
  List<HashMap<String, String>> stockData = new ArrayList<>();
  String startingDate = "2001-02-02";
  //String endingDate = "2022-10-25";

  String currentDate = startingDate;

  Set<String> listOfDates = new HashSet<>();
  String data;

  Map<String, List<List<String>>> inflexiblePortfolio = new HashMap<>();

  List<String> initialOptions = List.of("Create Inflexible Portfolio",
          "Examine Composition of current Portfolio",
          "Fast Forward Time", "Determine value of stocks on certain Date", "Upload a portfolio",
          "List all portfolios", "Create Flexible Portfolio", "Sell Stocks from a Portfolio"
          , "Determine Cost Basis", "Exit");

  Map<String, List<List<String>>> flexiblePortfolio = new HashMap<>();
  List<HashMap<String, String>> apiStockData;

  Map<String,Integer> tickerFinder = new HashMap<>();

  Set<String> companiesInPortfolio = new HashSet<>();

  @Override
  public List<String> getInitialOptions() {
    return initialOptions;
  }

  @Override
  public String getCurrentDate() {
    return currentDate;
  }

  //setter for currentDate
  @Override
  public void setCurrentDate(String currentDate) {
    this.currentDate = currentDate;
  }

  //getter for portfolio
  @Override
  public Map<String, List<List<String>>> getInflexiblePortfolio() {
    return inflexiblePortfolio;
  }

  @Override
  public void setInflexiblePortfolio(Map<String, List<List<String>>> inflexiblePortfolio) {
    this.inflexiblePortfolio = inflexiblePortfolio;
  }

  //getter for stockCompanyName
  @Override
  public List<String> getStockCompanyName() {
    return stockCompanyName;
  }

  //get All the stock data from the given text file and create a hashMap for each company,
  //add the hashmap to the stockData arraylist.
  @Override
  public void getContentsFromFile() {
    for (String filepath : stockCompanies) {
      try {
        //change this when building jar file
        Path path = Path.of(Path.of(System.getProperty("user.dir")) + "\\res\\" +
                "stockData");
        String files = String.valueOf(path);

        data = new String(Files.readAllBytes(Path.of(files + "\\" + filepath)));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      stockData.add(convertingStringToHashMap(data));
    }
  }

  @Override
  public HashMap<String, String> convertingStringToHashMap(String data) {
    HashMap<String, String> stockDateAndPrice = new HashMap<>();
    String[] breakingDownFullString = data.split("\r?\n|\r");
    for (String s : breakingDownFullString) {
      String[] separatingWithComa = s.split(",");
      if (separatingWithComa.length == 6) {
        stockDateAndPrice.put(separatingWithComa[0], separatingWithComa[4]);
      }
    }
    return stockDateAndPrice;
  }

  @Override
  public boolean hasAnotherPortfolioWithSameName(String name) {
    return inflexiblePortfolio.containsKey(name);
  }

  @Override
  public void addsFinalDataToPortfolio(List<List<String>> dataToAdd, String name,
                                       String currentDate) {
    ArrayList<List<String>> finalData = new ArrayList<>();
    ArrayList<String> data;
    for (List<String> strings : dataToAdd) {
      data = new ArrayList<>(strings);
      finalData.add(data);
      data.add(currentDate);
    }
    inflexiblePortfolio.put(name, finalData);
  }

  @Override
  public boolean checkIfCompanyExists(String name) {
    return stockCompanyName.contains(name.toUpperCase());
  }

  @Override
  public void savePortfolio() {
    List<String> names = new ArrayList<>();
    inflexiblePortfolio.forEach((key, value) -> names.add(key));
    Json json = new Json(this.inflexiblePortfolio, names);

    List<String> jsonPortfolios = json.FormatFromHashMap();

    try {
      Files.createDirectories(Path.of(Path.of(System.getProperty("user.dir")) + "\\" +
              "portfolios" + "\\"));
      Path path = Path.of(Path.of(System.getProperty("user.dir")) + "\\" +
              "InflexiblePortfolios");
      //System.out.println(path.toString());
      for (int i = 0; i < jsonPortfolios.size(); i++) {
        String newPath = String.valueOf(path);
        newPath += "\\" + names.get(i);
        newPath += ".txt";
        try {
          File myObj = new File(newPath);
          Files.writeString(Path.of(newPath), jsonPortfolios.get(i));
        } catch (FileNotFoundException e) {
          //handled
        } catch (IOException e) {
          //
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean isValidDate(String date) {
    try {
      LocalDate.parse(date);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  //if there is no stock data on certain date, we add 0.
  @Override
  public double getTotalStockValue(String portfolioName, String currentDate) {
    double ans = 1;

    List<List<String>> contents = inflexiblePortfolio.get(portfolioName);
    for (List<String> content : contents) {
      String company = content.get(0);
      double numbers = Double.parseDouble(content.get(1));

      double price;
      try {
        price = Double.parseDouble(stockData.get(stockCompanyName.indexOf(company
                        .toUpperCase()))
                .get(currentDate));
        ans *= (price * numbers);
      } catch (NullPointerException e) {
        //caught
      }
    }
    return ans;
  }

  @Override
  public int getPortfolioSize() {
    return inflexiblePortfolio.size();
  }

  @Override
  public boolean portfolioContainsCertainKey(String name) {
    return inflexiblePortfolio.containsKey(name);
  }

  @Override
  public String makeStringDate(int day, int month, int year) {
    String dateVal;
    String monthVal;
    if (day <= 9) {
      dateVal = "0" + day;
    } else {
      dateVal = String.valueOf(day);
    }
    if (month <= 9) {
      monthVal = "0" + month;
    } else {
      monthVal = String.valueOf(month);
    }

    return year + "-" + monthVal + "-"
            + dateVal;
  }

  @Override
  public void makeListOfDates() {
    Map<String, String> container;
    for (HashMap<String, String> stockDatum : stockData) {
      container = stockDatum;
      container.forEach((key, value) -> listOfDates.add(key));
    }
  }

  @Override
  public boolean setContainsGivenDate(String date) {
    return listOfDates.contains(date);
  }

  @Override
  public ArrayList<String> getPortfolioKeys() {
    return new ArrayList<>(inflexiblePortfolio.keySet());
  }

  @Override
  public LocalDate localDateParser(String currentDate) {
    return LocalDate.parse(currentDate);
  }

  @Override
  public HashMap<String, List<List<String>>> parseJson(String data) {
    Json json = new Json();
    HashMap<String, List<List<String>>> filePortfolio = json.Parser(data);
    return filePortfolio;
  }

  @Override
  public String readFromFile(String path) {
    StringBuilder output = new StringBuilder();
    try {
      FileReader filereader = new FileReader(path);
      int c = 0;
      while ((c = filereader.read()) != -1) {
        char character = (char) c;
        if (character == '"') {
          output.append("\"");
        } else {
          output.append(character);
        }
      }
    } catch (IOException e) {
      return "Failure";
      //throw new RuntimeException(e);
    }
    //System.out.println(output);

    return output.toString();
  }

  @Override
  public boolean checkParsedPortfolio(Map<String, List<List<String>>> parsedPortfolio) {
    List<String> keyset = new ArrayList<>(parsedPortfolio.keySet());
    List<List<String>> contents;
    List<String> insideContents;
    for (String s : keyset) {
      contents = parsedPortfolio.get(s);
      for (List<String> content : contents) {
        insideContents = new ArrayList<>(content);
        if (insideContents.size() != 3) {
          return false;
        }
        try {
          Double.parseDouble(insideContents.get(1));
        } catch (NumberFormatException e) {
          return false;
        }
        try {
          LocalDate.parse(insideContents.get(2));
        } catch (DateTimeParseException e) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public List<String> getListOfPortfolio() {
    //Path path = Path.of(Path.of(System.getProperty("user.dir")) + "\\" + "portfolios");
    List<String> files;
    File f = new File(String.valueOf(Path.of(Path.of(System.getProperty("user.dir"))
            + "\\" + "InflexiblePortfolios")));
    files = List.of(f.list());
    return files;
  }

  @Override
  public Double helper(Double val) {
    return Double.valueOf(Math.round(val));
  }

  @Override
  public void createDirectory() {
    try {
      Files.createDirectories(Path.of(Path.of(System.getProperty("user.dir")) + "\\" +
              "InflexiblePortfolios" + "\\"));
    } catch (IOException e) {
      //
    }
  }

  @Override
  public String addApiCompanyStockData(String companyTicker) {
    InputDataSource inp = new AlphaVantageAPI();
    String successOrFailure = inp.getData(companyTicker);
    if (successOrFailure.equals(apiErrorMessage)) {
      return "failure";
    }
    return successOrFailure;
  }

  @Override
  public boolean checkIfTickerExists(String ticker){
    return companiesInPortfolio.contains(ticker);
  }


}
