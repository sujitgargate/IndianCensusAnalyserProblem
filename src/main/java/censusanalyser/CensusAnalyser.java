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

   private Map<String, CensusDAO> censusStateMap;

   public CensusAnalyser() {
      censusStateMap = new HashMap<>();
   }

   //This Method Loads Census Data From File And Returns Count
   public int loadIndiaCensusData(String csvFilePath) throws CSVException {
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
         Iterable<IndiaCensusCSV> censusCSVIterable = () -> csvFileIterator;
         StreamSupport.stream(censusCSVIterable.spliterator(), false)
                 .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
         return censusStateMap.size();
      } catch (IOException e) {
         throw new CSVException(e.getMessage(),
                 CSVException.ExceptionType.CENSUS_FILE_PROBLEM);
      }
   }

   //This Method Loads State Code Data From File And Returns Count
   public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<IndianStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndianStateCodeCSV.class);
         Iterable<IndianStateCodeCSV> stateCodeCsvIterable = () -> stateCodeCSVIterator;
         StreamSupport.stream(stateCodeCsvIterable.spliterator(), false)
                 .forEach(censusCSV -> censusStateMap.put(censusCSV.stateCode, new CensusDAO(censusCSV)));
         return censusStateMap.size();
      } catch (IOException | CSVException e) {
         throw new CensusAnalyserException(e.getMessage(),
                 CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
      }
   }

   //Parsing Sorted Data in Json Format
   public String getStateWiseSortedCensusData() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
      List<CensusDAO> censusDAOList = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAOList, censusComparator);
      return new Gson().toJson(censusDAOList);
   }

   //Using Bubble Sort To Sort Data
   private void sort(List<CensusDAO> censusDAO, Comparator<CensusDAO> censusComparator) {
      int sortingCount = 0;
      for (int i = 0; i < censusDAO.size() - 1; i++) {
         for (int j = 0; j < censusDAO.size() - i - 1; j++) {
            CensusDAO census1 = censusDAO.get(j);
            CensusDAO census2 = censusDAO.get(j + 1);
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
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
      List<CensusDAO> censusDAO = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAO, censusComparator);
      return new Gson().toJson(censusDAO);
   }

   //Sorting data based on Population of Each State
   public String getPopulationWiseSortedData() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
      List<CensusDAO> censusDAO = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAO, censusComparator);
      return new Gson().toJson(censusDAO);
   }

   //Returns Sorted State Wise Data
   public String getSortedCensusDataPopulationDensity() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
      List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAOS, censusComparator);
      return new Gson().toJson(censusDAOS);
   }

   //This Function Loads Us Census Data
   public int loadUSCensusData(String csvFilePath) throws CSVException {
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<UsCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, UsCensusCSV.class);
         Iterable<UsCensusCSV> censusCSVIterable = () -> csvFileIterator;
         StreamSupport.stream(censusCSVIterable.spliterator(), false)
                 .forEach(censusCSV -> censusStateMap.put(censusCSV.State, new CensusDAO(censusCSV)));
         return censusStateMap.size();
      } catch (IOException e) {
         System.out.println("Exception Occured During Loading Us Data");

         throw new CSVException(e.getMessage(),
                 CSVException.ExceptionType.CENSUS_FILE_PROBLEM);
      }
   }

   //Parsing Sorted state Code Data in Json Format
   public String getUsStateWiseSortedStateCodeData() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.stateId);
      List<CensusDAO> censusDAO = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAO, censusComparator);
      return new Gson().toJson(censusDAO);
   }

   //Returns High population State Data
   public String getUSSortedCensusDataPopulationDensity() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.Population);
      List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAOS, censusComparator);
      return new Gson().toJson(censusDAOS);
   }

   //Returns High population State Data
   public String getUSSortedCensusDataStateDensity() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.Population);
      List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAOS, censusComparator);
      return new Gson().toJson(censusDAOS);
   }
}