<template>
        <div v-if="visible"
            class='ml-5 absolute left-0 xs:w-[90vw] xs:bottom-10 lg:bottom-1/3 lg:mb-5 lg:w-[20vw] p-2 flex flex-col gap-4 items-center bg-white rounded-md md:bg-opacity-60'>
            <button class='cursor-pointer hover:font-bold text-right self-end font-secondary' v-on:click="visible = false">X</button>
            <div class='opacity-100'>
                <h2 class='text-center font-secondary mb-4'>This website uses cookies!</h2>
                <article class='font-secondary text-[10px] text-center flex flex-col gap-4'>
                    <p>
                        You may see ads that are less relevant to you. These ads use cookies, but not for personalization.
                    </p>
                    <p>
                        Our website uses Google AdSense to display advertisements. AdSense uses cookies to personalize ads
                        and to analyze traffic to our website. Information about your use of our website, including your IP
                        address, may be transmitted to Google and stored on their servers.
                    </p>
                    <p>
                        By using our website, you consent to the processing of data about you by Google in the manner
                        described in Google's Privacy Policy and for the purposes set out above.
                    </p>
                    <p>
                        If you do not consent to the use of cookies and the processing of your data, please do not use our
                        website.
                    </p>
                </article>
            </div>
            <CustomButton label="Accept" @click="close"></CustomButton>
        </div>
</template>
<script>
import CustomButton from './controls/CustomButton.vue'
export default {
    name: "CookiesPrompt",
    components: { CustomButton },
    data() {
        return {
            visible: true
        }
    },
    methods: {
        close() {
            this.visible = false
            this.$store.commit("acceptCookies")
        }
    },
    mounted() {
        console.log()
        this.visible = !this.$store.state.cookiesAccepted
    }
}
</script>
<style>

</style>