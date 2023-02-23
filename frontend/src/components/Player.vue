<template>
    <div class='flex items-center gap-4 mt-3' >
        <div class='rounded-full overflow-hidden w-16 h-16 ml-1' :class="{['shadow-round'] : player.selectedCard!=null}">
            <img :src="getAvatar(player.avatar)" class='ml-1 mt-3'>
        </div>
        <div class='relative' :class="{ ['blur-[1px]']: player.isObserver }">
            <p class='text-xl'> {{ player.playerName }} </p>
            <span class='font-secondary font-extralight text-[10px] absolute -top-2'>{{ player.role }}</span>
            <div class='grid grid-cols-2 p-1'>
                <Checkbox label="Admin" :selected="player.isAdmin" @select="setAdmin"/>
                <Checkbox label="Observer" :selected="player.isObserver" @select="setObserver" />
            </div>
        </div>
    </div>
</template>
<script>
import Checkbox from './Checkbox.vue'
const avatars = import.meta.globEager('../assets/avatars/*.png')

export default {
    name: "Palyer",
    props: ["player"],
    components: {Checkbox},
    data() {
        return {
            avatars: []
        }
    }, methods: {
        prepareAvatars() {
            return Object.values(avatars)
                .map(a => a.default)
        },
        getAvatar(index) {
            return "../" + this.avatars()[index]
        },
        setAdmin(value) {
            this.$emit("setAdmin", {player: this.player.uid, isAdmin: value})

        },
        setObserver(value) {
            this.$emit("setObserver", {player: this.player.uid, isObserver: value})
        }
    },
    created() {
        this.avatars = this.prepareAvatars
    },
}
</script>
<style lang="">
    
</style>