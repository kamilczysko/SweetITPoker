<template>
    <div class='grid grid-rows-nav w-full h-full'>
        <nav class='w-full h-full grid grid-cols-3 items-center'>
            <a href="#" class='w-fit text-left ml-10 hover:text-xl active:mb-1 active:shadow-xl'>logout</a>
            <h1 class='text-4xl text-center'>{{ getRoomName }}</h1>
            <Player :player="getMyPlayer" class='absolute right-10' @setAdmin="setMyAdmin" @setObserver="setMyObserver" />
        </nav>
        <div class='grid 2xl:grid-cols-pokerMain lg:grid-cols-pokerMainSmaller grid-cols-pokerMainEvenSmaller '>
            <div class='grid grid-rows-pokerTable'>
                <GameTable :players="getAllPlayers" />
                <MyCards v-show="!amIObserver" @selectCard="selectCard" />
            </div>
            <div class='h-full grid '>
                <UsersList :players="getPlayersForList" @setAdmin="setAdmin" @setObserver="setObserver"
                    class='overflow-x-auto h-[35rem] scroll-smooth' />
                <div class='flex flex-col items-center justify-center gap-4'>
                    <CustomButton label="Copy link!" class='w-3/4' @clicked="copyToClipboard" />
                    <CustomButton v-if="amIAdmin" label="Reset!" class='w-3/4' @clicked="resetVotes" />
                    <p class="info" id="pokerRoomInfo">Copied!</p>
                </div>
            </div>
        </div>
        <Result v-if="isVotingFinished" :data="resultData" @reset="resetVotes" />
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

export default {
    name: "PokerTableView",
    components: { UsersList, GameTable, MyCards, Player, CustomButton, Result },
    data() {
        return {
            avatars: [],
            resultData: [{ role: "Developer", time: 13.4 },
            { role: "Tester", time: 19.43423 },
            { role: "Wututu", time: 25.54423 }],
            client: null
        }
    },
    methods: {
        prepareAvatars() {
            return Object.values(avatars)
                .map(a => a.default).sort()
        },
        setObserver() {

        },
        setAdmin() {

        },
        setMyAdmin(data) {
            this.$store.commit("setAdmin", data.isAdmin)
        },
        setMyObserver(data) {
            this.$store.commit("setObserver", data.isObserver)
        },
        resetVotes() {
            this.$store.commit("setVotingFinished", false)
            // this.$store.commit("cleanVotes")
            this.client.send("/app/room", JSON.stringify({
                roomId: this.$store.state.roomId,
                resetAllVotes: true
            }))
        },
        copyToClipboard() {
            const url = navigator.clipboard.writeText(window.location.origin + "/join/" + this.$store.state.roomId)
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
        onConnected() {
            this.client = new StompClient("/poker")
            this.client.subscribe("/topic/room/" + this.$store.state.roomId, (data) => {
                const roomData = JSON.parse(data.body)
                this.$store.commit("setPlayers", Array.from(roomData.players))
            })
        },
        selectCard() {
            this.client.send('/app/room',
                JSON.stringify({
                    roomId: this.$store.state.roomId,
                    modifiedPlayer:
                        Array.from(this.$store.state.players).filter(p => p.id == this.$store.state.myId)[0]
                }))
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
            console.log("My player")
            console.log(Array.from(this.$store.state.players).filter(p => p.id == this.$store.state.myId)[0])
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