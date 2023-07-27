<template>
    <div class="bg-none text-white w-full h-full rounded-lg flex flex-col xs:justify-start md:justify-center items-center">
        <h1 class='text-center font-secondary text-xl font-semibold underline mb-4 text-white  '>Results</h1>
        <div v-if="this.$store.state.result.results.length > 0" class="flex flex-col justify-center items-center w-2/3">
            <div v-for="row in this.$store.state.result.results" :key="row"
                class="flex flex-col justify-center items-center gap-2 w-full">
                <div class='bg-slate-800 bg-opacity-50 w-full mt-2 xs:text-xs lg:text-lg flex text-lg gap-3 justify-center items-center font-secondary px-4 font-extralight cursor-pointer shadow-black shadow-sm hover:shadow-md hover:shadow-black hover:bg-opacity-40 active:shadow-xl active:bg-slate-500 active:bg-opacity-70 active:shadow-black rounded-lg'
                    @click="copyToClipboard(row.avg + 'h')">
                    <p>{{ getRoleLabel(row.role) }}</p>
                    <p>{{ row.avg }} {{ resultUnit }}</p>
                </div>
            </div>
            <p class='font-extralight font-secondary text-[10px] mt-3'>(Click to copy to clipboard)</p>
        </div>
        <div>
            <div v-if="isCoffeeSelected && isQuestionSelected">
                <p class="font-main font-lg font-light"><b>Someone</b> still need some explanation and coffee!</p>
            </div>
            <div v-else-if="isCoffeeSelected || isQuestionSelected">
                <p v-if="isCoffeeSelected" class="font-main font-lg font-light"><b>Someone</b> need coffee! Maybe go to
                    coffee shop
                    instead of work?</p>
                <p v-else-if="isQuestionSelected" class="font-main font-lg font-light"><b>Someone</b> still need some
                    explanation!</p>
            </div>
        </div>
        <div class="xs:w-full md:w-1/2 px-6 mt-5">
            <CustomButton v-show="this.$store.getters.admin" label="Reset!" @clicked="reset" />
            <CustomButton v-show="!this.$store.getters.admin" label="close" @clicked="close" class="w-2/3 mt-5" />
        </div>
        <div class="h-16">
            <transition>
                <p class="text-white" v-if="showMessage">Copied to clipboard!</p>
            </transition>
        </div>

    </div>
</template>
<script>
import CustomButton from './controls/CustomButton.vue';
import roles from '../assets/roles.js';
export default {
    name: "Result",
    components: { CustomButton },
    props: ["isAdmin"],
    data() {
        return {
            showMessage: false
        }
    },
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
            this.showMessage = true
            setTimeout(() => { this.showMessage = false }, 1500);
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
        isCoffeeSelected() {
            return this.$store.state.result.hasSelectedCoffee
        },
        isQuestionSelected() {
            return this.$store.state.result.hasSelectedQuestion
        },
        resultUnit() {
            const unit =  this.$store.state.result.unit
            return unit
        }
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