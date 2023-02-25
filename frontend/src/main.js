import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Vuex from 'vuex'
import VuexPersistence from 'vuex-persist'

import './assets/main.css'

const store = new Vuex.Store({
    state() {
        return {
            isVotingFinished: false,
            roomId: null,
            myId: null,
            roomName: null,
            players: []
        }
    },
    mutations: {
        setVotingFinished(state, votingState) {
            state.isVotingFinished = votingState
        },
        initNewRoom(state, newRoomData) {
            state.roomId = newRoomData.roomId
            state.roomName = newRoomData.roomName
            state.myId = newRoomData.playerId
        },
        setPlayers(state, players) {
            state.players = players
        },
        selectCard(state, cardData) {
            state.players.filter(p => p.id == state.myId)
                .forEach(p => p.selectedCard = cardData)
        },
        cleanVotes(state) {
            state.players.forEach(p => p.selectedCard = null)
        },
        setAdmin(state, isAdmin) {
            state.players.filter(p => p.id == state.myId)
                .forEach(p => p.isAdmin = isAdmin)
        },
        setObserver(state, isObserver) {
            state.players.filter(p => p.id == state.myId)
                .forEach(p => p.isObserver = isObserver)
        },
        join(state, data) {
            state.myId = data.playerId
            state.roomId = data.roomId
        },
        setRoomName(state, name) {
            state.roomName = name
        },
        clearData(state) {
            state.isVotingFinished = false
            state.roomId = null
            state.myId = null
            state.roomName = null
            state.players = []
        },
        setPlayerAdmin(state, data) {
            state.players.filter(p => p.id == data.player)
                .forEach(p => p.isAdmin = data.isAdmin)
        },
        setPlayerObserver(state, data) {
            state.players.filter(p => p.id == data.player)
                .forEach(p => p.isObserver = data.isObserver)
        }
    },
    plugins: [new VuexPersistence().plugin]
})


const app = createApp(App)

app.use(router)
app.use(store)
app.mount('#app')
