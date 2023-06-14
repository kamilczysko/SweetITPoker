<template>
        <div class='flex flex-col justify-between items-stretch'>
            <nav class='w-screen h-[12vh] flex justify-between items-center bg-black bg-opacity-30 backdrop-blur-sm'>
                <CustomButton label="Logout" @clicked="logoutSelf" class='xs:w-[15vw] lg:w-[10vw] ml-10'>
                </CustomButton>
                <h1 class='text-4xl text-main-font xs:hidden md:block'>{{ getRoomName }}</h1>
                <Player :player="getMyPlayer" class='place-self-center justify-self-end mr-3' @setObserver="setObserver" />
            </nav>
            <div class='xs:flex xs:items-center xs:justify-center md:grid md:grid-cols-[85%_15%]'>
                <div class='flex flex-col justify-center items-center'>
                    <GameTable :players="getAllPlayers" />
                </div>
                <transition name="slide-right" appear>
                    <UsersList :isAdmin="amIAdmin" :players="getPlayersForList" @leave="logoutPlayer" @setAdmin="setAdmin"
                        @setObserver="setObserver"
                        class='xs:hidden md:block h-[70vh] overflow-scroll overflow-y-auto scroll-smooth scrollbar-hide scroll-m-4' />
                </transition>
            </div>
            <div class='grid grid-cols-[90%_10%] bg-black backdrop-blur-sm bg-opacity-20 absolute bottom-0 w-screen'>
                <div class='flex items-center justify-center py-1'>
                    <transition name="slide-bottom" appear>
                        <MyCards v-show="!amIObserver" @selectCard="selectCard" class='w-full' />
                    </transition>
                </div>
                <div class='xs:hidden sm:flex flex-col justify-start items-center gap-5 mx-5 mt-0 h-full pt-3'>
                    <CustomButton label="Copy link!" class='w-full' @clicked="copyToClipboard" />
                    <CustomButton v-if="amIAdmin" label="Reset!" class='w-full' @clicked="resetVotes" />
                    <CustomButton label="Refresh" class='w-full' @clicked="initRoom" />
                    <p class="info" id="pokerRoomInfo">Copied!</p>
                </div>
            </div>
            <transition name="appear">
                <Result v-if="isVotingFinished" :isAdmin="amIAdmin" :data="resultData" @reset="resetVotes" />
            </transition>
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
        },
        logoutSelf() {
            this.logoutPlayer(this.$store.state.myId)
            this.$router.push('/')
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

.slide-right-enter-from {
    opacity: 0;
    transform: translateX(200px);
}

.slide-right-enter-to {
    transition: all 0.3s ease;
}

.slide-bottom-enter-from {
    opacity: 0;
    transform: translateY(200px);
}

.slide-bottom-enter-to {
    transition: all 0.3s ease;
}

.appear-enter-from {
    opacity: 0;
}

.appear-enter-to {
    transition: opacity 0.5s ease;
}
</style>