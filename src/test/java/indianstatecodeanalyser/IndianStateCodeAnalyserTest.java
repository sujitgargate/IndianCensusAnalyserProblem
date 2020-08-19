package indianstatecodeanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IndianStateCodeAnalyserTest {

   private static final String INDIA_STATECODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
   private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCodeData.csv";
   private static final String WRONG_INPUT_CSV_FILE = "./src/test/resources/Wrong_CSV_File.txt";

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

   //This Case Is For Checking Wrong File Path Throws Exception
   @Test
   public void givenIndianStateCodeData_WithWrongFile_ShouldThrowException() {

      try {
         exceptionRule.expect(IndianStateAnalyserException.class);
         indianStateCode.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
      } catch (IndianStateAnalyserException e) {
         Assert.assertEquals(IndianStateAnalyserException.ExceptionType.STATECODE_FILE_PROBLEM, e.type);
      }
   }

   //This Case Is For Checking Wrong Delimiter Entered Or Not
   @Test
   public void givenWrongDelimiter_InFileData_ShouldThrowCustomException() {

      try {
         indianStateCode.loadIndiaCensusData(INDIA_STATECODE_CSV_FILE_PATH);
      } catch (IndianStateAnalyserException e) {
         Assert.assertEquals(IndianStateAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
      }
   }

   //This Case Is For Missing Or Wrong Headers
   @Test
   public void givenProblemHeader_CSVData_ShouldReturnCustomException() {
      try {
         indianStateCode.loadIndiaCensusData(INDIA_STATECODE_CSV_FILE_PATH);
      } catch (IndianStateAnalyserException e) {
         Assert.assertEquals(IndianStateAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
      }
   }

   //This Case Is For Wrong File Provided
   @Test
   public void givenIndiaCensusData_WhenWrongFileType_ShouldThrowException() {
      try {
         indianStateCode.loadIndiaCensusData(WRONG_INPUT_CSV_FILE);
      } catch (IndianStateAnalyserException e) {
         Assert.assertEquals(IndianStateAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
      }
   }
}