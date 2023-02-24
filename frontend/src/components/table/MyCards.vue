<template>
    <div class='grid grid-cols-deck p-1 items-center'>
        <Card v-for="card in getDeck" :isSelected="getSelectedCard == card.id" :key="card.id" :data="card" :isVisible="true"
            v-on:selectCard="selectCard" />
    </div>
</template>
<script>
import Card from './Card.vue';
import deck from '../../assets/carddeck';
export default {
    name: "MyCards",
    components: { Card },
    methods: {
        selectCard(data) {
            if (this.$store.state.isVotingFinished) {
                return
            }
            this.$store.commit("selectCard", data)
            this.selectedCard = Array.from(this.$store.state.players)
                .filter(p => p.id == this.$store.state.myId)
                .map(p => p.selectedCard)[0].id
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