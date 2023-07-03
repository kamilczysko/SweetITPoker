<template>
    <button v-if="!invisible" v-on:click="clicked" class='bg-[#5c23e1] w-full bg-none bg-opacity-70 font-secondary
                    border-button-font xs:border-[1px] md:border-2 rounded-xl
                     min-w-fit text-button-font xs:text-xs lg:text-sm hover:bg-opacity-70 font-extralight
                     hover:font-normal active:bg-opacity-80 px-2 py-1'>
        <p class="active:font-bold ">
            {{ label }}
        </p>
    </button>
</template>
<script>
export default {
    name: "CustomButton",
    props: {
        label: {
            type: String
        },
        invisible: {
            type: Boolean,
            default: false
        }
    },
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