<template>
    <div class="bg-background-first w-screen h-screen bg-cover flex flex-col justify-between">
        <RoomHeader @togglePlayerList="togglePlayerList()" @clicked="leave"></RoomHeader>
        <div
            class="grid xs:grid-cols-1 md:grid-cols-roomView_md xl:grid-cols-roomView h-[70vh] bg-slate-700 bg-opacity-30 backdrop-blur-sm xs:overflow-y-scroll scrollbar-hide">
            <div class="rounded-r-xl flex justify-center items-center scrollbar-hide">
                <GameTable></GameTable>
                <Result v-if="showResult" isAdmin="this.$store.getters.admin" @closeResult="closeResult"
                    @reset="sendResetRoom"></Result>
            </div>
            <PlayerList class="xs:hidden md:block scrollbar-hide"></PlayerList>
            <div class="absolute w-full overflow-y-scroll scrollbar-hide h-[70vh] z-50 bg-slate-900 bg-opacity-60 xs:block md:hidden"
                v-if="showPlayerList">
                <CustomButton label="Close" class="sticky top-0 z-50" @clicked="togglePlayerList"></CustomButton>
                <PlayerList></PlayerList>
            </div>
        </div>
        <div
            class="grid grid-cols-roomView h-1/5 bg-slate-700 bg-opacity-30 backdrop-blur-sm overflow-x-scroll scrollbar-hide">
            <div class="flex flex-col justify-center items-center">
                <PlayerDeck class="" :deck="this.$store.state.roomSettings.cardsValues"
                    :units="this.$store.state.roomSettings.units" @select="setlectCard"></PlayerDeck>
            </div>
            <ControlPanel @resetVotes="sendResetRoom" class="xs:hidden md:flex"></ControlPanel>
        </div>
    </div>
</template>
<script>
import RoomHeader from '../components/RoomHeader.vue';
import PlayerDeck from '@/components/game/PlayerDeck.vue'
import GameTable from '@/components/game/GameTable.vue'
import ControlPanel from '../components/controls/ControlPanel.vue';
import PlayerList from '@/components/player/PlayerList.vue'
import CustomButton from '../components/controls/CustomButton.vue';
import Result from '../components/Result.vue';
import StompClient from '../StompClient.js';
import axios from 'axios'
import { storeKey } from 'vuex';

export default {
    name: "RoomView",
    components: { RoomHeader, PlayerDeck, GameTable, ControlPanel, PlayerList, CustomButton, Result },
    data() {
        return {
            showPlayerList: false,
            showResult: false
        }
    },
    methods: {
        togglePlayerList() {
            this.showPlayerList = !this.showPlayerList
        },
        setlectCard(data) {
            this.client.send('/app/player',
                JSON.stringify({
                    playerId: this.$store.state.playerId,
                    cardValue: data.card,
                    unit: data.unit
                }))
        },
        connect() {
            this.client = new StompClient("/poker")
            const subscriptions = [
                {
                    path: "/topic/room/" + this.$store.state.roomId,
                    callback: (data) => this.updateRoom(JSON.parse(data.body))
                },
                {
                    path: "/topic/room/result/" + this.$store.state.roomId,
                    callback: (data) => this.setResult(JSON.parse(data.body))
                },
                {
                    path: "/topic/room/reset/" + this.$store.state.roomId,
                    callback: () => this.reset()
                }
            ]
            this.client.bulkSubscribe(subscriptions)
        },
        updateRoom(data) {
            const amIRemoved = data.filter(player => player.id == this.$store.state.playerId).length <= 0
            if (amIRemoved) {
                this.logout()
                return
            }
            this.$store.commit("refreshState", data)
        },
        setResult(data) {
            this.$store.commit('saveResult', data)
            this.showResult = true
        },
        reset() {
            this.$store.commit("resetResult")
            this.showResult = false
        },
        sendResetRoom() {
            axios.post('/game/reset/' + this.$store.state.roomId)
                .catch(error => console.log(error))
        },
        closeResult() {
            this.showResult = false
        },
        logout() {
            this.$router.push({ name: "start" })
        },
        leave() {
            axios.delete('/game/delete/' + this.$store.state.playerId)
                .catch(error => console.log(error))
            this.$router.push({ name: "start" })
        }
    },
    created() {
        
        this.connect()

        window.addEventListener('focus', (event) => {
            if (!this.client.isConnected()) {
                console.log("return focus")
                this.connect()

                axios.get('/room/' + this.$store.state.roomId)
                    .then(result => result.data)
                    .then(data => this.updateRoom(data))
                    .catch(error => console.log(error))

                const shouldGetResult = this.$store.state.players.filter(p => !p.isObserver).filter(p => p.selectedCard == null).length == 0
                if (shouldGetResult) {
                    axios.get('/room/result/' + this.$store.state.roomId)
                        .then(result => result.data)
                        .then(data => this.setResult(data))
                        .catch(error => console.log(error))
                }

            }
        });

        this.showResult = this.$store.state.result != null
    },
    mounted() {
        // axios.defaults.baseURL = 'http://localhost:8080';
    }
    
}
</script>