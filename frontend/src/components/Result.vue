<template>
    <div class="bg-none text-white w-1/2 h-full rounded-lg flex flex-col xs:justify-start md:justify-center items-center">
        <h1 class='text-center font-secondary text-xl font-semibold underline mb-4 text-black'>Results</h1>
        <div v-for="row in this.$store.state.result" :key="row" class="flex flex-col justify-center items-center gap-2 w-2/3">
            <div class='bg-slate-800 bg-opacity-50 w-full mt-2 xs:text-xs lg:text-lg flex text-lg gap-3 justify-center items-center font-secondary px-4 font-extralight cursor-pointer shadow-black shadow-sm hover:shadow-md hover:shadow-black hover:bg-opacity-40 active:shadow-xl active:bg-slate-500 active:bg-opacity-70 active:shadow-black rounded-lg'
                @click="copyToClipboard(row.avg+'h')">
                <p>{{ getRoleLabel(row.role) }}</p>
                <p>{{ row.avg }}h</p>
            </div>
        </div>
        <p class='font-extralight font-secondary text-[10px] mt-3'>(Click to copy to clipboard)</p>
        <CustomButton v-show="this.$store.getters.admin" label="Reset votes!" @clicked="reset" class="w-2/3 mt-5" />
        <CustomButton v-show="!this.$store.getters.admin" label="close" @clicked="close" class="w-2/3 mt-5" />
        <p class="info" id="info">Copied to clipboard!</p>
    </div>
</template>
<script>
import CustomButton from './controls/CustomButton.vue';
import roles from '../assets/roles.js';
export default {
    name: "Result",
    components: { CustomButton },
    props: ["isAdmin"],
    methods: {
        getProperTimeFromat(timeInHours) {
            return (Math.round(timeInHours * 100) / 100) + "h"
        },
        onDrag({ transform }) {
            this.$refs.target.style.transform = transform;
        },
        reset() {
            this.$emit("reset")
        },
        copyToClipboard(value) {
            this.unsecuredCopyToClipboard(value)
            document.getElementById("info").classList.remove("hideninfo")
            document.getElementById("info").classList.add("visibleinfo")
            setTimeout(() => { document.getElementById("info").classList.add("hideninfo") }, 1000);
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
        getRoleLabel(roleId) {
            return Array.from(roles).filter(role => role.value == roleId)[0].label
        },
        close() {
            this.$emit("closeResult")
        }
    },
    computed: {
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