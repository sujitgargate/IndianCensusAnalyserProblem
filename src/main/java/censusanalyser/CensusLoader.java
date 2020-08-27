package censusanalyser;

import csvBuilder.CSVBuilderFactory;
import csvBuilder.CSVException;
import csvBuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {

   public Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country, String[] csvFilePath) throws CensusAnalyserException {
      if (country.equals(CensusAnalyser.Country.INDIA)) {
         return this.loadCensusData(IndiaCensusCSV.class, csvFilePath);
      } else if (country.equals(CensusAnalyser.Country.US)) {
         return this.loadCensusData(UsCensusCSV.class, csvFilePath);
      } else
         throw new CensusAnalyserException("Country Not Found", CensusAnalyserException.ExceptionType.COUNTRYNOTFOUND);
   }

   //
   public <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CensusAnalyserException {
      Map<String, CensusDAO> censusStateMap = new HashMap<>();
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
         Iterable<E> csvIterable = () -> csvFileIterator;
         if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .map(IndiaCensusCSV.class::cast)
                    .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
         } else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .map(UsCensusCSV.class::cast)
                    .forEach(censusCSV -> censusStateMap.put(censusCSV.State, new CensusDAO(censusCSV)));
         }

         if (csvFilePath.length == 1) {
            return censusStateMap;
         }
         this.loadIndiaStateCode(censusStateMap, csvFilePath[1]);
         return censusStateMap;

      } catch (IOException e) {
         throw new CensusAnalyserException(e.getMessage(),
                 CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
      } catch (CensusAnalyserException e) {
         throw new CensusAnalyserException(e.getMessage(), e.type.name());
      } catch (CSVException e) {
         System.out.println("Exception Occured");
      }
      return censusStateMap;
   }

   //Loading Indian State Codes
   public int loadIndiaStateCode(Map<String, CensusDAO> censusStateMap, String csvFilePath) throws CensusAnalyserException {
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<IndianStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndianStateCodeCSV.class);
         Iterable<IndianStateCodeCSV> csvIterable = () -> stateCodeCSVIterator;
         StreamSupport.stream(csvIterable.spliterator(), false)
                 .filter(csvState -> censusStateMap.get(csvState.state) != null)
                 .forEach(csvState -> censusStateMap.get(csvState.state).stateId = csvState.stateCode);
         return censusStateMap.size();
      } catch (IOException e) {
         throw new CensusAnalyserException(e.getMessage(),
                 CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
      } catch (CSVException e) {
         throw new CensusAnalyserException(e.getMessage(), e.type.name());
      }
   }
}