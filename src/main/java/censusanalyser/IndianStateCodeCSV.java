package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndianStateCodeCSV {

   @CsvBindByName(column = "SrNo", required = true)
   public String srNo;

   @CsvBindByName(column = "State Name", required = true)
   public int state;

   @CsvBindByName(column = "TIN", required = true)
   public int TIN;

   @CsvBindByName(column = "StateCode", required = true)
   public int stateCode;

   @Override
   public String toString() {
      return "IndiaCensusCSV{" +
              "SrNo='" + srNo + '\'' +
              ", State Name ='" + state + '\'' +
              ", TIN ='" + TIN + '\'' +
              ", StateCode ='" + stateCode + '\'' +
              '}';
   }
}
