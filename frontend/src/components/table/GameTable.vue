<template>
    <div class='flex items-center flex-wrap justify-center gap-4'>
        <div v-for="player in getPlayers" :key="player.uid" class='flex flex-col items-center w-1/12 h-1/3'>
            <Card :data="player.selectedCard" :isVisible="showCards" :isSelected="player.selectedCard != null" class='w-full h-full'/>
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
            if (isVotingFinished) {
                const idsFromPreviousState = Array.from(this.savedState).map( player => player.id)
                this.savedState = this.players.filter(player => idsFromPreviousState.includes(player.id))
                return this.savedState
            }
            this.savedState = this.players.filter(p => !p.isObserver)
            return this.savedState
        },
        showCards() {
            return this.$store.state.isVotingFinished
        }
    },
    created() {
        this.savedState = this.players.filter(p => !p.isObserver)
    }
}
</script>