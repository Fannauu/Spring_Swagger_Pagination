package org.example.homework001.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.homework001.model.Ticket.ApiReponse.ApiResponse;
import org.example.homework001.model.Ticket.ApiReponse.Status;
import org.example.homework001.model.Ticket.Entity.RequestTicketIDs;
import org.example.homework001.model.Ticket.Ticket;
import org.example.homework001.model.Ticket.RequestTicketPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1/")
public class TicketController {


    AtomicInteger counter = new AtomicInteger(9);
    ArrayList<Ticket> tickets = new ArrayList<>();

    public TicketController() {
        tickets.add(new Ticket(1,"Bob","2025-03-12","Kps","PP",20, false,Status.BOOKED,"A10"));
        tickets.add(new Ticket(2,"Alice","2025-03-12","PVH","SVR",20,true,Status.CANCELLED,"A11"));
        tickets.add(new Ticket(3,"John Doe","2025-03-15","SR","PP",20,false,Status.COMPLETED,"A12"));
        tickets.add(new Ticket(4,"John Mav","2025-03-18","BTB","PV",10,true,Status.BOOKED,"A13"));
        tickets.add(new Ticket(5,"Jane Jane","2025-03-19","KOH KONG","PVH",10,true,Status.COMPLETED,"A14"));
        tickets.add(new Ticket(6,"Junior Jr","2025-03-20","BTB","PP",25,false,Status.CANCELLED,"A15"));
        tickets.add(new Ticket(7,"Ronaldo","2025-03-18","BTB","KPS",29,true,Status.BOOKED,"A16"));
        tickets.add(new Ticket(8,"Messi","2025-03-19","PP","SR",40,false,Status.BOOKED,"A17"));
    }

    @Operation(summary = "Get all tickets")
    @GetMapping("/allTicket")
    public ResponseEntity<ApiResponse<List<Ticket>>> getAllTickets(@RequestParam(defaultValue = "0") int offset,   // Starting point (default: 0)
                                                                   @RequestParam(defaultValue = "10") int limit){

        int totalItems = tickets.size();
        ApiResponse<List<Ticket>> response = new ApiResponse<>(
                true,
                "Retried all tickets successfully !!",
                HttpStatus.OK,
                tickets,
                LocalDateTime.now(),
                offset,
                limit,
                totalItems
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // change multiple status method here

    @Operation(summary = "Create new ticket")
    @PostMapping("/tickets")
    public ResponseEntity<ApiResponse<Ticket>> addTicket(@RequestBody RequestTicketPost ticketPost){
        Ticket ticket = new Ticket(
                counter.getAndIncrement(),
                ticketPost.getPassengerName(),
                ticketPost.getTravelDate(),
                ticketPost.getSourceStation(),
                ticketPost.getDestinationStation(),
                ticketPost.getPrice(),
                ticketPost.isPaymentStatus(),
                ticketPost.getStatusTicket(),
                ticketPost.getSeatNumber()

        );
        tickets.add(ticket);
        ApiResponse<Ticket> response = new ApiResponse<>(
                true,
                "All ticket retrieved successfully !!",
                HttpStatus.OK,
                ticket,
                LocalDateTime.now()

        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

}


    @Operation(summary = "Get a ticket by ID")
    @GetMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<List<Ticket>>> findTicketById(@PathVariable("ticket-id") int id){
        for (Ticket ticket : tickets) {
            if(ticket.getId() == id){

                ApiResponse<List<Ticket>> response = new ApiResponse<>(
                        true,
                        "Ticket retrieved successfully.",
                        HttpStatus.OK,
                        tickets,
                        LocalDateTime.now()
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

        }
        return ResponseEntity.notFound().build();
    }


    @Operation(summary = "Update an existing by ID")
    @PutMapping("{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> updateTicket(@PathVariable("ticket-id") Integer id, @RequestBody RequestTicketPost ticketPost){
        for(Ticket ticketPostUpdate : tickets){
            if(ticketPostUpdate.getId()==id){
                ticketPostUpdate.setPassengerName((ticketPost.getPassengerName()));
                ticketPostUpdate.setTravelDate(ticketPost.getTravelDate());
                ticketPostUpdate.setSourceStation(ticketPost.getSourceStation());
                ticketPostUpdate.setDestinationStation(ticketPost.getDestinationStation());
                ticketPostUpdate.setPrice(ticketPost.getPrice());
                ticketPostUpdate.setPaymentStatus(ticketPost.isPaymentStatus());
                ticketPostUpdate.setStatusTicket(ticketPost.getStatusTicket());
                ticketPostUpdate.setSeatNumber(ticketPost.getSeatNumber());

                ApiResponse<Ticket> response = new ApiResponse<>(
                        true,
                        "Updated ticket successfully.",
                        HttpStatus.OK,
                        ticketPostUpdate,
                        LocalDateTime.now()
                );

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        }
        return ResponseEntity.status(404).build();
    }

    @Operation(summary = "Deleted a ticket by ID")
    @DeleteMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<List<Ticket>>> deleteTicketById(@PathVariable("ticket-id") int id){
        for(Ticket ticket : tickets){
            if(ticket.getId() == id){
                tickets.remove(ticket);
                ApiResponse<List<Ticket>> response = new ApiResponse<>(
                        true,
                        "Ticket Deleted Successfully",
                        HttpStatus.OK,
                        null,
                        LocalDateTime.now()
                );
               return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Search tickets by passenger name")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Ticket>>> searchTicket(@RequestParam String name){
        ArrayList<Ticket> ticketsList = new ArrayList<>();
        for (Ticket ticketSearch : tickets){
            if(ticketSearch.getPassengerName().toLowerCase().contains(name.toLowerCase())){
              ticketsList.add(ticketSearch);
            }
        }
        ApiResponse<List<Ticket>> response = new ApiResponse<>(
                true,
                "All tickets retrieved Successfully.",
                // need 100 continue
                HttpStatus.CONTINUE,
                ticketsList,
                LocalDateTime.now()
    );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Bulk Create tickets")
    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<Ticket>>> addingManyTicket(@RequestBody List<RequestTicketPost> ticketPost){
        List<Ticket> ticket = new ArrayList<>();
       for(RequestTicketPost ticketPost1 : ticketPost){
           ticket.add(new Ticket(
                   counter.getAndIncrement(),
                   ticketPost1.getPassengerName(),
                   ticketPost1.getTravelDate(),
                   ticketPost1.getSourceStation(),
                   ticketPost1.getDestinationStation(),
                   ticketPost1.getPrice(),
                   ticketPost1.isPaymentStatus(),
                   ticketPost1.getStatusTicket(),
                   ticketPost1.getSeatNumber()
           ));
       }
       tickets.addAll(ticket);

       ApiResponse<List<Ticket>> response = new ApiResponse<>(
               true,
               "Bulk tickets created successfully",
               HttpStatus.CREATED,
               ticket,
               LocalDateTime.now()
       );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Filter tickets by status and travel date")
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Ticket>>> filter(@RequestParam Status status , @RequestParam String localDate ){
        ArrayList<Ticket> ticketsList = new ArrayList<>();
        for(Ticket ticket : tickets){
            if(ticket.getTravelDate().equals(localDate) && ticket.getStatusTicket().equals(status)){
                ticketsList.add(ticket);

            }
        }
        ApiResponse<List<Ticket>> response = new ApiResponse<>(
                true,
                "Filter successfully !!",
                HttpStatus.OK,
                ticketsList,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @Operation(summary = "Bulk update payment status for multiple tickets")
    @PutMapping("/tickets")
    public ResponseEntity<ApiResponse<List<Ticket>>> updatePaymentStatus(@RequestBody RequestTicketIDs ticketIDs){
        List<Ticket> updateTickets = new ArrayList<>();
        for(Integer ticketID : ticketIDs.getTicketIDs()){
            System.out.println(ticketID);
            for(Ticket ticket : tickets){
                if(ticket.getId()==ticketID){
                    ticket.setPaymentStatus(ticketIDs.isPaymentStatus());
                    System.out.println("Update: "+ticket);
                    updateTickets.add(ticket);
                    break;
                }
            }

        }
        ApiResponse<List<Ticket>> response = new ApiResponse<>(
                true,
                "Updated successfully",
                HttpStatus.FOUND,
                updateTickets,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
