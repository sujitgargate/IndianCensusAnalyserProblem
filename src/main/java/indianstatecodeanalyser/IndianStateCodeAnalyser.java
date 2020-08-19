package indianstatecodeanalyser;

import censusanalyser.CensusAnalyserException;
import censusanalyser.IndiaCensusCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class IndianStateCodeAnalyser {

   public int loadIndiaCensusData(String csvFilePath) throws IndianStateAnalyserException {
      try {
         Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
         CsvToBeanBuilder<IndianStateCodeCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
         csvToBeanBuilder.withType(IndianStateCodeCSV.class)
                 .withIgnoreLeadingWhiteSpace(true);
         CsvToBean<IndianStateCodeCSV> csvToBean = csvToBeanBuilder.build();
         Iterator<IndianStateCodeCSV> indianStateCodeCSVIterator = csvToBean.iterator();
         ;
         int numOfEnteries = 0;
         while (indianStateCodeCSVIterator.hasNext()) {
            numOfEnteries++;
            IndianStateCodeCSV censusData = indianStateCodeCSVIterator.next();
         }
         return numOfEnteries;
      } catch (IOException e) {
         throw new IndianStateAnalyserException(e.getMessage(),
                 IndianStateAnalyserException.ExceptionType.STATECODE_FILE_PROBLEM);
      }
   }
}
