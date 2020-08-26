package censusanalyser;

public class IndiaCensusDAO {
   public int population;
   public int densityPerSqKm;
   public int areaInSqKm;
   public String state;
   public int stateCode;

   public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
      state = indiaCensusCSV.state;
      areaInSqKm = indiaCensusCSV.areaInSqKm;
      densityPerSqKm = indiaCensusCSV.densityPerSqKm;
      population = indiaCensusCSV.Population;
   }
}