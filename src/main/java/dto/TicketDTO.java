package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
  @JsonProperty("arrival_date")
  private String arrivalDate;
  @JsonProperty("arrival_time")
  private String arrivalTime;
  @JsonProperty("carrier")
  private String carrier;
  @JsonProperty("departure_date")
  private String departureDate;
  @JsonProperty("departure_time")
  private String departureTime;
  @JsonProperty("destination")
  private String destination;
  @JsonProperty("destination_name")
  private String destinationName;
  @JsonProperty("origin")
  private String origin;
  @JsonProperty("origin_name")
  private String originName;
  @JsonProperty("price")
  private Long price;
  @JsonProperty("stops")
  private Long stops;

}
