package censusanalyser;

public class CensusDAO {
   public int population;
   public int densityPerSqKm;
   public int areaInSqKm;
   public String State;
   public String state;
   public String stateId;
   public double areaOfState;
   public double populationDensity;
   public double Population;

   public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
      state = indiaCensusCSV.state;
      areaInSqKm = indiaCensusCSV.areaInSqKm;
      densityPerSqKm = indiaCensusCSV.densityPerSqKm;
      population = indiaCensusCSV.Population;
      areaInSqKm = indiaCensusCSV.areaInSqKm;
   }

   public CensusDAO(UsCensusCSV UsCensusCSV) {
      State = UsCensusCSV.State;
      stateId = UsCensusCSV.stateId;
      areaOfState = UsCensusCSV.areaOfState;
      populationDensity = UsCensusCSV.populationDensity;
      Population = UsCensusCSV.Population;
   }

   public CensusDAO(IndianStateCodeCSV censusCSV) {
   }
   public <R> CensusDAO(R censusCSV) {
   }
}
