package censusanalyser;

import com.google.gson.Gson;
import csvBuilder.CSVException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.cert.CertStoreException;

public class CensusAnalyserTest {

   private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
   private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
   private static final String WRONG_INPUT_CSV_FILE = "./src/test/resources/Wrong_CSV_File.txt";
   private static final String WRONG_DELIMITER_CSVFILE = "./src/test/resources/Wrong_HeaderAndDelimiter.csv";
   private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

   @Test
   public void givenIndianCensusCSVFileReturnsCorrectRecords() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
         Assert.assertEquals(29, numOfRecords);
      } catch (CensusAnalyserException e) {
      }
   }

   @Test
   public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
      }
   }

   @Test
   public void givenIndiaCensusData_WhenWrongType_ShouldThrowException() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_INPUT_CSV_FILE);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
      }
   }

   @Test
   public void givenIndiaCensusData_WhenDelimiterIncorrect_ShouldThrowException() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_CSVFILE);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
      }
   }

   @Test
   public void givenIndiaCensusData_WhenHeaderIncorrect_ShouldThrowException() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_CSVFILE);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
      }
   }

   @Test
   public void givenIndianStateCodeCSVFileReturnsCorrectRecords() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         int numOfRecords = 0;
         numOfRecords = censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
         Assert.assertEquals(37, numOfRecords);
      } catch (CensusAnalyserException e) {
      }
   }

   @Test
   public void givenIndiaStateCode_WithWrongFile_ShouldThrowException() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
      }
   }

   @Test
   public void givenIndiaStateCode_WhenWrongType_ShouldThrowException() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_INPUT_CSV_FILE);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
      }
   }

   @Test
   public void givenIndiaStateCode_WhenDelimiterIncorrect_ShouldThrowException() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_CSVFILE);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
      }
   }

   @Test
   public void givenIndiaStateCode_WhenHeaderIncorrect_ShouldThrowException() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_CSVFILE);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
      }
   }

   @Test
   public void givenIndiaStateCode_WhenSortedOnState_ShouldReturnSortedResult() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
         String SortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
         IndiaCensusCSV[] censusCSV = new Gson().fromJson(SortedCensusData, IndiaCensusCSV[].class);
         Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
      } catch(CensusAnalyserException e) {
         System.out.println("Error Occured While Parsing Data");
      }
   }

   @Test
   public void givenIndiaStateCodeFile_whenSortedOnStateCode_shouldReturnSortedResult() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
         String stateWiseSortedData = censusAnalyser.getStateWiseSortedStateCodeData();
         IndianStateCodeCSV[] indianStateCodeCSV = new Gson().fromJson(stateWiseSortedData, IndianStateCodeCSV[].class);
         Assert.assertEquals("AP", indianStateCodeCSV[0].stateCode);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
      }
   }
}