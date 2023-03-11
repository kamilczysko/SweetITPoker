<template>
    <div class='flex items-center justify-center bg-none'>
        <p class='text-left font-light'>{{ label }} : </p>
        <div class='flex justify-center w-3/5 align-middle cursor-pointer relative' v-on:click="toggle">
            <div class='rounded-full w-14 h-14 overflow-hidden hover:shadow-bg3 hover: shadow-md'>
                <img v-show="!isOpen" class='scale-100 hover:scale-110 ' :src="getIconUrl(value)" alt="">
            </div>
            <div v-show="isOpen" class='flex flex-wrap gap-1 w-[13vw] bg-bg2 rounded-xl p-1 bg-opacity-70 absolute' v-on:mouseleave="hide">
                <div v-for=" img, index in images()" class='rounded-full w-11 h-11 overflow-hidden hover: shadow-md hover:shadow-bg3 focus:scale-xl' v-on:click="select(img, index)">
                    <img class='scale-100 hover: shadow-xl hover:scale-110 focus:scale-120' :src="getIconUrl(img)">
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: "ImageChoose",
    props: ["label", "images"],
    data() {
        return {
            value: this.images()[0],
            isOpen: false
        }
    },
    methods: {
        select(image, imageIdx) {
            this.value = image
            this.$emit("select", imageIdx)
        },
        getIconUrl(path) {
            return "../" + path
        },
        toggle() {
            this.isOpen = !this.isOpen
        },
        hide() {
            this.isOpen = false
        }
    }
}
</script>
<style lang="">
    
</style>