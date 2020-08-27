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

   public enum Country {
      INDIA, US;
   }

   //This Method Loads Census Data From File And Returns Count
   public int loadCensusData(Country country, String... csvFilePath) throws CSVException, CensusAnalyserException {
      censusStateMap = new CensusLoader().loadCensusData(country, csvFilePath);
      return censusStateMap.size();
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
   public String getStateCodeWiseSortedData() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
      List<CensusDAO> censusDAO = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAO, censusComparator);
      return new Gson().toJson(censusDAO);
   }

   //Sorting data based on Population of Each State
   public String getStateWiseSortedData() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
      List<CensusDAO> censusDAO = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAO, censusComparator);
      return new Gson().toJson(censusDAO);
   }

   //Returns Sorted State Wise Sorted Data
   public String getStatePopulationWiseSortedData() throws CensusAnalyserException {
      if (censusStateMap == null || censusStateMap.size() == 0) {
         throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
      List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAOS, censusComparator);
      return new Gson().toJson(censusDAOS);
   }

   //Returns State Population Sorted Data
   public String getStatePopulationDensityWiseSortedData() {
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.populationDensity);
      List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAOS, censusComparator);
      String sortedStateCensusJson = new Gson().toJson(censusDAOS);
      return sortedStateCensusJson;
   }

   //Returns State Area In SqKM Sorted Data
   public String getStateAreaWiseSortedData() {
      Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
      List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
      this.sort(censusDAOS, censusComparator);
      String sortedStateCensusJson = new Gson().toJson(censusDAOS);
      return sortedStateCensusJson;
   }
}