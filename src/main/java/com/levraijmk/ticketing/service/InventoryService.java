package com.levraijmk.ticketing.service;

import com.levraijmk.ticketing.entity.Event;
import com.levraijmk.ticketing.entity.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.levraijmk.ticketing.repository.EventRepository;
import com.levraijmk.ticketing.repository.VenueRepository;
import com.levraijmk.ticketing.responses.EventInventoryResponse;
import com.levraijmk.ticketing.responses.VenueInventoryResponse;

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
                      .eventId(event.getId())
                      .event(event.getName())
                      .capacity(event.getLeftCapacity())
                      .venue(event.getVenue())
                      .build())
              .collect(Collectors.toList());

    }

    public VenueInventoryResponse getVenueInformation(Long venueId) {
        final Venue venue  = venueRepository.findById(venueId).orElse(null);
        return VenueInventoryResponse.builder()
                .VenueId(venue.getId())
                .venueName(venue.getName())
                .totalCapacity(venue.getTotalCapacity())

                .build();
    }

    public EventInventoryResponse getEventInformation(Long eventId) {
      Event event = this.eventRepository.findById(eventId).orElse(null);
        assert event != null;
        return EventInventoryResponse.builder()
                .eventId(eventId)
                .event(event.getName())
                .venue(event.getVenue())
                .ticketPrice(event.getTicketPrice())
                .capacity(event.getLeftCapacity())
                .eventId(event.getId())

                .build();
    }
}
