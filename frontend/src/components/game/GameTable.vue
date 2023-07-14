<template>
    <div class="w-full flex justify-center items-center xs:gap-2 md:gap-5 flex-wrap m-auto p-5 rounded-r-lg scrollbar-hide overflow-y-scroll h-full">
        <ReversCard v-if="this.$store.state.result == null" v-for="player in nonObservers" :key="player.id" :data="player"></ReversCard>
        <FrontCard v-else v-for="player in nonObservers" :unit="player.selectedUnit" :value="player.selectedCard"></FrontCard>
    </div>
</template>
<script>
import ReversCard from '../card/ReversCard.vue';
import FrontCard from '../card/FrontCard.vue';
export default {
    name: "GameTable",
    components: { ReversCard, FrontCard },
    computed: {
        nonObservers() {
            return this.$store.state.players.filter(player => !player.isObserver)
        }
    }
}
</script>