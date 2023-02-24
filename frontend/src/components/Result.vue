<template>
    <div ref="target" class="target">
        <img src="../assets/sad.png" class='h-10 w-10 absolute right-0 -top-8'>
        <Moveable className="moveable" v-bind:target="['.target']" v-bind:draggable="true" @drag="onDrag"
            v-bind:origin="false" />
        <div
            class='rounded-2xl w-[30vw] min-h-[10vw] bg-gradient-to-tr from-[#FBDA61] to-[#FF5ACD] bg-opacity-10 flex flex-col items-center justify-center py-2'>
            <h1 class='text-center font-secondary text-xl font-bold'>Results</h1>
            <div v-for="row in data" :key="row.role"
                class='grid grid-cols-3 items-center w-full font-secondary font-extralight cursor-pointer hover:bg-red-400 hover:bg-opacity-40 px-4 py-1 mb-1 rounded-lg active:bg-opacity-70'>
                <p>{{ row.role }}s</p>
                <p>{{ getProperTimeFromat(row.time) }}</p>
                <CustomButton class='w-full' label="Copy to clipboard"
                    @clicked="copyToClipboard(getProperTimeFromat(row.time))" />
            </div>
            <CustomButton label="reset" @clicked="reset" class='mt-3'/>
            <p class="info" id="info">Copied to clipboard!</p>
        </div>
    </div>
</template>
<script>
import Moveable from "vue3-moveable";
import CustomButton from "./CustomButton.vue";
export default {
    name: "Result",
    components: { Moveable, CustomButton },
    props: ['data'],
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
        }
    }
}
</script>
<style scoped>
.target {
    position: absolute;
    bottom: 12vw;
    left: 30vw;
    z-index: 100;
    border-radius: 20px;
}

.info {
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