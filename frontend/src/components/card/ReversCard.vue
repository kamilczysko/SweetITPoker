<template>
    <div class="cursor-pointer hover:brightness-105 xs:w-16 md:w-20  aspect-card">
        <div class="bg-revers bg-cover aspect-card rounded-md p-[1px] w-full"
        :class="{ [`mb-3 shadow-highlight-more brightness-125`]: isSelected }, { [`mb-0 brightness-100 shadow-md shadow-black`]: !isSelected }">
        </div>
    </div>
</template>
<script>
export default {
    name: "ReversCard",
    props: ["data"],
    computed: {
        isSelected() {
            return this.$store.state.players.filter(player => player.id == this.data.id)[0].selectedCard != null
        }
    }
}
</script>