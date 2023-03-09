<template>
    <div class='h-[90%] cursor-pointer z-40'>
        <div v-if="isVisible"
            class='rounded-md  px-2 w-full py-2 font-secondary h-full border border-cyan-800 bg-card-bg-2 mb-3 brightness-90 shadow-lg shadow-bg3 hover:shadow-xl hover:shadow-bg3 hover:brightness-100'
            v-on:click="selectCard">
            <div
                class='w-full h-full rounded-lg flex flex-col items-center justify-center bg-card-bg-3 bg-small z-50 border border-cyan-800'>
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
        <div v-else
            class='rounded-md  w-full h-full bg-revers bg-center bg-cover hover:shadow-md hover:shadow-black active:shadow-xl active:shadow-black active:mb-1'>
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
        toggleUnit() {
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