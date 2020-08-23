package censusanalyser;

import csvBuilder.*;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

   //This Method Loads Census Data From File And Returns Count
   public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException, CSVException {
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         List <IndiaCensusCSV> censusCSVList = csvBuilder.getCSVList(reader, IndiaCensusCSV.class);
         return censusCSVList.size();
      } catch (IOException e) {
         throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
      } catch (RuntimeException e) {
         throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE);
      }
   }

   //This Method Loads State Code Data From File And Returns Count
   public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException, CSVException {
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<IndianStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader, IndianStateCodeCSV.class);
         return getCount(stateCSVIterator);
      } catch (IOException e) {
         throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
      } catch (IllegalStateException e) {
         throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_CAN_NOT_PARSE);
      } catch (RuntimeException e) {
         throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE);
      }
   }

   private <E> int getCount(Iterator<E> iterator) {
      Iterable<E> csvIterable = () -> iterator;
      int numbOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
      return numbOfEntries;
   }
}