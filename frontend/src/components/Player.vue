<template>
    <div class='flex items-center gap-4 mt-3' >
        <div class='rounded-full overflow-hidden w-16 h-16 ml-1' :class="{['shadow-round'] : getPlayer.selectedCard!=null}">
            <img :src="getAvatar(getPlayer.avatarIdx)" class='ml-1 mt-3'>
        </div>
        <div class='relative' :class="{ ['blur-[1px]']: getPlayer.isObserver }">
            <p class='text-xl'> {{ getPlayer.name }} </p>
            <span class='font-secondary font-extralight text-[10px] absolute -top-2'>{{ getPlayer.role }}</span>
            <div class='grid grid-cols-2 p-1'>
                <Checkbox label="Admin" :selected="getPlayer.isAdmin" @select="setAdmin"/>
                <Checkbox label="Observer" :selected="getPlayer.isObserver" @select="setObserver" />
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
            this.$emit("setAdmin", {player: this.player.id, isAdmin: value})

        },
        setObserver(value) {
            this.$emit("setObserver", {player: this.player.id, isObserver: value})
        }
    },
    created() {
        this.avatars = this.prepareAvatars
    },
    computed: {
        getPlayer() {
            return this.player
        }
    }
}
</script>
<style lang="">
    
</style>