<template>
    <div class="relative hover:brightness-115 shadow-highlight bg-front bg-cover rounded-md p-[1px] hover:shadow-highlight flex flex-col items-center justify-center cursor-pointer"
    :class="{ [`bg-front-other`]: unit == 'd' }"
        v-on:click="selectCard">
        <p v-if="value != 'q' && value != 'c'" class="text-white absolute xs:text-md md:text-xl font-card h-5 w-5 flex justify-center items-center rounded-full bg-black bg-opacity-20 top-1 left-1">{{ unit }}</p>
        <p v-if="value != 'q' && value != 'c'" class="text-white absolute xs:text-md md:text-xl font-card h-5 w-5 flex justify-center items-center rounded-full bg-black bg-opacity-20 bottom-1 right-1">{{ unit }}</p>
        <div class="w-full h-full flex justify-center items-center xs:w-16 xl:w-20 aspect-card">
            <img v-if="value == 'q'" src="../../assets/card/questionmark.png" class="aspect-square w-[75%]">
            <img v-else-if="value == 'c'" src="../../assets/card/coffee.png" class="aspect-square w-[75%]">
            <div v-else class="flex justify-center font-card items-center flex-col gap-0 xs:text-xs md:text-lg text-white">
                <p> {{ value }} </p>
                <div class="flex items-center justify-evenly -mt-1 mb-[6px] cursor-pointer h-3 lg:text-xs xl:text-sm">
                    <p class="font-card text-white">{{ unit }}</p>
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