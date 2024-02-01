package com.stalevs;

import data.AirportsData;
import tickets.PlaneTicket;

public class Main {

  public static void main(String[] args) {

    PlaneTicket planeTicket = new PlaneTicket();

    planeTicket.getMinFlightTimeByCarrier(AirportsData.VVO, AirportsData.TLV);

    planeTicket.getAverageAndMedianPriceDifference(AirportsData.VVO, AirportsData.TLV);
  }

}