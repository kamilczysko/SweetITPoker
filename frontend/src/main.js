import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Vuex from 'vuex'
import VuexPersistence from 'vuex-persist'
import axios from 'axios'
import VueAxios from 'vue-axios'
import { VueReCaptcha } from 'vue-recaptcha-v3'

import './assets/main.css'

const store = new Vuex.Store({
    state() {
        return {
            roomId: null,
            playerId: null,
            roomSettings: null,
            players: [],
            result: null,
            cookiesAccepted: false,
            playWithSP: false
        }
    },
    mutations: {
        saveSPSetting(state, value) {
          state.playWithSP = value  
        },
        cleanup(state) {
            state.roomId = null
            state.playerId = null
            state.roomSettings = null
            state.players = []
            state.result = null
        },
        saveResult(state, result) {
            state.result = result
        },
        joinRoom(state, data) {
            state.playerId = data.playerId
            state.roomId = data.roomId
        },
        loadRoom(state, data) {
            state.roomSettings = {
                cardsValues: data.cardsValues,
                units: data.units,
                name: data.name
            }
            state.players = data.players
        },
        refreshState(state, data) {
            state.players = data
        },
        initRoom(state, data) {
            state.roomId = null;
            state.playerId = null;
            state.roomSettings = null;
            state.players = [];

            state.playerId = data.ids.playerId
            state.roomId = data.ids.roomId
            state.roomSettings = {
                cardsValues: data.data.cardsValues,
                units: data.data.units,
                name: data.data.name
            }
            const admin = {
                id: data.ids.playerId,
                name: data.data.roomCreator.name,
                avatarIdx: data.data.roomCreator.avatarIdx,
                isObserver: false,
                isAdmin: true,
                selectedCard: null,
                selectedUnit: null,
                role: data.data.roomCreator.role
            }
            state.players.push(admin)
        },
        resetResult(state) {
            state.result = null
        },
        acceptCookies(state) {
            state.cookiesAccepted = true
        }
    },getters: {
        selectedCard(state) {
            return state.players.filter(player => player.id == state.playerId)[0].selectedCard
        },
        selectedUnit(state) {
            return state.players.filter(player => player.id == state.playerId)[0].selectedUnit
        },
        admin(state){
            return state.players.filter(player => player.id == state.playerId)[0].isAdmin
        },
        observer(state){
            return state.players.filter(player => player.id == state.playerId)[0].isObserver
        }
    },  
    plugins: [new VuexPersistence().plugin]
})


const app = createApp(App)
app.use(router)
app.use(store)
app.use(VueAxios, axios)
app.use(VueReCaptcha, { siteKey: import.meta.env.VITE_CAPTCHA_SITE })
app.mount('#app')
