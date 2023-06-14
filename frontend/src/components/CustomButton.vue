<template>
    <button v-if="!invisible" v-on:click="clicked" class='bg-[#5c23e1] w-2/3 bg-none bg-opacity-30 
                    border-button-font border-2 rounded-xl text-button-font hover:bg-opacity-50 font-extralight
                     hover:font-normal active:font-bold active:bg-opacity-80'>
        <p>
            {{ label }}
        </p>
    </button>
</template>
<script>
export default {
    name: "CustomButton",
    props: ["label", "invisible"],
    methods: {
        clicked() {
            this.$emit("clicked")
        }
    },
    computed: {
        isInactive() {
            console.log(this.inactive)
            return this.inactive
        }
    }
}
</script>
<style lang="">
    
</style>