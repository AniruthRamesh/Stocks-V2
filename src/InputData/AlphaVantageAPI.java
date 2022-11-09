package InputData;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class AlphaVantageAPI implements InputDataSource{
  @Override
  public String getData(String companyName) {
    String apiKey = "1IVNJAFKX4WIOZIK";
    URL url;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + companyName + "&apikey="+apiKey+"&datatype=csv");
    }
    catch (MalformedURLException e) {
      return "failure";
    }
    InputStream in;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b=in.read())!=-1) {
        output.append((char)b);
      }
    }
    catch (IOException e) {
      return "failure";
    }
    return output.toString();
  }
}
