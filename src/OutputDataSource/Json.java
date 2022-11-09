package OutputDataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A helper class for ModelImpl. It converts a given HashMap to Json and also parses a given string
 * in Json format and creates a hashmap.
 */
public class Json implements SavingDataSource{
    Map<String, List<List<String>>> portfolio;
    List<String> keys;

    /**
     * Constructor for the Json class. Initializes the fields portfolio, keys with argument.
     *
     * @param portfolio Map of string and a nested List of strings, represents the portfolio data.
     * @param keys      List of strings, containing keys(names of companies) of the portfolio.
     */
    public Json(Map<String, List<List<String>>> portfolio, List<String> keys) {
      this.portfolio = portfolio;
      this.keys = keys;
    }

    /**
     * A default constructor for the Json class. Initializes field with default values.
     */
    public Json() {
      portfolio = new HashMap<>();
      keys = new ArrayList<>();
    }

    @Override
    public List<String> FormatFromHashMap() {
      List<String> jsonPortfolio = new ArrayList<>();

      for (String portfolioName : keys) {
        StringBuilder currPortfolio = new StringBuilder("{");
        currPortfolio.append("\"");
        currPortfolio.append(portfolioName);
        currPortfolio.append("\":");
        currPortfolio.append("[");

        List<List<String>> currContents = portfolio.get(portfolioName);
        String anotherPrefix = "";
        for (List<String> currContent : currContents) {
          currPortfolio.append(anotherPrefix);
          currPortfolio.append("[");

          String prefix = "";
          for (String s : currContent) {
            currPortfolio.append(prefix);
            prefix = ",";
            currPortfolio.append("\"");
            currPortfolio.append(s);
            currPortfolio.append("\"");
          }
          currPortfolio.append("]");
          anotherPrefix = ",";
        }
        currPortfolio.append("]");
        currPortfolio.append("}");

        jsonPortfolio.add(currPortfolio.toString());
      }

      return jsonPortfolio;
    }

    @Override
    public HashMap<String, List<List<String>>> Parser(String json) {

      HashMap<String, List<List<String>>> portfolio = new HashMap<>();
      List<String> portfolioNameAndData = Arrays.asList(json.split(":"));


      if (portfolioNameAndData.size() != 2) {
        return null;
      }
      List<String> possibleWaste = List.of("[", "[[", ",", "]", "]]}");

      String[] splittingCompanyData = portfolioNameAndData.get(1).split("],");
      List<List<String>> furtherSplittingCompanyData = new ArrayList<>();

      for (String splittingCompanyDatum : splittingCompanyData) {
        List<String> gettingAllData = Arrays.asList(splittingCompanyDatum.split("\""));
        furtherSplittingCompanyData.add(gettingAllData);
      }

      List<List<String>> actualPortfolioContents = new ArrayList<>();
      for (List<String> furtherSplittingCompanyDatum : furtherSplittingCompanyData) {
        List<String> adder = new ArrayList<>();
        for (String temp : furtherSplittingCompanyDatum) {
          if (possibleWaste.contains(temp)) {
            continue;
          }
          adder.add(temp);
        }
        actualPortfolioContents.add(adder);
      }

      String companyNameWithNoise = portfolioNameAndData.get(0);
      String companyName = companyNameWithNoise.substring(2, companyNameWithNoise.length() - 1);
      portfolio.put(companyName, actualPortfolioContents);
      return portfolio;
    }
  }

