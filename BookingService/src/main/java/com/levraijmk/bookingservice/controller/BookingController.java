package com.levraijmk.bookingservice.controller;

import com.levraijmk.bookingservice.BookingServiceApplication;
import com.levraijmk.bookingservice.request.BookingRequest;
import com.levraijmk.bookingservice.response.BookingResponse;
import com.levraijmk.bookingservice.service.BookingService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Resource
@RequestMapping("/api/v1")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(final BookingService bookingService){
        this.bookingService=bookingService;
    }


    @PostMapping("/booking")
    public BookingResponse createBooking(@RequestBody BookingRequest request){
        return bookingService.createBooking(request);
    }



}
