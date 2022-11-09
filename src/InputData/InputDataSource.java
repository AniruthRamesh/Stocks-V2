package InputData;

/**
 * This Interface is used for getting the data source. There might tbe several class implementing
 * the interface regarding the datasource which they want to use. currently the application uses
 * InputDataSource.AlphaVantageAPI, there is option for future changes as well, in the main method we have to
 * mention the data source we want to use.
 */
public interface InputDataSource {
  /**
   * This method imposes the classes implementing to pass in an argument for which we need to get
   * data source, return the data as string.
   * @param companyName String, the company's data needed.
   * @return String, containing the data.
   */
  String getData(String companyName);
}
