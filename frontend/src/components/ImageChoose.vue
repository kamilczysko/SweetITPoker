<template>
    <div class='flex items-center justify-center bg-none'>
        <p class='text-left font-light'>{{ label }} : </p>

        <div class='flex justify-center w-3/5 align-middle cursor-pointer relative' v-on:click="toggle">
            <div class='rounded-full aspect-square sm:w-14 2xl:w-20 overflow-hidden hover:shadow-bg3 hover: shadow-md'>
                <img v-show="!isOpen" class='scale-100 hover:scale-110 ' :src="getIconUrl(value)" alt="">
            </div>

            <div v-show="isOpen" class='flex flex-wrap items-center justify-center gap-1 py-1 flex-shrink sm:min-w-[50vw] 2xl:min-w-[45vw] bg-bg2 rounded-xl bg-opacity-70 absolute'
                v-on:mouseleave="hide">
                <div v-for=" img, index in images()"
                    class='rounded-full aspect-square sm::min-w-[1.5rem] sm:max-w-[3rem] 2xl:min-w-[4rem] 2xl:max-w-[5rem] overflow-hidden hover: shadow-md hover:shadow-bg3 focus:scale-xl'
                    v-on:click="select(img, index)">
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