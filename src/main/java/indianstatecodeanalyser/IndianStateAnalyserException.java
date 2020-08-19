package indianstatecodeanalyser;

public class IndianStateAnalyserException extends Exception {

   enum ExceptionType {
      WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, STATECODE_FILE_PROBLEM, FILE_CAN_NOT_PARSE
   }

   IndianStateAnalyserException.ExceptionType type;

   public IndianStateAnalyserException(String message, IndianStateAnalyserException.ExceptionType type) {
      super(message);
      this.type = type;
   }

   public IndianStateAnalyserException(String message, IndianStateAnalyserException.ExceptionType type, Throwable cause) {
      super(message, cause);
      this.type = type;
   }
}
