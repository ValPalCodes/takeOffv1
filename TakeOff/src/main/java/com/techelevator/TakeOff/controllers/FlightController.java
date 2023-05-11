/*
Created 5/2/23 by AS*/

package com.techelevator.takeoff.controllers;

import com.techelevator.TakeOff.models.responses.flights.FlightOffer;
import com.techelevator.TakeOff.services.FlightApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private FlightApiService flightApiService;
    public FlightController(FlightApiService flightApiService){this.flightApiService=flightApiService;}

@RequestMapping(method = RequestMethod.GET)
public ResponseEntity<List<FlightOffer>> getFlightOffersBySearch(
        @RequestParam(defaultValue = "") String originLocationCode,
        @RequestParam(defaultValue = "") String destinationLocationCode,
        @RequestParam(defaultValue = "") String departureDate,
        @RequestParam(defaultValue = "") String returnDate, //If not entered will be a one way trip
        @RequestParam(defaultValue = "") int adults, //Assume 1 person
        @RequestParam(defaultValue = "") int max

){
        return new ResponseEntity<>(flightApiService.flightOffersSearch(originLocationCode, destinationLocationCode,
                departureDate, returnDate, adults, max), HttpStatus.OK);
}
}
