package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class UsCensusCSV {

   @CsvBindByName(column = "State", required = true)
   public String State;

   @CsvBindByName(column = "State ID", required = true)
   public String stateId;

   @CsvBindByName(column = "Population", required = true)
   public int population;

   @CsvBindByName(column = "Total area", required = true)
   public double totalArea;

   @CsvBindByName(column = "Population Density", required = true)
   public double populationDensity;

   @Override
   public String toString() {
      return "UsCensusCSV{" +
              "State='" + State + '\'' +
              ", State Id='" + stateId + '\'' +
              ", Population='" + population + '\'' +
              ", Total Area='" + totalArea + '\'' +
              ", population Density='" + populationDensity + '\'' +
              '}';
   }
}

