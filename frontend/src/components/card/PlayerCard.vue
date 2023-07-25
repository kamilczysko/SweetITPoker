<template>
    <div class="flex flex-col gap-2 h-fit items-center">
        <div class="relative hover:brightness-115 bg-front bg-cover rounded-md p-[1px] hover:shadow-highlight flex flex-col items-center cursor-pointer justify-center"
            :class="{ [`mb-3 shadow-highlight  brightness-110`]: isSelected }, { [`mb-0 brightness-100 shadow-md shadow-black`]: !isSelected },
                { [`bg-front-other`]: unit == 'h' }" v-on:click="selectCard">
            <p v-if="value != 'q' && value != 'c'" class="text-white absolute xs:text-md md:text-xl font-main h-5 w-5 flex justify-center items-center rounded-full bg-black bg-opacity-20 top-1 left-1">{{ unit }}</p>
            <p v-if="value != 'q' && value != 'c'" class="text-white absolute xs:text-md md:text-xl font-main h-5 w-5 flex justify-center items-center rounded-full bg-black bg-opacity-20 bottom-1 right-1">{{ unit }}</p>
            <div class="w-full h-full flex justify-center items-center xs:w-16 xl:w-20 aspect-card">
                <img v-if="value == 'q'" src="../../assets/card/questionmark.png" class="aspect-square w-[75%]">
                <img v-else-if="value == 'c'" src="../../assets/card/coffee.png" class="aspect-square w-[75%]">
                <div v-else class="flex justify-center items-center flex-col gap-0 xs:text-xs md:text-2xl text-white">
                    <p> {{ value }} </p>
                </div>

            </div>
        </div>
        <div class="rounded-xl bg-opacity-30 text-white bg-[#e66860]  bg-cover bottom-0 flex items-center justify-evenly cursor-pointer xs:h-fit md:h-8 xs:text-xs md:text-xl gap-2 -py-1 hover:shadow-highlight active:shadow-highlight-more"
            @click.stop @click="switchUnit" :class="{ [`invisible`]: value == 'q' || value == 'c' }, { [`bg-[#602887]`]: unit == 'h' }">
            <p v-if="units.length > 1"
                class="select-none w-5 font-light bg-none border-none cursor-pointer hover:scale-125 text-center hover:font-bold active:scale-150">⊲</p>
            <p class="select-none font-secondary font-light w-5 text-center">{{ unit }}</p>
            <p v-if="units.length > 1"
                class="select-none w-5 font-light bg-none border-none cursor-pointer hover:scale-125 text-center hover:font-bold active:scale-150">⊳</p>
        </div>
    </div>
</template>
<script>
export default {
    name: "PlayerCard",
    props: ["value", "customUnits"],
    data() {
        return {
            unit: "h",
            units: ["h", "d"]
        }
    },
    methods: {
        selectCard() {
            this.$emit("select", { card: this.value, unit: this.unit })
        },
        switchUnit() {
            let index = this.units.indexOf(this.unit)
            if (index == this.units.length - 1) {
                index = -1
            }
            this.unit = this.units[index + 1]
            if (this.value == this.$store.getters.selectedCard) {
                this.$store.commit('setUnit', this.unit)
                // this.$emit("select", { card: this.value, unit: this.unit })
            }
        }
    },
    mounted() {
        this.units = this.customUnits
        this.unit = this.units[0]
    },
    computed: {
        isSelected() {
            return this.$store.getters.selectedCard == this.value
        }
    }
}
</script>