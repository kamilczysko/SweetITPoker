<template>
    <div class="hover:brightness-115 bg-front bg-cover rounded-md p-[1px] hover:shadow-highlight flex flex-col items-center justify-center cursor-pointer "
        :class="{ [`mb-3 shadow-highlight  brightness-110`]: isSelected }, { [`mb-0 brightness-100 shadow-md shadow-black`]: !isSelected }"
        v-on:click="selectCard">
        <div class="w-full h-full flex justify-center items-center xs:w-16 xl:w-20    aspect-card">
            <img v-if="value == 'q'" src="../../assets/card/questionmark.png" class="aspect-square w-[75%]">
            <img v-else-if="value == 'c'" src="../../assets/card/coffee.png" class="aspect-square w-[75%]">
            <div v-else class="flex justify-center items-center flex-col gap-0 xs:text-xs md:text-lg text-white">
                <p> {{ value }} </p>
                <div class="flex items-center justify-evenly -mt-1 mb-[6px] cursor-pointer h-3 lg:text-xs xl:text-sm"
                    @click.stop @click="switchUnit">
                    <button v-if="units.length > 1"
                        class="bg-none border-none cursor-pointer w-1/3 hover:text-xl text-center hover:scale-110 active:scale-125">◂</button>
                    <p class="font-secondary text-white">{{ unit }}</p>
                    <button v-if="units.length > 1"
                        class="bg-none border-none xs:mt-1 lg:mt-2 rotate-180 cursor-pointer w-3 hover:text-xl text-center hover:scale-110 active:scale-125">◂</button>
                </div>
            </div>
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
            this.$emit("select", {card: this.value, unit: this.unit})
        },
        switchUnit() {
            let index = this.units.indexOf(this.unit)
            if (index == this.units.length - 1) {
                index = -1
            }
            this.unit = this.units[index + 1]
            if (this.value == this.$store.getters.selectedCard) {
                this.$store.commit('setUnit', this.unit)
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