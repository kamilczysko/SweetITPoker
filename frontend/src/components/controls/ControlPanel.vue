<template>
    <div class="flex xs:flex-row md:flex-col items-center justify-center gap-2 relative">
        <transition>
            <p class="text-white absolute top-8 font-main" v-show="showInfo">Copied to clipboard!</p>
        </transition>
        <CustomButton label="Copy link!" class='w-full' @clicked="copyToClipboard" />
        <CustomButton label="Reset votes!" class='w-full' @clicked="resetVotes" v-if="this.$store.getters.admin" />
    </div>
</template>
<script>
import CustomButton from '@/components/controls/CustomButton.vue'
export default {
    name: "ControlPanel",
    components: { CustomButton },
    data() {
        return {
            showInfo: false
        }
    },
    methods: {
        copyToClipboard() {
            const url = window.location.origin + "/join/" + this.$store.state.roomId
            this.unsecuredCopyToClipboard(url)
            this.showInfo = true
            setTimeout(() => { this.showInfo = false }, 1500);
        },
        unsecuredCopyToClipboard(text) {
            const textArea = document.createElement("textarea");
            textArea.value = text;
            document.body.appendChild(textArea);
            textArea.focus();
            textArea.select();
            try {
                document.execCommand('copy');
            } catch (err) {
                console.error('Unable to copy to clipboard', err);
            }
            document.body.removeChild(textArea);
        },
        resetVotes() {
            this.$emit("resetVotes")
        },
        initRoom() { }
    }
}
</script>
<style scoped>
.v-enter-active,
.v-leave-active {
  transition: opacity 0.5s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>