package com.levraijmk.bookingservice.client;

import com.levraijmk.bookingservice.response.InventoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryServiceClient {

    @Value("${inventory.services.url}")
    private String inventoryServicesUrl;


    public InventoryResponse getInventory(final Long eventId){
        final RestTemplate restTemplate  =new RestTemplate();
        return restTemplate.getForObject(inventoryServicesUrl+"/events/"+eventId,InventoryResponse.class);
    }

}
