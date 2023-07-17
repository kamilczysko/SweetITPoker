<template>
    <ul class="bg-pink-400 md:w-[10vw] xs:w-[25vw] bg-opacity-75 p-1 flex flex-col justify-center items-center rounded-lg md:text-sm scrollbar-hide" v-on:mouseleave="mouseleave">
        <li class="cursor-pointer px-3 py-1 hover:bg-red-500 hover:text-white hover:bg-opacity-80 hover:scale-105 active:scale-110 w-full rounded-l" @click="toggleAdmin" v-if="this.$store.getters.admin  && this.$store.state.playerId != playerId">
            <p class="text-center"><span v-if="isAdmin">✓</span> admin</p>
        </li>
        <li class="cursor-pointer px-3 py-1 hover:bg-red-500 hover:text-white hover:bg-opacity-80 hover:scale-105 active:scale-110 w-full rounded-lg" @click="toggleObserver">
            <p class="text-center"><span v-if="isObserver">✓</span> observer</p>
        </li>
        <li @click="remove" class="cursor-pointer px-3 py-1 hover:bg-red-500 hover:text-white hover:bg-opacity-90 hover:scale-105
         active:scale-110 flex items-center justify-center gap-2 w-full rounded-lg" v-if="this.$store.getters.admin && this.$store.state.playerId != playerId">
            <img src="../../assets/bin.svg" class="w-3">
            <p class="text-center">remove</p>
        </li>
    </ul>
</template>
<script>
export default {
    name: "PlayerSettingsMenu",
    props: {
        isAdmin: { type: Boolean, default: false },
        isObserver: { type: Boolean, default: false },
        playerId: {type: String}
    },
    methods: {
        mouseleave() {
            this.$emit("mouseleave")
        },
        toggleAdmin() {
            this.$emit("toggleAdmin")
        },
        toggleObserver() {
            this.$emit("toggleObserver")
        },
        remove() {
            this.$emit("remove")
        }
    }
}
</script>