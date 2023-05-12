<template>
    <div class='flex justify-between items-center overflow-scroll overflow-x-scroll ml-5'>
        <PlayerCard v-for="card in getDeck" :isSelected="getSelectedCard == card.cardId" :key="card.id" :data="card" v-on:selectCard="selectCard" />
    </div>
</template>
<script>
import PlayerCard from './PlayerCard.vue';
import deck from '../../assets/carddeck';
export default {
    name: "MyCards",
    components: { PlayerCard },
    methods: {
        selectCard(data) {
            if (this.$store.state.isVotingFinished) {
                return
            }
            this.$store.commit("selectCard", data)
            this.selectedCard = Array.from(this.$store.state.players)
                .filter(p => p.id == this.$store.state.myId)
                .map(p => p.selectedCard)[0].cardId
            this.$emit("selectCard", data)
        }
    },
    computed: {
        getDeck() {
            return deck
        },
        getSelectedCard() {
            const selectedCard = Array.from(this.$store.state.players)
                .filter(p => p.id == this.$store.state.myId)
                .map(p => p.selectedCard)[0]
            if(selectedCard == null) {
                return null
            }
            return selectedCard.cardId
        }
    }
}
</script>
<style lang="">
    
</style>