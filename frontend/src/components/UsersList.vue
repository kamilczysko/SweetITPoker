<template>
    <div class='mt-5'>
            <Player class='absoulte' v-for="player in roomState" :key="player.id" :player="player" @setAdmin="setAdmin" @setObserver="setObserver"/>
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
            if (this.$store.state.isVotingFinished) {
                return
            }
            this.$emit("setAdmin", data)
        },
        setObserver(data) {
            if (this.$store.state.isVotingFinished) {
                return
            }
            this.$emit("setObserver", data)
        }
    }
}
</script>
<style lang="">
    
</style>