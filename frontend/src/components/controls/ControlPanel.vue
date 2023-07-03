<template>
    <div class="flex xs:flex-row lg:flex-col items-center justify-center gap-2">
        <p class="info" id="info-panel">Copied to clipboard!</p>
        <CustomButton label="Copy link!" class='w-full' @clicked="copyToClipboard" />
        <CustomButton label="Reset votes!" class='w-full' @clicked="resetVotes" v-if="this.$store.getters.admin" />
        <!-- <CustomButton label="Refresh" class='w-full' @clicked="initRoom" /> -->
    </div>
</template>
<script>
import CustomButton from '@/components/controls/CustomButton.vue'
export default {
    name: "ControlPanel",
    components: { CustomButton },
    methods: {
        copyToClipboard() {
            const url = window.location.origin + "/join/" + this.$store.state.roomId
            this.unsecuredCopyToClipboard(url)
            console.log(document.getElementById("info-panel").classList)
            document.getElementById("info-panel").classList.remove("hideninfo")
            document.getElementById("info-panel").classList.add("visibleinfo")
            console.log(document.getElementById("info-panel").classList)
        
            setTimeout(() => { document.getElementById("info-panel").classList.add("hideninfo") }, 1000);
            console.log(document.getElementById("info-panel").classList)
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
.info {
    /* margin-top: 10%; */
    visibility: hidden;
}

.visibleinfo {
    visibility: visible;
}

.hideninfo {
    opacity: 0;
    transition: visibility .5s .5s, opacity .5s ease;
}
</style>