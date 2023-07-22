<template>
    <div
        class="w-full flex justify-center items-center xs:gap-2 md:gap-5 flex-wrap m-auto p-5 rounded-r-lg scrollbar-hide overflow-y-scroll h-full">
        <div v-for="player in nonObservers" :key="player.id">
            <ReversCard v-if="this.$store.state.result == null" :data="player"></ReversCard>
            <FrontCard v-else :unit="player.selectedUnit" :value="player.selectedCard"></FrontCard>
            <p
                class="pt-3 text-center xs:text-sm font-secondary font-light md:text-md text-white hover:font-extrabold flex flex-col">
                {{ player.name }}
                <span class="font-secondary font-extralight text-white text-[10px]">({{ getRoleLabel(player.role) }})</span>
            </p>
        </div>
    </div>
</template>
<script>
import ReversCard from '../card/ReversCard.vue';
import FrontCard from '../card/FrontCard.vue';
import roles from '../../assets/roles.js';
export default {
    name: "GameTable",
    components: { ReversCard, FrontCard },
    computed: {
        nonObservers() {
            return this.$store.state.players.filter(player => !player.isObserver)
        }
    },
    methods: {
        getRoleLabel(roleId) {
            return Array.from(roles).filter(role => role.value == roleId)[0].label
        },
    }
}
</script>