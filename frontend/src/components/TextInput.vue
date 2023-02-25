<template>
    <div class='flex items-center justify-between gap-4 align-middle bg-none'>
        <p class='text-left font-light'>{{ label }} : </p>
        <input class='text-center h-4 w-3/5 outline-none bg-bg1 bg-opacity-0 border-b border-black hover:border-b-2 font-light' type="text" v-model="value" v-on:input="input">
    </div>
</template>
<script>
export default {
    name: "TextInput",
    props: ["label"],
    data() {
        return {
            value: ""
        }
    },
    methods: {
        input() {
            this.$emit("inputText", this.value)
        }
    }
}
</script>
<style></style>