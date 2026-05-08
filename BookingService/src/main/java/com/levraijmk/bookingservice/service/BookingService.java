package com.levraijmk.bookingservice.service;

import com.levraijmk.bookingservice.client.InventoryServiceClient;
import com.levraijmk.bookingservice.entity.Customer;
import com.levraijmk.bookingservice.repository.CustomerRepository;
import com.levraijmk.bookingservice.request.BookingRequest;
import com.levraijmk.bookingservice.response.BookingResponse;
import com.levraijmk.bookingservice.response.InventoryResponse;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.text.DateFormat;

@Service
public class BookingService {

    private final CustomerRepository customerRepository;
    private final InventoryServiceClient inventoryServiceClient;

    public BookingService(final CustomerRepository customerRepository,final InventoryServiceClient inventoryServiceClient ){
        this.customerRepository = customerRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    public BookingResponse createBooking(BookingRequest request) {
        //verifier si l'utilisateur existe

        final Customer customer = customerRepository.findById(request.getUserId()).orElse(null);

        if (customer == null) {
            throw new RuntimeException("User Not found");
        }
        //verifier l'inventaire des billets

        final InventoryResponse inventoryResponse = inventoryServiceClient.getInventory(request.getEventId());
        //on verifie si le place sont disponibles
       if(inventoryResponse.getCapacity() < request.getTicketCount()){
           throw new RuntimeException("Not enough inventory");
       }

        return BookingResponse.builder()
                .event(inventoryResponse.getEvent())
                .ticketPrice(inventoryResponse.getTicketPrice().toString())
                .ticketCount(inventoryResponse.getCapacity())
                .venue(inventoryResponse.getVenue())
                .userId(request.getUserId())
                .eventId(request.getEventId())


                .build();
    }
}
