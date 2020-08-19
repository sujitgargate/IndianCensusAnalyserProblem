package censusanalyser;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class CensusAnalyser {
   public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
      try {
         Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
         CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
         csvToBeanBuilder.withType(IndiaCensusCSV.class)
                 .withIgnoreLeadingWhiteSpace(true);
         CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
         Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();

         int numOfEnteries = 0;
         while (censusCSVIterator.hasNext()) {
            numOfEnteries++;
            IndiaCensusCSV censusData = censusCSVIterator.next();
         }
         return numOfEnteries;

      } catch (IOException e) {
         throw new CensusAnalyserException(e.getMessage(),
                 CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
      } catch (IllegalStateException e) {
         throw new CensusAnalyserException(e.getMessage(),
                 CensusAnalyserException.ExceptionType.FILE_CAN_NOT_PARSE);
      } catch (RuntimeException e) {
         throw new CensusAnalyserException(e.getMessage(),
                 CensusAnalyserException.ExceptionType.WRONG_HEADER_OR_WRONG_DELIMITER_OR_WRONG_FILE_TYPE);
      }
   }
}