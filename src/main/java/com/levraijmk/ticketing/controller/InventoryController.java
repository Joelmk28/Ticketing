package com.levraijmk.ticketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.levraijmk.ticketing.responses.EventInventoryResponse;
import com.levraijmk.ticketing.responses.VenueInventoryResponse;
import com.levraijmk.ticketing.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(final InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory/events")
    public @ResponseBody List<EventInventoryResponse> inventoryGetAllEvents(){
        return this.inventoryService.getAllEvents();
    }

    @GetMapping("/inventory/venue/{venueId}")
    public @ResponseBody VenueInventoryResponse inventoryByVenueId(@PathVariable("venueId") Long venueId){
        return inventoryService.getVenueInformation(venueId);
    }

    @GetMapping("/inventory/events/{eventId}")
    public @ResponseBody EventInventoryResponse inventoryByEnventId(@PathVariable("eventId") Long eventId){
        return inventoryService.getEventInformation(eventId);
    }
}
