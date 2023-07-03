<template>
    <div class="hover:brightness-115 shadow-highlight bg-front bg-cover rounded-md p-[1px] hover:shadow-highlight flex flex-col items-center justify-center cursor-pointer"
        v-on:click="selectCard">
        <div class="w-full h-full flex justify-center items-center xs:w-16 xl:w-20    aspect-card">
            <img v-if="value == 'q'" src="../../assets/card/questionmark.png" class="aspect-square w-[75%]">
            <img v-else-if="value == 'c'" src="../../assets/card/coffee.png" class="aspect-square w-[75%]">
            <div v-else class="flex justify-center items-center flex-col gap-0 xs:text-xs md:text-lg text-white">
                <p> {{ value }} </p>
                <div class="flex items-center justify-evenly -mt-1 mb-[6px] cursor-pointer h-3 lg:text-xs xl:text-sm">
                    <p class="font-secondary text-white">{{ unit }}</p>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: "FrontCard",
    props: ["value", "unit"],
}
</script>