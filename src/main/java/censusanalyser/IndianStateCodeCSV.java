package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndianStateCodeCSV extends Throwable {

   @CsvBindByName(column = "SrNo", required = true)
   public int srNo;

   @CsvBindByName(column = "State Name", required = true)
   public String state;

   @CsvBindByName(column = "TIN", required = true)
   public int TIN;

   @CsvBindByName(column = "StateCode", required = true)
   public String stateCode;

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
