<template>
    <nav class="flex items-center justify-between gap-3 bg-slate-700 bg-opacity-30 backdrop-blur-sm xs:mt-1 md:mt-0 px-2 relative">
        <LogoutButton class="z-50" @clicked="clicked"></LogoutButton>
        <ControlPanel class="xs:flex md:hidden"></ControlPanel>
        <div class="flex justify-center flex-col items-center gap-0 self-center w-fit xs:hidden lg:flex absolute inset-0 mx-auto text-white font-main">
            <h1 class="font-extrabold xs:text-10 md:text-3xl text-center">{{ this.$store.state.roomSettings.name }}</h1>
            <span class="xs:text-6 xl:text-xs italic">Cutest Agile Planning Poker</span>
        </div>
        <img src="../assets/logo/uwustimates-bg-mlg.png" class="xs:w-10 md:w-12 lg:w-16 mr-3 aspect-square xs:hidden md:block relative">
        <div class="xs:block md:hidden flex justify-center cursor-pointer hover:font-extrabold" @click="togglePlayersList">
            <img src="../assets/logo/uwustimates-bg-mlg.png" class="xs:w-10 md:w-12 lg:w-16 mr-3 aspect-square">
            <p class="font-secondary text-xs font-extralight absolute -bottom-2 right-4 text-white">☰</p>
        </div>
    </nav>
</template>
<script>
import LogoutButton from './controls/LogoutButton.vue';
import ControlPanel from './controls/ControlPanel.vue';
export default {
    name: "RoomHeader",
    components: { LogoutButton, ControlPanel },
    methods: {
        togglePlayersList() {
            this.$emit("togglePlayerList")
        },
        clicked() {
            this.$emit("clicked")
        }
    }
}
</script>