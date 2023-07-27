<template>
    <div class='flex justify-center items-center gap-3 font-main font-extralight cursor-pointer h-fit' v-on:click="select">
        <p class='ml-2 xs:text-sm lg:text-md font-normal cursor-pointer '>{{ label }}</p>
        <div class='w-3 h-3 pb-1 pl-1 rounded-md border border-black flex items-center justify-center hover:scale-110 active:scale-125 select-none'>
            <p v-show="selection" class='text-center font-extrabold text-xl'>✓</p>
        </div>
    </div>
</template>
<script>
export default {
    name: "Checkbox",
    props: ["label", "selected"],
    data() {
        return {
            selection: this.selected
        }
    },
    methods: {
        select() {
            this.selection = !this.selection
            this.$emit("select", this.selection)
        }
    }
}
</script>
<style lang="">
    
</style>