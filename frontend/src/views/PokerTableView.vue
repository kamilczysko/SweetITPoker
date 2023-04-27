<template>
    <div class='grid grid-rows-nav w-full h-full'>
        <nav class='w-full h-full grid grid-cols-3 items-center'>
            <div class='w-24 ml-6'>
                <CustomButton label="Logout" @clicked="logoutPlayer(this.$store.state.myId)"></CustomButton>
            </div>
            <h1 class='text-4xl text-center'>{{ getRoomName }}</h1>
            <Player :player="getMyPlayer" class='absolute right-10' @setObserver="setObserver" />
        </nav>
        <div class='grid 2xl:grid-cols-pokerMain lg:grid-cols-pokerMainSmaller grid-cols-pokerMainEvenSmaller '>
            <div class='grid grid-rows-pokerTable'>
                <GameTable :players="getAllPlayers" />
                <MyCards v-show="!amIObserver" @selectCard="selectCard" />
            </div>
            <div class='h-full grid '>
                <UsersList :isAdmin="amIAdmin" :players="getPlayersForList" @leave="logoutPlayer" @setAdmin="setAdmin"
                    @setObserver="setObserver" class='overflow-x-auto h-[35rem] scroll-smooth' />
                <div class='flex flex-col items-center justify-center gap-4'>
                    <CustomButton label="Copy link!" class='w-3/4' @clicked="copyToClipboard" />
                    <CustomButton v-if="amIAdmin" label="Reset!" class='w-3/4' @clicked="resetVotes" />
                    <CustomButton label="Refresh" class='w-3/4' @clicked="initRoom" />
                    <p class="info" id="pokerRoomInfo">Copied!</p>
                </div>
            </div>
        </div>
        <Result v-if="isVotingFinished" :isAdmin="amIAdmin" :data="resultData" @reset="resetVotes" />
    </div>
</template>
<script>

const avatars = import.meta.globEager('../assets/avatars/*.png')

import UsersList from '../components/UsersList.vue'
import MyCards from '../components/table/MyCards.vue'
import GameTable from '../components/table/GameTable.vue'
import Player from '../components/Player.vue'
import CustomButton from '../components/CustomButton.vue'
import Result from '../components/Result.vue'
import StompClient from '../StompClient.js'
import axios from 'axios'

export default {
    name: "PokerTableView",
    components: { UsersList, GameTable, MyCards, Player, CustomButton, Result },
    data() {
        return {
            avatars: [],
            resultData: [],
            client: null
        }
    },
    methods: {
        prepareAvatars() {
            return Object.values(avatars)
                .map(a => a.default).sort()
        },
        setObserver(data) {
            if (this.amIAdmin || data.player == this.$store.state.myId) {
                this.$store.commit("setPlayerObserver", data)
                this.sendPlayerInfo(data.player)
            }
        },
        setAdmin(data) {
            if (this.amIAdmin) {
                this.$store.commit("setPlayerAdmin", data)
                this.sendPlayerInfo(data.player)

            }
        },
        logoutPlayer(playerId) {
            this.client.send("/app/leave", JSON.stringify({
                playerId: playerId,
                roomId: this.$store.state.roomId
            }))
            this.$router.push({ name: "join", params: { id: this.$store.state.roomId } })
        },
        sendPlayerInfo(playerId) {
            if (this.$store.state.isVotingFinished) {
                const player = Array.from(this.$store.state.players).filter(p => p.id == playerId)[0]
                axios.post('/rest/room/' + this.$store.state.roomId + '/modify-player', player)
                return
            }
            this.client.send('/app/room',
                JSON.stringify({
                    roomId: this.$store.state.roomId,
                    modifiedPlayer:
                        Array.from(this.$store.state.players).filter(p => p.id == playerId)[0]
                }))
        },
        resetVotes() {
            this.$store.commit("setVotingFinished", false)
            this.client.send("/app/room", JSON.stringify({
                roomId: this.$store.state.roomId,
                resetAllVotes: true
            }))
        },
        copyToClipboard() {
            const url = window.location.origin + "/join/" + this.$store.state.roomId
            this.unsecuredCopyToClipboard(url)
            document.getElementById("pokerRoomInfo").classList.remove("hideninfo")
            document.getElementById("pokerRoomInfo").classList.add("visibleinfo")
            setTimeout(() => { document.getElementById("pokerRoomInfo").classList.add("hideninfo") }, 1000);
        },
        unsecuredCopyToClipboard(text) {
            const textArea = document.createElement("textarea");
            textArea.value = text;
            document.body.appendChild(textArea);
            textArea.focus();
            textArea.select();
            try {
                document.execCommand('copy');
            } catch (err) {
                console.error('Unable to copy to clipboard', err);
            }
            document.body.removeChild(textArea);
        },
        updateRoom(data) {
            const roomData = JSON.parse(data.body)
            const amILoggedOut = Array.from(roomData.players).filter(player => player.id == this.$store.state.myId).length == 0
            if (amILoggedOut) {
                this.$router.push("/")
                return
            }

            this.$store.commit("setPlayers", Array.from(roomData.players))

            if (!this.isVotingFinished) {
                this.showResult = false
            }

        },
        fetchScore(data) {
            const res = JSON.parse(data.body)
            if (res.length == 0) {
                this.$store.commit("setVotingFinished", false)
                return
            }
            this.resultData = res
            this.$store.commit("setVotingFinished", true)
        },
        onConnected() {
            this.client = new StompClient("/poker")
            const subscriptions = [
                {
                    path: "/topic/room/" + this.$store.state.roomId,
                    callback: (data) => this.updateRoom(data)
                },
                {
                    path: "/topic/room/result/" + this.$store.state.roomId,
                    callback: (data) => this.fetchScore(data)
                }
            ]
            this.client.bulkSubscribe(subscriptions)
        },
        selectCard() {
            this.client.send('/app/room',
                JSON.stringify({
                    roomId: this.$store.state.roomId,
                    modifiedPlayer:
                        Array.from(this.$store.state.players).filter(p => p.id == this.$store.state.myId)[0]
                }))
        },
        initRoom() {
            axios.get("/rest/room/" + this.$store.state.roomId)
                .then(response => response.data)
                .then(data => {
                    this.$store.commit("setRoomName", data.roomName)
                    this.$store.commit("setPlayers", Array.from(data.players))
                    this.refreshResult()
                })
                .catch(error => this.errorMessage = "Init room error! " + error)
        },
        refreshResult() {
            const nonVoters = Array.from(this.$store.state.players)
                .filter(player => player.isObserver == false)
                .filter(player => player.selectedCard == null)
                .length
            this.$store.commit("setVotingFinished", (nonVoters == 0))
        }
    },
    computed: {
        isVotingFinished() {
            return this.$store.state.isVotingFinished
        },
        getRoomName() {
            return this.$store.state.roomName
        },
        getMyPlayer() {
            return Array.from(this.$store.state.players).filter(p => p.id == this.$store.state.myId)[0]
        },
        getPlayersForList() {
            return Array.from(this.$store.state.players).filter(p => p.id != this.$store.state.myId).sort()
        },
        getAllPlayers() {
            return Array.from(this.$store.state.players)
        },
        amIObserver() {
            const player = Array.from(this.$store.state.players).filter(p => p.id == this.$store.state.myId)
            if (player.length == 0) {
                return true
            }
            return player[0].isObserver
        },
        amIAdmin() {
            const player = Array.from(this.$store.state.players).filter(p => p.id == this.$store.state.myId)
            if (player.length == 0) {
                return true
            }
            return player[0].isAdmin
        }
    },
    created() {
        this.avatars = this.prepareAvatars
        this.onConnected()
        this.initRoom()

        window.addEventListener('focus', (event) => {
            if (!this.client.isConnected()) {
                this.onConnected()
                this.initRoom()
            }
        });
    }
}
</script>
<style scoped>
.info {
    visibility: hidden;
}

.visibleinfo {
    visibility: visible;
}

.hideninfo {
    opacity: 0;
    transition: visibility .5s .5s, opacity .5s ease;
}
</style>