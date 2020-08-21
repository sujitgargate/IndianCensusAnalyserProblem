package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

   //This Method Loads Census Data From File And Returns Count
   public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
         Iterator<IndiaCensusCSV> censusCSVIterator = getCSVFileIterator(reader, IndiaCensusCSV.class);
         Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVIterator;
         return getCount(censusCSVIterator);
      } catch (IOException e) {
         throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
      } catch (RuntimeException e) {
         throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE);
      }
   }

   //This Method Loads State Code Data From File And Returns Count
   public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
         Iterator<IndianStateCodeCSV> stateCSVIterator = getCSVFileIterator(reader, IndianStateCodeCSV.class);
         Iterable<IndianStateCodeCSV> csvIterable = () -> stateCSVIterator;
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

   //Common Method For Iterating Through CSV File
   public static <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CensusAnalyserException {
      try {
         CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder(reader);
         csvToBeanBuilder.withType(csvClass);
         csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
         CsvToBean<E> csvToBean = csvToBeanBuilder.build();
         return csvToBean.iterator();
      } catch (IllegalStateException e) {
         throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_CAN_NOT_PARSE);
      }
   }
}