package com.levraijmk.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryServiceClient {

    @Value("${inventory.services.url}")
    private String inventoryServicesUrl;

    public ResponseEntity<Void> updateInventory(final Long eventId,final Long ticketCount){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(inventoryServicesUrl+"/events/"+eventId+"/"+ticketCount,null);
//http://localhost:9090/api/v1/inventory/events/1/capacity/8
        //http://localhost:9090/api/v1/inventory/events/1
        return ResponseEntity.ok().build();
    }

}
