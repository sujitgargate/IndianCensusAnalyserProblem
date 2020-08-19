package indianstatecodeanalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
public class IndianStateCodeAnalyserTest {

   private static final String INDIA_STATECODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
   IndianStateCodeAnalyser indianStateCode = new IndianStateCodeAnalyser();
   ExpectedException exceptionRule = ExpectedException.none();

   //This Case Is For Checking Number Of Records Matches With Given Number
   @Test
   public void givenIndianStateCodeCSVFileReturnsCorrectRecords() throws Exception {

      try {
         int numOfRecords = indianStateCode.loadIndiaCensusData(INDIA_STATECODE_CSV_FILE_PATH);
         Assert.assertEquals(37, numOfRecords);
      } catch (IndianStateAnalyserException e) {
         Assert.assertEquals(IndianStateAnalyserException.ExceptionType.STATECODE_FILE_PROBLEM, e.type);
      }
   }
}