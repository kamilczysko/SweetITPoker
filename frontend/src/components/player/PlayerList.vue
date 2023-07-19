<template>
    <div class="pl-2 h-ful bg-slate-700 bg-opacity-30 backdrop-blur-sm overflow-y-scroll scrollbar-hide rounded-lg rounded-tl-[30px] relative">
        <div class="shadow-2xl shadow-fuchsia-900 rounded-l-full top-0 sticky z-50">
            <Player :data="getMyPlayerData" class="bg-opacity-80 bg-fuchsia-800 backdrop-blur-2xl "
            @toggleAdmin="toggleAdmin" @toggleObserver="toggleObserver" @remove="remove"></Player>
        </div>
        <div>
            <Player v-for="player in getPlayers" :key="player.id" :data="player"
            @toggleAdmin="toggleAdmin" @toggleObserver="toggleObserver" @remove="remove"></Player>
        </div>
    </div>
</template>
<script>
import Player from './Player.vue';
import axios from 'axios';
export default {
    name: "PlayerList",
    components: { Player },
    methods: {
        toggleAdmin(id) {
            axios.post('/player/update/toggle/admin/' + id)
                .catch(error => console.log(error))
        },
        toggleObserver(id) {
            axios.post('/player/update/toggle/observer/' + id)
                .catch(error => console.log(error))
        },
        remove(id) {
            this.removePlayer(id)
        },
        removePlayer(id) {
            axios.delete('/game/delete/' + id)
                .catch(error => console.log(error))
        }
    },
    computed: {
        getPlayers() {
            return this.$store.state.players.filter(player => player.id != this.$store.state.playerId)
        },
        getMyPlayerData() {
            return this.$store.state.players.filter(player => player.id == this.$store.state.playerId)[0]
        }
    }
}
</script>