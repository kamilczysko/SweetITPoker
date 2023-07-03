<template>
    <button v-if="!invisible" v-on:click="clicked" class='bg-[#5c23e1] w-[10%] bg-none bg-opacity-70 font-secondary
                    border-button-font xs:border-[1px] md:border-2 rounded-xl
                     min-w-fit text-button-font xs:text-xs md:text-sm font-extralight
                     hover:font-semibold active:brightness-125 active:font-bold cursor-pointer px-5 py-1'>
        <div class="flex items-center justify-center gap-1">
            <img src="../../assets/logout.svg" class="aspect-square w-4">
            <p class="active:font-bold w-10 px-1">
                {{ label }}
            </p>
        </div>
    </button>
</template>
<script>
export default {
    name: "LogoutButton",
    props: {
        label: {
            type: String,
            default: "Leave"
        },
        invisible: {
            type: Boolean,
            default: false
        },
        icon: {
            type: String,
            default: null
        }
    },
    methods: {
        clicked() {
            this.$emit("clicked")
        }
    },
    computed: {
        isInactive() {
            return this.inactive
        }
    }
}
</script>