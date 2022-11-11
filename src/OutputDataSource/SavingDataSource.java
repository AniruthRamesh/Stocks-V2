package OutputDataSource;

import java.util.HashMap;
import java.util.List;

public interface SavingDataSource {
  /**
   * It returns a list of strings.
   *
   * @return A list of strings.
   */
  List<String> FormatFromHashMap();

  /**
   * Given a JSON string, return a HashMap that maps each key to a list of lists of strings.
   *
   * @param json The json string to be parsed.
   * @return A HashMap with a String as the key and a List of List of Strings as the value.
   */
  HashMap<String, List<List<String>>> Parser(String json);
}
