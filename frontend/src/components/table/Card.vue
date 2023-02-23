<template>
    <div
        class='rounded-md h-[90%] px-2 py-2 font-secondary cursor-pointer bg-card-bg-2 z-40 border border-cyan-800 brightness-75 hover:shadow-md hover:shadow-black active:shadow-xl active:shadow-black active:mb-1' :class="{'mb-3 brightness-90 shadow-xl shadow-bg3 hover:shadow-2xl hover:shadow-bg3 hover:brightness-100 ':isSelected}"
        v-on:click="selectCard">
        <div
            class="h-full w-full rounded-lg flex flex-col items-center justify-center bg-card-bg-3 bg-small z-50 border border-cyan-800">
            <div v-if="isVisible">
                <div v-if="showUnit" class='flex justify-center items-center flex-col w-full'>
                    <p class='text-2xl'>{{ data.value }}</p>
                    <div class='text-lg relative cursor-pointer grid grid-cols-3 items-center w-full font-extralight'
                        v-on:click="toggleUnit" @click.stop z-50>
                        <p class='scale-x-[-1] text-xs  hover:text-sm active:text-xl'>▶</p>
                        <p class='text-center'>{{ this.unit }}</p>
                        <p class='text-xs hover:text-sm active:text-xl'>▶</p>
                    </div>
                </div>
                <div v-else>
                    <img v-if="isCoffeCard" src="../../assets/coffe.png">
                    <img v-else src="../../assets/questionmark.png">
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: "Card",
    props: ["data", "isVisible", "isSelected"],
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
                this.$emit("selectCard", this.data.id)
            }
    },
    computed: {
        isCoffeCard() {
            return "isCoffeCard" in this.data
        },
        isQuestionCard() {
            return "isQuestionCard" in this.data
        },
        showUnit() {
            return !("isCoffeCard" in this.data) && !("isQuestionCard" in this.data)
        },
        getUnit() {
            return this.unit
        }
    },
    created() {
        this.unit = this.data.unit
    }
}
</script>
<style lang="">
    
</style>