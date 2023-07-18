<template>
    <div class="bg-background-first bg-cover xs:h-full md:h-screen">
        <Header></Header>
        <div class="px-[1vw] flex flex-row justify-center items-center">
            <div class="xs:flex xs:flex-col md:grid md:grid-cols-header">
                <Description></Description>
                <div class="h-full w-full shadow-black"></div>
                <div class="flex flex-col justify-center items-center relative">
                    <StartForm></StartForm>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import Header from '@/components/Header.vue'
import Description from '@/components/Description.vue'
import StartForm from '@/components/form/StartForm.vue'
export default {
    name: "StartView",
    components: { Header, Description, StartForm }
}
</script>