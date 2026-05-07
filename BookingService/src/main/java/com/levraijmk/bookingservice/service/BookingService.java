package com.levraijmk.bookingservice.service;

import com.levraijmk.bookingservice.request.BookingRequest;
import com.levraijmk.bookingservice.response.BookingResponse;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    public BookingResponse createBooking(BookingRequest request) {
        return BookingResponse.builder().build();
    }
}
