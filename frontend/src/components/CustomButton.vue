<template>
    <button v-on:click="clicked" class='bg-[#8b5cf6] w-2/3 bg-none bg-opacity-10 
    border-bg3 border-2 rounded-xl text-bg3 hover:bg-opacity-30 font-light
     hover:font-normal active:font-bold active:bg-opacity-50'>
        {{ label }}
    </button>
</template>
<script>
export default {
    name: "CustomButton",
    props: ["label"],
    methods: {
        clicked() {
            this.$emit("clicked")
        }
    }
}
</script>
<style lang="">
    
</style>