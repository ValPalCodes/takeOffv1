<template>
  <main>
    <div v-if="loading" id="plane-container">
      <img id="plane" src="spinner.gif" />
    </div>

    <div id="hotelOfferDetails" v-if="hotelOfferDetails">
      <img
        id="hotel-image"
        src="hotel4_francesca-saraco-_dS27XGgRyQ-unsplash.jpg"
      />
      <h2 id="hotel-name">{{ hotel.name }}</h2>

      <h2>
        <span>Price:</span>
        ${{
          hotelOfferDetails.price.total.toLocaleString("en-US", {
            style: "currency",
            currency: "USD",
          })
        }}
      </h2>
      <h2><span>Check-in:</span> {{ hotelOfferDetails.checkInDate }}</h2>
      <h2><span>Check-out:</span> {{ hotelOfferDetails.checkOutDate }}</h2>

      <h2><span>Adults:</span> {{ hotelOfferDetails.guests.adults }}</h2>
      <h2 v-if="hotelOfferDetails.room.typeEstimated != null">
        {{ hotelOfferDetails.room.typeEstimated.category }}
      </h2>
      <h2 v-if="hotelOfferDetails.room.typeEstimated != null">
        <span>Beds:</span>
        {{ hotelOfferDetails.room.typeEstimated.beds }}
      </h2>
      <h2 v-if="hotelOfferDetails.room.typeEstimated != null">
        <span>Bed Type: </span>
        {{ hotelOfferDetails.room.typeEstimated.bedType }}
      </h2>
      <!-- <h2>{{ hotelOfferDetails.room.description.text }}</h2> -->

      <button @click="bookHotel">BOOK HOTEL</button>
    </div>
  </main>
</template>
    
    <script>
import { pushScopeId } from "vue";
import HotelService from "../services/HotelService";

export default {
  components: {},
  data() {
    return {
      hotelOfferDetails: null,
      hotel: null,
      loading: false,
    };
  },
  created() {
    this.loading = true;
    HotelService.getHotelOfferDetailsByOfferId(this.$route.params.offerId).then(
      (response) => {
        this.hotelOfferDetails = response.data.offers[0];
        this.hotel = response.data.hotel;
        this.loading = false;
      }
    );
  },
  methods: {
    bookHotel() {
      this.$router.push({ name: "Confirmation" });
    },
  },
};
</script>
    
<style scoped>
:root {
  --primary-color: #0038ff;
  --secondary-color: #85c4ff;
  --third-color: #ffffff;
  --fourth-color: #000000;
  --fifth-color: #e0e0e0;
  --sixth-color: #db0f0f;
  --seventh-color: #8a2020;
  --eighth-color: #ffc107;
}

* {
  box-sizing: border-box;
}

main {
  overflow: auto;
  background: linear-gradient(rgba(0, 0, 0, 0.322), rgba(0, 0, 0, 0.548)),
    url(hotel1_rhema-kallianpur-uocSnWMhnAs-unsplash.jpg);
  background-size: cover;
  background-position: center center;
  background-attachment: fixed;
}

#plane-container {
  height: 100vh;
  width: 100vw;
  position: fixed;
  left: 0;
  top: 0;
  justify-content: center;
  align-items: center;
  display: flex;
  background: linear-gradient(rgba(0, 0, 0, 0.322), rgba(0, 0, 0, 0.548)),
    url(hotel1_rhema-kallianpur-uocSnWMhnAs-unsplash.jpg);
  background-size: cover;
  background-position: center center;
  background-attachment: fixed;
}

span {
  color: var(--eighth-color);
}

#hotel-image {
  width: 550px;
  height: auto;
}

#hotelOfferDetails {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  flex-direction: column;
  color: white;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.363);
  font-size: 0.8rem;
  gap: 20px;
  padding-top: 1em;
  padding-bottom: 1em;
}

#hotel-name {
  color: white;
}

button {
  background-color: rgba(145, 52, 24, 0.76);
  padding: 1em;
  width: 500px;
  color: white;
  font-weight: 600;
}

button:hover {
  color: var(--eighth-color);
  border: 3px solid var(--third-color);
}
</style>