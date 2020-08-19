package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

   private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
   private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
   CensusAnalyser censusAnalyser = new CensusAnalyser();
   ExpectedException exceptionRule = ExpectedException.none();

   //This Case Is For Checking Number Of Records Matches With Given Number
   @Test
   public void givenIndianCensusCSVFileReturnsCorrectRecords() throws Exception {
      try {

         int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
         Assert.assertEquals(29, numOfRecords);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
      }
   }

   //This Case Is For Checking Wrong File Path Throws Exception
   @Test
   public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {

      try {
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
      }
   }

   //This Case Is For Checking Wrong Delimiter Entered Or Not
   @Test
   public void givenWrongDelimiter_InFileData_ShouldThrowCustomException() {
      try {
         censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER, e.type);
      }
   }

   @Test
   public void givenProblemHeader_CSVData_ShouldReturnCustomException() {
      try {
         censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER, e.type);
      }
   }

}