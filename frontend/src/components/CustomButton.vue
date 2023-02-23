<template>
    <button v-if="isInactive" v-on:click="clicked" class='bg-[#424242] w-2/3 bg-none bg-opacity-10 
             border-black border-2 rounded-xl text-black hover:bg-opacity-30 font-light
                 hover:font-normal active:font-bold active:bg-opacity-50'>
        <p class='blur-none'>
            {{ label }}
        </p>
    </button>

    <button v-else v-on:click="clicked" class='bg-[#8b5cf6] w-2/3 bg-none bg-opacity-10 
                border-bg3 border-2 rounded-xl text-bg3 hover:bg-opacity-30 font-light
                 hover:font-normal active:font-bold active:bg-opacity-50'>
        <p>
            {{ label }}
        </p>
    </button>
</template>
<script>
export default {
    name: "CustomButton",
    props: ["label", "inactive"],
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