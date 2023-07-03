<template>
    <div class="cursor-pointer hover:brightness-115 w-1/12">
        <div
            class="bg-front bg-cover aspect-card rounded-md p-[1px] w-full hover:shadow-highlight shadow-md shadow-black flex flex-col items-center justify-center">
            <img v-if="value == 'q'" src="../../assets/card/questionmark.png" class="aspect-square w-[75%]">
            <img v-else-if="value == 'c'" src="../../assets/card/coffee.png" class="aspect-square w-[75%]">
            <p v-else class="text-2xl text-white "> {{ value }} </p>
        </div>
    </div>
</template>
<script>
export default {
    name: "Card",
    props: ["value"]
}
</script>