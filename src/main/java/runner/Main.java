package runner;

import data.AirportsData;
import tickets.PlaneTicket;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {

    PlaneTicket planeTicket = new PlaneTicket();

    planeTicket.printTicketData();

    planeTicket.getAverageAndMedianPriceDifference(AirportsData.VVO, AirportsData.TLV);
  }

}