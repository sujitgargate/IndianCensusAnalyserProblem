package censusanalyser;

import com.google.gson.Gson;
import csvBuilder.*;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

   Map<String, IndiaCensusDAO> censusStateMap = null;

   public CensusAnalyser() {
      censusStateMap = new HashMap<>();
   }

   //This Method Loads Census Data From File And Returns Count
   public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException, CSVException {
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
         Iterable<IndiaCensusCSV> csvIterable = () -> csvFileIterator;
         StreamSupport.stream(csvIterable.spliterator(), false)
                      .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
         return censusStateMap.size();
      } catch (IOException e) {
         throw new CSVException(e.getMessage(),
                 CSVException.ExceptionType.CENSUS_FILE_PROBLEM);
      }
   }

   //This Method Loads State Code Data From File And Returns Count
   public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<IndianStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndianStateCodeCSV.class);
         Iterable<IndianStateCodeCSV> csvIterable = () -> stateCodeCSVIterator;
         //StreamSupport.stream(csvIterable.spliterator(), false).filter(csvState -> censusStateMap.get(csvState.state) != null)
           //           .forEach(csvState -> censusStateMap.get(csvState.state).stateCode = csvState.stateCode);
         return censusStateMap.size();
      } catch (IOException | CSVException e) {
         throw new CensusAnalyserException(e.getMessage(),
                 CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
      }
   }

/*
   private <E> int getCount(Iterator<E> iterator) {
      Iterable<E> csvIterable = () -> iterator;
      int numbOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
      return numbOfEntries;
   }
*/

   //Parsing Sorted Data in Json Format
   public String getStateWiseSortedCensusData() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
      List<IndiaCensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAOS, censusComparator);
      String sortedStateCensusJson = new Gson().toJson(censusDAOS);
      return sortedStateCensusJson;
   }

   //Using Bubble Sort TO Sort Data

   private void sort(List<IndiaCensusDAO> censusDAO, Comparator<IndiaCensusDAO> censusComparator) {
      int sortingCount = 0;
      for (int i = 0; i < censusDAO.size() - 1; i++) {
         for (int j = 0; j < censusDAO.size() - i - 1; j++) {
            IndiaCensusDAO census1 = censusDAO.get(j);
            IndiaCensusDAO census2 = censusDAO.get(j + 1);
            if (censusComparator.compare(census1, census2) > 0) {
               censusDAO.set(j, census2);
               censusDAO.set(j + 1, census1);
               sortingCount++;
            }
         }
      }
      System.out.println(sortingCount + "Times Sorting Done");
   }

   //Parsing Sorted state Code Data in Json Format
   public String getStateWiseSortedStateCodeData() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
      List<IndiaCensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAOS, censusComparator);
      String sortedStateCensusJson = new Gson().toJson(censusDAOS);
      return sortedStateCensusJson;
   }

   //Sorting data based on Population of Each State
   public String getPopulationWiseSortedData() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
      List<IndiaCensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAOS, censusComparator);
      String sortedStateCensusJson = new Gson().toJson(censusDAOS);
      return sortedStateCensusJson;
   }

   public String getSortedCensusDataPopulationDensity() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
      List<IndiaCensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAOS, censusComparator);
      String sortedStateCensusJson = new Gson().toJson(censusDAOS);
      return sortedStateCensusJson;
   }
}