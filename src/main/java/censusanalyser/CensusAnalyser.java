package censusanalyser;

import com.google.gson.Gson;
import csvBuilder.*;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

   List<IndiaCensusDAO> censusList = null;
   List<IndianStateCodeCSV> stateCSVList = null;

   public CensusAnalyser() {
      this.censusList = new ArrayList<IndiaCensusDAO>();
   }

   //This Method Loads Census Data From File And Returns Count
   public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException, CSVException {
      try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
         ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
         Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
         while (csvFileIterator.hasNext()) {
            this.censusList.add(new IndiaCensusDAO(csvFileIterator.next()));
         }
         return this.censusList.size();
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

   //Parsing Sorted Data in Json Format
   public String getStateWiseSortedCensusData() throws CensusAnalyserException {
      if (censusList == null || censusList.size() == 0) {
         throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
      this.sort(censusList, censusComparator);
      String sortedStateCensusJson = new Gson().toJson(censusList);
      return sortedStateCensusJson;
   }

   //Using Bubble Sort TO Sort Data
   private <E> List<E> sort(List<E> indianDataList, Comparator<E> censusComparator) {
      int sortingCount = 0;
      for (int i = 0; i < indianDataList.size() - 1; i++) {
         for (int j = 0; j < indianDataList.size() - i - 1; j++) {
            E census1 = indianDataList.get(j);
            E census2 = indianDataList.get(j + 1);
            if (censusComparator.compare(census1, census2) < 0) {
               indianDataList.set(j, census2);
               indianDataList.set(j + 1, census1);
               sortingCount++;
            }
         }
      }
      System.out.println(sortingCount + "Times Sorting Has Done");
      return indianDataList;
   }

   //Parsing Sorted state Code Data in Json Format
   public String getStateWiseSortedStateCodeData() throws CensusAnalyserException {
      if (stateCSVList == null || stateCSVList.size() == 0) {
         throw new CensusAnalyserException("No Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<IndianStateCodeCSV> stateCodeComparator = Comparator.comparing(census -> census.stateCode);
      this.sort(stateCSVList, stateCodeComparator);
      String sortedStateCensusJson = new Gson().toJson(stateCSVList);
      return sortedStateCensusJson;
   }

   //Sorting data based on Population of Each State
   public String getPopulationWiseSortedData() {
      Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
      sort(this.censusList, censusComparator);
      String sortedStateCensusJson = new Gson().toJson(censusList);
      return sortedStateCensusJson;
   }

   public String getSortedCensusDataPopulationDensity() throws CensusAnalyserException {
      if (censusList == null || censusList.size() == 0) {
         throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
      }
      Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
      sort(censusList, censusComparator);
      String sortedPopulationCensusJson = new Gson().toJson(censusList);
      return sortedPopulationCensusJson;
   }
}