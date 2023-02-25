<template>
    <div class='flex items-center justify-between align-middle bg-none relative'>
        <p class='text-left font-light'>{{ label }} : </p>
        <div  v-on:click="toggle" class='cursor-pointer w-3/5'>
            <div v-show="isOpen" class='hover:font-bold flex align-middle w-full'>
                <p class='m-auto'>{{ value }}</p>
                <p class='text-right mr-5'>▼</p>
            </div>
            <div v-show="!isOpen" class='hover:font-bold flex align-middle w-full'>
                <p class='m-auto'>{{ value }}</p>
                <p class='text-right  mr-5'>◀</p>
            </div>
            <ul class='text-center cursor-pointer bg-bg2 bg-opacity-50 rounded-xl absolute w-3/4 z-50' >
                <div class=''>
                    <li class='hover:font-bold text-center w-full' v-for="v in values" v-show="isOpen && v != value"
                        :key="v" v-on:click="choose(v)">{{ v }}</li>
                </div>
            </ul>
        </div>
    </div>
</template>
<script>
export default {
    name: "Choose",
    props: ["label", "values"],
    data() {
        return {
            value: this.values[0],
            isOpen: false
        }
    },
    methods: {
        choose(value) {
            this.value = value
            this.$emit("choose", value)
        },
        toggle() {
            this.isOpen = !this.isOpen
        },
        hide() {
            this.isOpen = false
        }
    }
}
</script>
<style></style>