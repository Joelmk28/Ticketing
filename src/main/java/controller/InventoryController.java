package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import responses.EventInventoryResponse;
import responses.VenueInventoryReponse;
import service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(final InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory/events/")
    public @ResponseBody List<EventInventoryResponse> inventoryGetAllEvents(){

        return this.inventoryService.getAllEvents();
    }

    @GetMapping("inventory/venue/{venueId}")
    public @ResponseBody VenueInventoryReponse inventoryByVenueId(@PathVariable("venueId") Long venueId){
        return inventoryService.getVenueInformation(venueId);
    }
}
