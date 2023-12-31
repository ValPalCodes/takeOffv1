/*
Added to App 4/21/23 by AS
*/

package com.techelevator.TakeOff.services;

import com.techelevator.TakeOff.models.combination.HotelAndOffers;
import com.techelevator.TakeOff.models.responses.hotels.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HotelApiService extends ApiBaseService {

    public List<Hotel> getHotelsByCity(String city, String stars, String amenities) {
        HttpEntity<String> entity = new HttpEntity<String>(getHeadersWithAuth());

        String url = "https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-city?cityCode={cityCode}";

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("cityCode", city);
        queryParams.put("ratings", stars);
        queryParams.put("amenities", amenities);

        if (!stars.isEmpty()) {
            url += "&ratings={ratings}";
        }
        if (!amenities.isEmpty()) {
            url += "&amenities={amenities}";
        }

        ResponseEntity<HotelData> response = restTemplate.exchange(url, HttpMethod.GET, entity, HotelData.class, queryParams);


        return response.getBody().getHotels();
    }

    public List<HotelOffer> getHotelOffers(String hotelIds, String adults, String checkIn, String checkOut) {
        HttpEntity<String> entity = new HttpEntity<>(getHeadersWithAuth());

        String params = "hotelIds={hotelIds}";

        if (adults != null && !adults.isBlank())
            params += "&adults={adults}";

        if(checkIn != null && !checkIn.isBlank()) {
            params += "&checkInDate={checkInDate}";
        }

        if(checkOut != null && !checkOut.isBlank()) {
            params += "&checkOutDate={checkOutDate}";
        }

        String url =
                "https://test.api.amadeus.com/v3/shopping/hotel-offers?currency=USD&countryOfResidence=US&" + params;

        System.out.println("--------------------------------------");
        System.out.println(url);
        System.out.println("--------------------------------------");
        ResponseEntity<HotelOffers> response =
                restTemplate.exchange(url,
                        HttpMethod.GET, entity, HotelOffers.class, hotelIds, adults, checkIn, checkOut);
        return response.getBody().getHotelOffers();
    }

    public List<HotelAndOffers> getHotelsAndTheirOffers(String city, String adults, String checkIn, String checkOut) {
        List<HotelAndOffers> hotelAndOffers = new ArrayList<>();
        int i = 0;
        List<Hotel> hotels = getHotelsByCity(city, "", "");
        for(Hotel hotel : hotels) {
            i++;
            if (i > 5) { break; }
            hotelAndOffers.add(new HotelAndOffers(hotel,getHotelOffers(hotel.getHotelId(),adults,checkIn,checkOut)));
        }
        return hotelAndOffers;
    }

    public OfferData getOfferData(String offerId) {

        HttpEntity<String> entity = new HttpEntity<>(getHeadersWithAuth());

        String url = "https://test.api.amadeus.com/v3/shopping/hotel-offers/{offerId}";

        ResponseEntity<OfferDetails> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, OfferDetails.class, offerId);
        return response.getBody().getOfferData();
    }

    public List<BookingData> bookHotel(CustomerDataParent customerData) {


        HttpEntity<String> entity = new HttpEntity<>(getHeadersWithAuth());

        String url = "https://test.api.amadeus.com/v1/booking/hotel-bookings";

// <<<<<<< priceybranch
//         ObjectMapper om = new ObjectMapper();
//         try {
//             om.writeValue(System.out, customerData);
//         } catch (Throwable e) {
//             System.out.println("uh oh");
//         }


        ResponseEntity<BookingConfirmation> response =
                restTemplate.exchange(url, HttpMethod.POST, entity, BookingConfirmation.class, customerData);
        return response.getBody().getBookingData();
    }

}
