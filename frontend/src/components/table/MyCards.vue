<template>
    <div class='grid grid-cols-deck gap-2 p-2 items-center'>
            <Card v-for="card in getDeck" :key="card.id" :data="card" :isVisible="true"/>
    </div>
</template>
<script>
import Card from './Card.vue';
import deck from '../../assets/carddeck';
export default {
    name: "MyCards",
    components: { Card },
    methods: {

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