
package dto;

import java.util.List;

public class TicketInfoDTO {

    private List<TicketDTO> tickets;

    public List<TicketDTO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDTO> tickets) {
        this.tickets = tickets;
    }

}
