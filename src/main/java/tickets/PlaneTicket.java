package tickets;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.AirportsData;
import data.CarriersData;
import dto.TicketDTO;
import dto.TicketInfoDTO;
import exceptions.JsonFileReadException;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PlaneTicket {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final String ticketsPath = System.getProperty("tickets.path", "src/main/resources/tickets.json");
  private final List<TicketDTO> listOfTickets = getListOfTickets();

  private List<TicketDTO> getListOfTickets() {
    TicketInfoDTO ticketInfoDTO;
    try {
      ticketInfoDTO = objectMapper.readValue(new File(ticketsPath), TicketInfoDTO.class);
    } catch (IOException e) {
      throw new JsonFileReadException(ticketsPath, e);
    }
    return ticketInfoDTO.getTickets();
  }

  private List<TicketDTO> getTicketsByRoute(AirportsData departure, AirportsData destination) {
    return listOfTickets.stream()
        .filter(t -> t.getOriginName().equals(departure.getName()))
        .filter(t -> t.getDestinationName().equals(destination.getName()))
        .collect(Collectors.toList());
  }

  private Long getAverageFlightPrice(List<TicketDTO> tickets) {
    long averageFlightPrice = 0;
    for (TicketDTO ticket : tickets) {
      averageFlightPrice += ticket.getPrice();
    }
    return averageFlightPrice / tickets.size();
  }

  private Long getMedianFlightPrice(List<TicketDTO> tickets) {
    Long medianPrice;
    List<Long> prices = new ArrayList<>();
    for (TicketDTO ticket : tickets) {
      prices.add(ticket.getPrice());
    }
    Collections.sort(prices);
    if (prices.size() % 2 != 0) {
      medianPrice = prices.get(prices.size() / 2);
    } else {
      medianPrice = (prices.get(prices.size() / 2 - 1) + prices.get(prices.size() / 2)) / 2;
    }
    return medianPrice;
  }

  public void getMinFlightTimeByCarrier(AirportsData departure, AirportsData destination) {
    List<TicketDTO> flightsByRoute = getTicketsByRoute(departure, destination);
    Map<CarriersData, Duration> minFlightTimes = new HashMap<>();
    for (TicketDTO ticket : flightsByRoute) {
      CarriersData carrier = CarriersData.valueOf(ticket.getCarrier());
      Duration flightTime = getFlightTime(ticket);
      if (!minFlightTimes.isEmpty()) {
        if (minFlightTimes.get(carrier) != null) {
          Duration registerdDuration = minFlightTimes.get(carrier);
          if (registerdDuration.compareTo(flightTime) > 0) {
            minFlightTimes.put(carrier, flightTime);
          }
        } else {
          minFlightTimes.put(carrier, flightTime);
        }
      } else {
        minFlightTimes.put(carrier, flightTime);
      }
    }
    minFlightTimes.forEach((key, value) ->
        System.out.printf("Минимальное время полета из города %s в город %s для перевозчика %s составляет %s.%n",
            departure.getName(), destination.getName(), key.getName(), timeConverter(value)));
  }

  public void getAverageAndMedianPriceDifference(AirportsData departure, AirportsData destination) {
    System.out.printf("Для полета из города %s в город %s разница между средней ценой и медианой составляет %s рублей.%n",
        departure.getName(), destination.getName(),
        (getAverageFlightPrice(getTicketsByRoute(departure, destination)))
            - getMedianFlightPrice(getTicketsByRoute(departure, destination)));
  }

  private Duration getFlightTime(TicketDTO ticket) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    LocalDateTime departure = LocalDateTime.parse(getFlightDepartureString(ticket), formatter);
    LocalDateTime arrival = LocalDateTime.parse(getFlightArrivalString(ticket), formatter);
    return Duration.between(departure, arrival);
  }

  private String timeConverter(Duration duration) {
    long hours = duration.toMinutes() / 60;
    long minutes = duration.toMinutes() % 60;
    return String.format("%02d часов %02d минут", hours, minutes);
  }

  private String getFlightDepartureString(TicketDTO ticket) {
    String[] dayString = ticket.getDepartureDate().split("\\.");
    String[] timeString = ticket.getDepartureTime().split(":");
    if (dayString[2].length() < 3) {
      dayString[2] = String.format("20%s", dayString[2]);
    }
    if (timeString[0].length() != 2) {
      timeString[0] = String.format("0%s", timeString[0]);
    }
    return String.format("%s.%s.%s %s:%s", dayString[0], dayString[1], dayString[2], timeString[0], timeString[1]);
  }

  private String getFlightArrivalString(TicketDTO ticket) {
    String[] dayString = ticket.getArrivalDate().split("\\.");
    String[] timeString = ticket.getArrivalTime().split(":");
    if (dayString[2].length() < 3) {
      dayString[2] = String.format("20%s", dayString[2]);
    }
    if (timeString[0].length() != 2) {
      timeString[0] = String.format("0%s", timeString[0]);
    }
    return String.format("%s.%s.%s %s:%s", dayString[0], dayString[1], dayString[2], timeString[0], timeString[1]);
  }

}