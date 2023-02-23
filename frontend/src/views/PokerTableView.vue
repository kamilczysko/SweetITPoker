<template>
    <div class='grid grid-rows-nav w-full h-full'>
        <nav class='w-full h-full bg-slate-500 grid grid-cols-3 items-center'>
            <a href="#" class='w-fit text-left ml-10 hover:text-xl active:mb-1 active:shadow-xl'>logout</a>
            <h1 class='text-4xl text-center'>{{ roomName }}</h1>
            <Player :player="roomState[0]" class='absolute right-10' @setAdmin="setAdmin" @setObserver="setObserver" />
        </nav>
        <div class='grid 2xl:grid-cols-pokerMain lg:grid-cols-pokerMainSmaller grid-cols-pokerMainEvenSmaller '>
            <div class='grid grid-rows-pokerTable'>
                <GameTable :players="roomState"/>
                <MyCards />
            </div>
            <div class='h-full grid '>
                <UsersList :roomState="roomState" @setAdmin="setAdmin" @setObserver="setObserver" class='overflow-x-auto h-[35rem] scroll-smooth' />
                <div class='flex flex-col items-center justify-center gap-4'>
                    <CustomButton label="Copy link!" class='w-3/4' @clicked="copyLink" />
                    <CustomButton label="Reset!" class='w-3/4' @clicked="resetVotes"/>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
const avatars = import.meta.globEager('../assets/avatars/*.png')

import UsersList from '../components/UsersList.vue'
import MyCards from '../components/table/MyCards.vue'
import GameTable from '../components/table/GameTable.vue'
import Player from '../components/Player.vue'
import CustomButton from '../components/CustomButton.vue'
export default {
    name: "PokerTableView",
    components: { UsersList, GameTable, MyCards, Player, CustomButton },
    data() {
        return {
            roomName: "R00m",
            roomState: [
                { uid: 1, playerName: "czesiek", selectedCard: null, avatar: 1, role: "developer", isAdmin: false, isObserver: true },
                { uid: 2, playerName: "grzesiek", selectedCard: { value: 4, unit: "h" }, avatar: 2, role: "developer", isAdmin: false, isObserver: false },
                { uid: 3, playerName: "franciszek", selectedCard: null, avatar: 11, role: "developer", isAdmin: false, isObserver: false },
                { uid: 4, playerName: "pczemek", selectedCard: { value: 5, unit: "d" }, avatar: 21, role: "tester", isAdmin: false, isObserver: true },
                { uid: 5, playerName: "janina", selectedCard: null, avatar: 8, role: "tester", isAdmin: true, isObserver: false },
                { uid: 11, playerName: "czesiek", selectedCard: null, avatar: 1, role: "developer", isAdmin: false, isObserver: true },
                { uid: 21, playerName: "grzesiek", selectedCard: { value: 4, unit: "h" }, avatar: 2, role: "developer", isAdmin: false, isObserver: false },
                { uid: 31, playerName: "franciszek", selectedCard: null, avatar: 11, role: "developer", isAdmin: false, isObserver: false },
                // {uid: 41, playerName: "pczemek", selectedCard: {value: 5, unit: "d"}, avatar:21, role: "tester", isAdmin:false, isObserver: true},
                // {uid: 51, playerName: "janina", selectedCard: null, avatar:8, role: "tester", isAdmin:true, isObserver: false},
                // {uid: 12, playerName: "czesiek", selectedCard: null, avatar:1, role: "developer", isAdmin:false, isObserver: true},
                // {uid: 22, playerName: "grzesiek", selectedCard: {value: 4, unit:"h"}, avatar:2, role: "developer", isAdmin:false, isObserver: false},
                // {uid: 32, playerName: "franciszek", selectedCard: null, avatar:11, role: "developer", isAdmin:false, isObserver: false},
                // {uid: 42, playerName: "pczemek", selectedCard: {value: 5, unit: "d"}, avatar:21, role: "tester", isAdmin:false, isObserver: true},
                // {uid: 52, playerName: "janina", selectedCard: null, avatar:8, role: "tester", isAdmin:true, isObserver: false}
            ],
            avatars: []
        }
    },
    methods: {
        prepareAvatars() {
            return Object.values(avatars)
                .map(a => a.default).sort()
        },
        setAdmin(data) {
            this.roomState.filter(u => u.uid === data.player)
                .forEach(u => u.isAdmin = data.isAdmin)
        },
        setObserver(data) {
            this.roomState.filter(u => u.uid === data.player)
                .forEach(u => u.isObserver = data.isObserver)
        },
        resetVotes() {
            this.roomState.forEach(u => u.selectedCard = null)
        }
    },
    created() {
        this.avatars = this.prepareAvatars
    }

}
</script>
<style lang="">
    
</style>