<template>
    <div class='lg:pl-2 backdrop-blur-sm bg-black bg-opacity-30'>
        <div v-for="player in players" :key="player.id" class='relative'>
            <Player class='absoulte' :player="player" @setAdmin="setAdmin" @setObserver="setObserver" />
            <button v-if="amIAdmin" v-on:click="leave(player.id)" class='absolute top-1'>
                <img class='w-3 h-3 hover:h-4 hover:w-4 active:h-5 active:w-5' src="../assets/bin.svg">
            </button>
        </div>
    </div>
</template>
<script>
import Player from './Player.vue'
export default {
    name: "UsersList",
    components: { Player },
    props: ["players", 'isAdmin'],
    methods: {
        setAdmin(data) {
            this.$emit("setAdmin", data)
        },
        setObserver(data) {
            this.$emit("setObserver", data)
        },
        leave(id) {
            this.$emit("leave", id)
        }
    },
    computed: {
        amIAdmin() {
            return this.isAdmin
        }
    }
}
</script>
<style lang="">
    
</style>