<template>
    <div class='flex items-center flex-wrap justify-center gap-4'>
        <div v-for="player in players" :key="player.uid" class='flex flex-col items-center'>
            <Card :data="player.selectedCard" :isSelected="player.selectedCard!=null" class='w-[6rem] h-[9rem]'/>
            <p>{{ player.playerName }}</p>
        </div>
    </div>
</template>
<script>
import Card from './Card.vue';
export default {
    name: "GameTable",
    props: ["players"],
    components: { Card }
}
</script>
<style lang="">
    
</style>