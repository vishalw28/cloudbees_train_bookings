package com.cloudbees.controller;

import com.cloudbees.model.BookingRequest;
import com.cloudbees.model.BookingResponse;
import com.cloudbees.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> book(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.book(bookingRequest));
    }

//    @P
//    public ResponseEntity<BookingResponse> book(@RequestBody BookingRequest bookingRequest){
//        return ResponseEntity.ok(bookingService.book(bookingRequest));
//    }

    @DeleteMapping("/{pnr}")
    public ResponseEntity<BookingResponse> cancel(@PathVariable String pnr){
        return ResponseEntity.ok(bookingService.cancel(pnr));
    }

    @GetMapping("/{pnr}")
    public ResponseEntity<BookingResponse> fetch(@PathVariable String pnr){
        return ResponseEntity.ok(bookingService.bookingDetails(pnr));
    }
}
