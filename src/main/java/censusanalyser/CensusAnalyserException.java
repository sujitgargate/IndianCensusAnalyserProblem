package censusanalyser;

/*public class CensusAnalyserException extends Exception {

   public enum ExceptionType {
      WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, CENSUS_FILE_PROBLEM, FILE_CAN_NOT_PARSE, NO_CENSUS_DATA;
   }

   public ExceptionType type;

   public CensusAnalyserException(String message, ExceptionType type) {
      super(message);
      this.type = type;
   }

   public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
      super(message, cause);
      this.type = type;
   }
}*/

public class CensusAnalyserException extends Exception {

   enum ExceptionType {

      WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, CENSUS_FILE_PROBLEM, FILE_CAN_NOT_PARSE, NO_CENSUS_DATA;
   }

   ExceptionType type;

   public CensusAnalyserException(String message, ExceptionType type) {
      super(message);
      this.type = type;
   }
}