<template>
    <div class='flex items-center flex-wrap justify-center gap-4'>
        <div v-for="player in getPlayers" :key="player.uid" class='flex flex-col items-center w-1/12 h-2/6 aspect-card'>
            <Card :data="player.selectedCard" :isVisible="showCards" :isSelected="player.selectedCard != null"
                :isUserSelection="true" class='w-full h-full'/>
            <p>{{ player.name }}</p>
        </div>
    </div>
</template>
<script>
import Card from './Card.vue';
export default {
    name: "GameTable",
    props: ["players"],
    components: { Card },
    data() {
        return {
            savedState: []
        }
    },
    methods: {

    },
    computed: {
        getPlayers() {
            const isVotingFinished = this.$store.state.isVotingFinished
            this.savedState = this.players.filter(p => !p.isObserver || (p.selectedCard != null && isVotingFinished))
            return this.savedState
        },
        showCards() {
            this.savedState = this.players
            const isDone = this.players
                .filter(p => !p.isObserver)
                .filter(p => p.selectedCard == null).length == 0
            this.$store.commit("setVotingFinished", isDone)
            return isDone
        }
    },
    created() {
        this.savedState = this.players.filter(p => !p.isObserver)
    }
}
</script>
<style lang="">
    
</style>