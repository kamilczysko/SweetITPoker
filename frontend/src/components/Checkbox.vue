<template>
    <div class='flex justify-center cursor-pointer hover:shadow-lg active:shadow-xl' v-on:click="select">
        <div class='min-w-3 w-3 max-h-3 h-3 rounded-md border border-blue-500 flex items-center justify-center'>
            <p v-show="selected" class='text-center'>✓</p>
        </div>
        <p class='ml-1 font-secondary text-[10px] font-extralight text-gray-900 cursor-pointer '>{{ label }}</p>
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