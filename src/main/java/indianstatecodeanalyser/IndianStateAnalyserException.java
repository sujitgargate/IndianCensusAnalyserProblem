package indianstatecodeanalyser;

public class IndianStateAnalyserException extends Exception {

   enum ExceptionType {
      STATECODE_FILE_PROBLEM
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
