<template>
    <div class='cursor-pointer z-40 rounded-md aspect-card'>
        <div v-if="isVisible"
            class='w-full h-full rounded-md font-secondary border border-black bg-front bg-center bg-cover mb-3 brightness-90 shadow-lg shadow-bg3 hover:shadow-xl hover:shadow-bg3 active:brightness-110 hover:brightness-100'
            v-on:click="selectCard">
            <div class='w-full h-full rounded-lg flex flex-col items-center justify-center z-50'>
                <div v-if="isImageCard" class='flex items-center justify-center'>
                    <img v-if="isCoffeCard" src="../../assets/coffe.png">
                    <img v-else src="../../assets/questionmark.png">
                </div>
                <div v-else class='flex justify-center items-center flex-col h-full w-full gap-0 relative'>
                    <p class='md:text-sm 2xl:text-3xl text-xl text-white font-main -m-1 active:brightness-110'>{{ data.value }}</p>
                    <div v-if="isUserSelection" class='text-lg flex justify-center items-center w-full font-extralight'>
                        <p class='md:text-sm 2xl:text-3xl text-xl text-center text-white font-medium font-main -m-1'>{{ data.unit }}</p>
                    </div>
                    <div v-else
                        class='md:text-xs 2xl:text-lg text-sm text-white cursor-pointer flex justify-center items-center font-extralight -m-1 gap-2 w-full'
                        v-on:click="toggleUnit" @click.stop>
                        <p class='scale-x-[-1] text-sm  hover:text-lg active:text-xl w-3'>▶</p>
                        <p class='text-center font-medium'>{{ unit }}</p>
                        <p class='text-sm hover:text-lg active:text-xl w-3'>▶</p>
                    </div>
                </div>
            </div>
        </div>
        <div v-else
            class='rounded-md border border-black w-full h-full bg-revers bg-center bg-cover hover:shadow-md hover:shadow-black active:shadow-xl active:shadow-black'>
        </div>
    </div>
</template>
<script>
import deck from '../../assets/carddeck';

export default {
    name: "Card",
    props: ["data", "isVisible", "isSelected", "isUserSelection"],
    data() {
        return {
            unit: "h"
        }
    },
    methods: {
        toggleUnit(event) {
            event.stopPropagation()
            if (this.unit === "h") {
                this.unit = "d"
            } else {
                this.unit = "h"
            }
            if (this.isSelected) {
                this.selectCard()
            }
        },
        selectCard() {
            this.$emit("selectCard", { cardId: this.data.cardId, isCoffeCard: this.data.isCoffeCard, isQuestionCard: this.isQuestionCard, unit: this.unit, value: this.data.value })
        },
        getCoffeeCardId() {
            const res = Array.from(deck).filter(card => card.isCoffeCard)
            if (res.length == 0) {
                return false
            }
            return res[0].cardId
        },
        getQuestionCardId() {
            const res = Array.from(deck).filter(card => card.isQuestionCard)
            if (res.length == 0) {
                return false
            }
            return res[0].cardId
        }
    },
    computed: {
        isCoffeCard() {
            return this.getCoffeeCardId() == this.data.cardId
        },
        isQuestionCard() {
            return this.getQuestionCardId() == this.data.cardId
        },
        isImageCard() {
            return ((this.getCoffeeCardId() == this.data.cardId) || (this.getQuestionCardId() == this.data.cardId))
        },
        getUnit() {
            return this.unit
        }
    }
}
</script>
<style></style>