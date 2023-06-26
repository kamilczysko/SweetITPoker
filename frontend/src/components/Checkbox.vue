<template>
    <div class='flex justify-center cursor-pointer hover:shadow-lg active:shadow-xl' v-on:click="select">
        <div class='w-3 h-3 pl-1 pb-1 pl-1 rounded-md border border-white-500 flex items-center justify-center'>
            <p v-show="selected" class='text-center text-main-font'>✓</p>
        </div>
        <p class='ml-2 font-secondary text-main-font text-[12px] font-normal cursor-pointer '>{{ label }}</p>
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