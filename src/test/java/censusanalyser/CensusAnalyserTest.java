package censusanalyser;

import com.google.gson.Gson;
import csvBuilder.CSVException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;

public class CensusAnalyserTest {

   private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
   private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
   private static final String WRONG_INPUT_CSV_FILE = "./src/test/resources/Wrong_CSV_File.txt";
   private static final String WRONG_DELIMITER_CSVFILE = "./src/test/resources/Wrong_HeaderAndDelimiter.csv";
   private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
   private static final String SAMPLE_JSON_FILE_PATH = "./src/test/resources/SAMPLE_JSON_FILE_PATH.json";
   private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

   @Test
   public void givenIndianCensusCSVFileReturnsCorrectRecords() {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
         Assert.assertEquals(29, numOfRecords);
      } catch (CSVException e) {
         Assert.assertEquals(CSVException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
      }
   }

   @Test
   public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
      } catch (CSVException e) {
         Assert.assertEquals(CSVException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
      }
   }

   @Test
   public void givenIndiaCensusData_WhenWrongType_ShouldThrowException() {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_INPUT_CSV_FILE);
      } catch (CSVException e) {
         Assert.assertEquals(CSVException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
         System.out.println("Exception o");
      }
   }

   @Test
   public void givenIndiaCensusData_WhenDelimiterIncorrect_ShouldThrowException() throws CSVException {
      CensusAnalyser censusAnalyser = new CensusAnalyser();
      ExpectedException exceptionRule = ExpectedException.none();
      exceptionRule.expect(CensusAnalyserException.class);
      censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_CSVFILE);
   }

   @Test
   public void givenIndiaCensusData_WhenHeaderIncorrect_ShouldThrowException() throws CSVException {
      CensusAnalyser censusAnalyser = new CensusAnalyser();
      ExpectedException exceptionRule = ExpectedException.none();
      exceptionRule.expect(CensusAnalyserException.class);
      censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_CSVFILE);
   }

   @Test
   public void givenIndianStateCodeCSVFileReturnsCorrectRecords() {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         int numOfRecords;
         numOfRecords = censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
         Assert.assertEquals(37, numOfRecords);
         System.out.println(numOfRecords);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
      }
   }

   @Test
   public void givenIndiaStateCode_WithWrongFile_ShouldThrowException() {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
      } catch (CSVException e) {
         Assert.assertEquals(CSVException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
      }
   }

   @Test
   public void givenIndiaStateCode_WhenWrongType_ShouldThrowException() {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_INPUT_CSV_FILE);
      } catch (CSVException e) {
         Assert.assertEquals(CSVException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
      }
   }

   @Test
   public void givenIndiaStateCode_WhenDelimiterIncorrect_ShouldThrowException() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_CSVFILE);
      } catch (CSVException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE, e.type);
      }
   }

   @Test
   public void givenIndiaStateCode_WhenHeaderIncorrect_ShouldThrowException() {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         ExpectedException exceptionRule = ExpectedException.none();
         exceptionRule.expect(CensusAnalyserException.class);
         censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_CSVFILE);
      } catch (CSVException e) {
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
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
      }
   }

   @Test
   public void givenIndiaStateCodeFile_whenSortedOnStateCode_shouldReturnSortedResult() {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
         String stateWiseSortedData = censusAnalyser.getStateWiseSortedStateCodeData();
         IndianStateCodeCSV[] indianStateCodeCSV = new Gson().fromJson(stateWiseSortedData, IndianStateCodeCSV[].class);
         Assert.assertEquals("AP", indianStateCodeCSV[0].stateCode);
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
      }
   }

   @Test
   public void givenIndianCensusCSVFile_whenSortedOnPopulation_shouldReturnSortedResult() {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
         String stateWiseSortedCensusData = censusAnalyser.getPopulationWiseSortedData();
         IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
         Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
         FileWriter writer = new FileWriter(SAMPLE_JSON_FILE_PATH);
         writer.write(String.valueOf(censusCSV[0]));
         writer.close();
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
      } catch (FileNotFoundException e) {
         System.out.println("File Not Found");
      } catch (IOException e) {
         System.out.println("File Input Error Occured");
      } catch (CSVException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
      }
   }

   @Test
   public void givenIndianCensusCSVFile_whenSortedOnPopulationDensity_shouldReturnSortedResult() throws CSVException {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
         String stateWiseSortedCensusData = censusAnalyser.getSortedCensusDataPopulationDensity();
         IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
         Assert.assertEquals("Bihar", censusCSV[0].state);
         FileWriter writer = new FileWriter(SAMPLE_JSON_FILE_PATH);
         for (int i = 0; i < censusCSV.length; i++) {
            writer.write(String.valueOf(censusCSV[i]));
            writer.write(" , ");
         }
         writer.close();
      } catch (CensusAnalyserException e) {
         Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
      } catch (IOException e) {
         System.out.println("File Can't Be Parsed");
      }
   }

   @Test
   public void givenUSCensusData_ShouldReturnsCorrectRecords() {
      try {
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         int numOfRecords = censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
         Assert.assertEquals(51, numOfRecords);
         System.out.println(numOfRecords);
      } catch (Exception e) {
         System.out.println("Exception Occured");
      }
   }

   @Test
   public void givenUSCensusData_WhenSortedOnStateCode_ShouldReturnSortedResult() throws CSVException {
      try{
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
         String sortedCensusData = censusAnalyser.getUsStateWiseSortedStateCodeData();
         UsCensusCSV[] usCensusCSV =  new Gson().fromJson(sortedCensusData, UsCensusCSV[].class);
         Assert.assertEquals("AK", usCensusCSV[0].stateId);
      }catch (CensusAnalyserException e ) {
         System.out.println("Exception Occured");
      }
   }

   @Test
   public void givenUSCensusData_WhenSortedOnStatePopulation_ShouldReturnSortedResult() throws CSVException {
      try{
         CensusAnalyser censusAnalyser = new CensusAnalyser();
         censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
         String sortedCensusData = censusAnalyser.getUSSortedCensusDataPopulationDensity();
         UsCensusCSV[] usCensusCSV =  new Gson().fromJson(sortedCensusData, UsCensusCSV[].class);
         Assert.assertEquals("Wyoming", usCensusCSV[0].State);
         FileWriter writer = new FileWriter(SAMPLE_JSON_FILE_PATH);
         for (int i = 0; i < usCensusCSV.length; i++) {
            writer.write(String.valueOf(usCensusCSV[i]));
            writer.write(" , ");
         }
         writer.close();
      }catch (CensusAnalyserException | IOException e ) {
         System.out.println("Exception Occured");

      }
   }

}