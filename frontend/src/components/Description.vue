<template>
    <article class="flex justify-center items-center m-auto w-[95%]">
        <div class='flex flex-col justify-center items-center gap-7 py-5 xs:mb-4 rounded-2xl backdrop-blur-sm bg-slate-50 bg-opacity-30'>
            <h2 class='xs:text-lg lg:text-2xl underline'>Your perfect planning tool</h2>
            <p class='font-secondary xs:text-sm lg:text-md font-light text-center px-5'>
                <b>UwUstimates</b> is a planning poker tool designed to help development teams. It's an easy-to-use
                tool,
                for you and your team to estimate and manage tasks in the Agile methodology, in a kawaii matter <span
                    class='whitespace-nowrap'>(´・ω・｀)</span>
            </p>
            <div class='xs:self-center lg:self-start'>
                <ul class='font-secondary list-outside list-none ml-10 font-light xs:text-sm lg:text-lg'>
                    <li class='xs:p-1 lg:p-2 flex justify-start items-center gap-3'>
                        <img src="../assets/rocket.svg" alt="" class='aspect-square xs:w-4 lg:w-6'>
                        Estimate your tasks
                    </li>
                    <li class='xs:p-1 lg:p-2 flex justify-start items-center gap-3'>
                        <img src="../assets/rocket.svg" alt="" class='aspect-square xs:w-4 lg:w-6'>
                        Roleplay as one of many anime avatars
                    </li>
                    <li class='xs:p-1 lg:p-2 flex justify-start items-center gap-3'>
                        <img src="../assets/rocket.svg" alt="" class='aspect-square xs:w-4 lg:w-6'>
                        Get paid doing it!
                    </li>
                </ul>
            </div>
        </div>
    </article>
</template>
<script>
export default {
    name: "Description"
}
</script>