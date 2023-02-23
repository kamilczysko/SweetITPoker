<template>
    <div class='grid grid-cols-deck gap-2 p-2 items-center'>
            <Card v-for="card in getDeck" :isSelected="selectedCard == card.id" :key="card.id" :data="card" :isVisible="true" v-on:selectCard="selectCard"/>
    </div>
</template>
<script>
import Card from './Card.vue';
import deck from '../../assets/carddeck';
export default {
    name: "MyCards",
    components: { Card },
    data() {
        return {
            selectedCard: null
        }
    },
    methods: {
        selectCard(id) {
                this.selectedCard = id
            }
    },
    computed: {
        getDeck() {
            return deck
        }
    }
}
</script>
<style lang="">
    
</style>