<template>
    <div class="max-h-24 w-full pl-4 py-2 flex flex-row xs:justify-center xs:gap-5 md:justify-start xs:items-center md:items-center rounded-l-full cursor-pointer relative hover:shadow-md hover:shadow-black"
        :class="{ ['shadow-highlight-more border-pink-300 border-2 brightness-125 bg-pink-700 bg-opacity-50']: data.selectedCard }">
        <div class="relative inset-0">
            <img :src="getAvatar(data.avatarIdx)"
                class="cursor-pointer hover:shadow-md hover:shadow-black rounded-full resize-none xs:w-10 md:w-12 z-10"
                :class="{ ['shadow-highlight-more']: data.selectedCard, ['blur-[1px]']: data.isObserver }">
            <img src="../../assets/crown.svg" class="w-4 cursor-pointer hover:scale-110 absolute -top-1 -left-1"
                v-if="data.isAdmin">
            <img src="../../assets/glasses.svg" class="w-5 mb-3 cursor-pointer hover:scale-110 absolute -bottom-4 -left-2"
                v-if="data.isObserver">

            <div class="absolute -bottom-3 -right-1 text-white hover:scale-110 active:scale-125 z-20"
                v-on:click="toggleMenu" v-if="this.$store.getters.admin || data.id==this.$store.state.playerId">
                <p>☰</p>
            </div>
            
        </div>
        <div :class="{ ['blur-[1px]']: data.isObserver }" class="w-fit flex flex-col justify-center items-start ">
            <p class="text-white font-main font-extrabold xs:text-xs md:text-sm lg:text-base">{{ data.name }}</p>
            <p class="text-white font-secondary text-xs font-light">{{ getRoleLabel(data.role) }}</p>
            <p class="text-white font-secondary text-xs font-extralight" v-if="data.isAdmin">Senpai (Admin)</p>
        </div>
        <PlayerSettingsMenu v-if="showMenu" class="xs:block md:absolute xs:top-0 xs:overflow-y-scroll h- md:top-14 md:left-12 z-50" :isObserver="data.isObserver"
               :playerId="data.id" :isAdmin="data.isAdmin" @mouseleave="closeMenu" @click="closeMenu" @toggleAdmin="toggleAdmin" @toggleObserver="toggleObserver" @remove="remove">
            </PlayerSettingsMenu>
    </div>
</template>
<script>
import PlayerSettingsMenu from './PlayerSettingsMenu.vue';
const avatars = import.meta.globEager('../../assets/avatars/*.png')
import roles from '../../assets/roles.js'

export default {
    name: "Player",
    components: { PlayerSettingsMenu },
    props: {
        data: { type: Object }
    },
    data() {
        return {
            showMenu: false,
            menuItems: ["admin", "observer"],
            avatars: []
        }
    },
    methods: {
        prepareAvatars() {
            return Object.values(avatars)
                .map(a => a.default)
        },
        getAvatar(index) {
            return "../../" + this.avatars()[index]
        },
        toggleMenu() {
            this.showMenu = !this.showMenu
        },
        closeMenu() {
            this.showMenu = false
        },
        getRoleLabel(roleId) {
            return Array.from(roles).filter(role => role.value == roleId)[0].label
        },
        toggleAdmin() {
            this.$emit("toggleAdmin", this.data.id)
        },
        toggleObserver() {
            this.$emit("toggleObserver", this.data.id)
        },
        remove() {
            this.$emit("remove", this.data.id)
        }
    },
    created() {
        this.avatars = this.prepareAvatars
    }
}
</script>