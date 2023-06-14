<template>
    <div class='cursor-pointer z-40 rounded-md aspect-card lg:min-w-[7vw] lg:w-[7vw] xs:min-w-[20vw] xs:w-[20vw] sm:min-w-[10vw] sm:w-[10vw] md:min-w-[12vw] md:w-[12vw]' :class="{['brightness-125']:isSelected}">
        <div v-if="isVisible"
            class='w-full h-full rounded-md font-secondary border border-black bg-front bg-center bg-cover mb-3 brightness-90 shadow-lg shadow-bg3 hover:shadow-xl hover:shadow-bg3 active:brightness-110 hover:brightness-105'
            v-on:click="selectCard">
            <div class='w-full h-full rounded-lg flex flex-col items-center justify-center z-50'>
                <div v-if="isImageCard" class='flex items-center justify-center'>
                    <img v-if="isCoffeCard" src="../../assets/coffe.png">
                    <img v-else src="../../assets/questionmark.png">
                </div>
                
                <div v-else-if="isSelected" class='flex justify-center items-center flex-col gap-0 relative pt-3'>
                    <p class='xs:text-sm lg:text-xl text-white font-main -m-3 active:brightness-110'>{{ data.value }}</p>
                    <div class='text-lg flex justify-center items-center w-full font-extralight'>
                        <p class='xs:text-sm lg:text-xl text-center text-white font-light font-main '>{{ data.unit }}</p>
                    </div>
                </div>
            </div>
        </div>
        <div v-else
            class='rounded-md border border-black w-full h-full bg-revers bg-center bg-cover hover:shadow-md hover:shadow-black active:shadow-xl active:shadow-black'
            :class="{['brightness-110']:isSelected}, {['brightness-90']:!isSelected}">
        </div>
    </div>
</template>
<script>
import deck from '../../assets/carddeck';

export default {
    name: "Card",
    props: ["data", "isVisible", "isSelected"],
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
            return this.data != null && ((this.getCoffeeCardId() == this.data.cardId) || (this.getQuestionCardId() == this.data.cardId))
        },
        getUnit() {
            return this.unit
        }
    }
}
</script>
<style></style>