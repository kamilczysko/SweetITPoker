<template>
    <div class='flex items-center align-middle bg-none'>
        <p class='text-left text'>{{ label }} : </p>
        <input class='text-center w-full outline-none bg-bg1 bg-opacity-0 border-b border-black' type="text" v-model="value" v-on:input="input">
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