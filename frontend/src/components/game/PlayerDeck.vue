<template>
    <div class="flex justify-center items-center gap-2">
        <PlayerCard v-for="card in deck" :key="card" :value="card" :custom-units="units" @select="select" v-if="!this.$store.getters.observer"></PlayerCard>
    </div>
</template>
<script>
import PlayerCard from '../card/PlayerCard.vue';
export default {

    name: "PlayerDeck",
    components: { PlayerCard },
    props: {
        deck: {default: []},
        units: {default: []}
    },
    // props: ["deck", "units"],
    methods: {
        select(data) {
            this.$emit("select", data)
        }
    }
}
</script>