package service;

import entity.Event;
import entity.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.EventRepository;
import repository.VenueRepository;
import responses.EventInventoryResponse;
import responses.VenueInventoryReponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final  EventRepository eventRepository;
    private final VenueRepository venueRepository;

    @Autowired
    public InventoryService(final EventRepository eventRepository, final VenueRepository venueRepository){
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public  List<EventInventoryResponse> getAllEvents()
    {
      final List<Event> events = eventRepository.findAll();
      return events.stream().map(
              event -> EventInventoryResponse.builder()
                      .event(event.getName())
                      .capacity(event.getLeftCapacity())
                      .venue(event.getVenue())
                      .build())
              .collect(Collectors.toList());

    }

    public VenueInventoryReponse getVenueInformation(Long venueId) {
        final Venue venue  = venueRepository.findById(venueId).orElse(null);
        return VenueInventoryReponse.builder()
                .VenueId(venue.getId())
                .venueName(venue.getName())
                .totalCapacity(venue.getTotalCapacity())

                .build();
    }
}
