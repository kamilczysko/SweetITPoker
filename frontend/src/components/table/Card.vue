<template>
    <div class='rounded-md h-[90%] px-2 w-10/12 py-2 font-secondary cursor-pointer bg-card-bg-2 z-40 border border-cyan-800 brightness-75'
        :class="{ 'mb-3 brightness-90 shadow-lg shadow-bg3 hover:shadow-xl hover:shadow-bg3 hover:brightness-100 ': isSelected }, { ' hover:shadow-md hover:shadow-black active:shadow-xl active:shadow-black active:mb-1': !isSelected }"
        v-on:click="selectCard">
        <div
            class="h-full w-full rounded-lg flex flex-col items-center justify-center bg-card-bg-3 bg-small z-50 border border-cyan-800">
            <div v-if="isVisible" class='w-full'>
                <div v-if="isImageCard" class='flex items-center justify-center'>
                    <img v-if="isCoffeCard" src="../../assets/coffe.png">
                    <img v-else src="../../assets/questionmark.png">
                </div>
                <div v-else class='flex justify-center items-center gap-3 flex-col w-full'>
                    <p class='text-4xl'>{{ data.value }}</p>
                    <div v-if="isUserSelection" class='text-lg flex justify-center items-center w-full font-extralight'>
                        <p class='text-center font-medium'>{{ data.unit }}</p>
                    </div>
                    <div v-else class='text-lg relative cursor-pointer grid grid-cols-3 items-center w-full font-extralight'
                        v-on:click="toggleUnit" @click.stop>
                        <p class='scale-x-[-1] text-sm  hover:text-lg active:text-xl'>▶</p>
                        <p class='text-center font-medium'>{{ unit }}</p>
                        <p class='text-sm hover:text-lg active:text-xl'>▶</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: "Card",
    props: ["data", "isVisible", "isSelected", "isUserSelection"],
    data() {
        return {
            unit: "h"
        }
    },
    methods: {
        toggleUnit() {
            if (this.unit === "h") {
                this.unit = "d"
            } else {
                this.unit = "h"
            }
        },
        selectCard() {
            this.$emit("selectCard", {cardId: this.data.id, isCoffeCard: this.data.isCoffeCard, isQuestionCard: this.isQuestionCard,  unit: this.unit, value: this.data.value})
        }
    },
    computed: {
        isCoffeCard() {
            return this.data.isCoffeCard
        },
        isQuestionCard() {
            return this.data.isQuestionCard
        },
        isImageCard() {
            return this.data != null && ((this.data.isCoffeCard) || (this.data.isQuestionCard))
        },
        getUnit() {
            return this.unit
        }
    }
}
</script>
<style>
    
</style>