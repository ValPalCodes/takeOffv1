import { createApp } from 'vue'
import './style.css'
import { createRouter, createWebHashHistory } from 'vue-router'
import App from './App.vue'
import Home from './pages/home.vue'
import Flights from './pages/flights.vue'
import Hotels from './pages/hotels.vue'
import Contact from './pages/contact.vue'
import FlightView from './pages/flight-view.vue'
import FlightDetails from './pages/flight-details.vue'
import HotelDetails from './pages/hotel-details.vue'
import HotelView from './pages/hotel-view.vue'
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import HotelOffers from './components/HotelOffers'



const vuetify = createVuetify({
  components,
  directives,
})

const router = createRouter({
    history: createWebHashHistory(),
    routes:   [
        { path: '/', component: Home },
        { path: '/hotels', component: Hotels },
        { path: '/flights', component: Flights },
        { path: '/contact', component: Contact },
        { path: '/flight-details/:id', component: FlightDetails },
        { path: '/hotels/offers/:hotelId', name: 'hotelOffers', component: HotelOffers}

    ]
})

axios.defaults.baseURL = 'http://localhost:5001';

var app = createApp(App)
app.use(router)
app.use(vuetify)
// app.component('star-rating', VueStarRating.default)
app.mount('#app')
