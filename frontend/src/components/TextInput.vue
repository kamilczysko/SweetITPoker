<template>
    <div class='flex items-center justify-center gap-4 align-middle bg-none'>
        <p class='text-left font-extralight xs:text-xs lg:text-lg'>{{ label }} : </p>
        <input class='xs:text-xs lg:text-lg text-center h-full w-3/5 outline-none bg-bg1 bg-opacity-0 border-b border-black hover:border-b-[1.5px] font-extralight' type="text" v-model="value" v-on:input="input">
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