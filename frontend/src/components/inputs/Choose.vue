<template>
    <div class='flex font-light items-center justify-center align-middle bg-none relative'>
        <p class='text-left xs:text-xs lg:text-lg'>{{ label }} : </p>
        <div v-on:click="toggle" class='cursor-pointer w-3/5 font-light' v-on:mouseleave="hide">
            <div v-show="isOpen" class='hover:font-normal flex align-middle w-full xs:text-xs lg:text-lg'>
                <p class='m-auto'>{{ value.label }}</p>
                <p class='text-right mr-5'>▼</p>
            </div>
            <div v-show="!isOpen" class='xs:text-xs lg:text-lg hover:font-normal flex align-middle w-full'>
                <p class='m-auto'>{{ value.label }}</p>
                <p class='text-right mr-5'>◀</p>
            </div>
            <ul
                class='text-center cursor-pointer bg-bg2 bg-opacity-50 rounded-xl absolute w-3/4 z-50 xs:text-xs lg:text-lg'>
                <div class=''>
                    <li class='hover:font-normal text-center w-full' v-for="v in values" v-show="isOpen && v.value != value.value"
                        :key="v" v-on:click="choose(v)">{{ v.label }}</li>
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
    },
    mounted() {
        this.$emit("choose", this.value)
    }
}
</script>