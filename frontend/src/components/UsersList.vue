<template>
    <div class='mt-3 m-auto'>
            <Player v-for="player in roomState" :key="player.id" :player="player" @setAdmin="setAdmin" @setObserver="setObserver"/>
    </div>
</template>
<script>
import Player from './Player.vue'
export default {
    name: "UsersList",
    components: { Player },
    props: ["roomState"],
    methods: {
        setAdmin(data) {
            this.$emit("setAdmin", data)
        },
        setObserver(data) {
            this.$emit("setObserver", data)
        }
    }
}
</script>
<style lang="">
    
</style>