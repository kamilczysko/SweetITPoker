<template>
    <div class='grid grid-rows-nav w-full h-full'>
        <nav class='w-full h-full grid grid-cols-3 items-center'>
            <a href="#" class='w-fit text-left ml-10 hover:text-xl active:mb-1 active:shadow-xl'>logout</a>
            <h1 class='text-4xl text-center'>{{ getRoomName }}</h1>
            <Player :player="getMyPlayer" class='absolute right-10' @setAdmin="setAdmin" @setObserver="setObserver" />
        </nav>
        <div class='grid 2xl:grid-cols-pokerMain lg:grid-cols-pokerMainSmaller grid-cols-pokerMainEvenSmaller '>
            <div class='grid grid-rows-pokerTable'>
                <GameTable :players="getAllPlayers" />
                <MyCards v-show="!getMyPlayer.isObserver" />
            </div>
            <div class='h-full grid '>
                <UsersList :palyers="getPlayersForList" @setAdmin="setAdmin" @setObserver="setObserver"
                    class='overflow-x-auto h-[35rem] scroll-smooth' />
                <div class='flex flex-col items-center justify-center gap-4'>
                    <CustomButton label="Copy link!" class='w-3/4' @clicked="copyToClipboard"/>
                    <CustomButton v-if="getMyPlayer.isAdmin" label="Reset!" class='w-3/4' @clicked="resetVotes" />
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
            { role: "Wututu", time: 25.54423 }]
        }
    },
    methods: {
        prepareAvatars() {
            return Object.values(avatars)
                .map(a => a.default).sort()
        },
        setAdmin(data) {
            this.$store.commit("setAdmin", data.isAdmin)
        },
        setObserver(data) {
            console.log(this.$store.state.players)
            this.$store.commit("setObserver", data.isObserver)
            console.log(this.$store.state.players)
        },
        resetVotes() {
            this.$store.commit("setVotingFinished", false)
            this.$store.commit("cleanVotes")
        },
        copyToClipboard() {
            document.getElementById("pokerRoomInfo").classList.remove("hideninfo")
            document.getElementById("pokerRoomInfo").classList.add("visibleinfo")
            setTimeout(() => { document.getElementById("pokerRoomInfo").classList.add("hideninfo") }, 1000);
        },
        onConnected() {
            console.log("Connect...")
            const connection =  new StompClient("/poker")
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