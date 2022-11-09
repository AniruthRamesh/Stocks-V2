package OutputDataSource;

import java.util.HashMap;
import java.util.List;

public interface SavingDataSource {
  List<String> FormatFromHashMap();
  HashMap<String, List<List<String>>> Parser(String json);
}
