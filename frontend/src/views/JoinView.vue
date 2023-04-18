<template>
    <div class='flex flex-col items-center'>
        <Header></Header>
        <div class='w-full h-full flex flex-col items-center justify-center font-secondary'>
            <p class='text-center text-[red] font-light h-5'> {{ errorMessage }}</p>
            <div class='h-3/6 md:w-2/5 xs:w-[90vw] relative'>
                <img src="../assets/sad.png" class='w-7 h-7 absolute right-5 -top-6'>
                <div class='flex flex-col items-center rounded-lg py-10 shadow-bg3 shadow-xl 
                        bg-gradient-to-tr from-bg1 to-bg2'>
                    <div class='flex justify-center relative'>
                        <h1 class='mb-10 font-main text-xl'>Join room!</h1>
                    </div>
                    <div class='flex flex-col xs:gap-10 md:gap-7 xs:w-[95vw] md:w-3/4'>
                        <TextInput label="Your name" v-on:inputText="setName" />
                        <Choose label="Your role" :values="roles" v-on:choose="setRole" />
                        <ImageChoose label="Your avatar" :images="prepareAvatars" v-on:select="selectAvatar" />
                    </div>
                    <div class='mt-8 flex flex-col items-center justify-center w-full'>
                        <CustomButton label="Join!" v-on:clicked="join" />
                        <p class='font-extralight'>or</p>
                        <CustomButton label="Create new one!" v-on:clicked="newRoom" />
                        <small class="text-xs w-8/12 font-extralight text-center mt-2">
                        This site is protected by reCAPTCHA and the Google
                        <a class='text-blue-500' href="https://policies.google.com/privacy">Privacy Policy</a> and
                        <a class='text-blue-500' href="https://policies.google.com/terms">Terms of Service</a> apply.
                    </small>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>
<script>
const avatars = import.meta.globEager('../assets/avatars/*.png')
import roles from '../assets/roles.js'

import CustomButton from '../components/CustomButton.vue'
import Choose from '../components/Choose.vue';
import ImageChoose from '../components/ImageChoose.vue';
import TextInput from '../components/TextInput.vue';
import Header from '../components/Header.vue';

import axios from 'axios';

export default {
    name: "JoinView",
    components: { TextInput, Choose, ImageChoose, CustomButton, Header },
    data() {
        return {
            name: "",
            roles: [],
            selectedRole: null,
            selectedAvatar: null,
            errorMessage: null,
        }
    },
    methods: {
        setName(name) {
            this.name = name
        },
        setRole(role) {
            this.selectedRole = role
        },
        prepareAvatars() {
            return Object.values(avatars)
                .map(a => a.default)
        },
        selectAvatar(avatarIdx) {
            this.selectedAvatar = avatarIdx
        },
        getRoles() {
            return roles
        },
        initPlayer(palyerId) {
            const player = {
                id: palyerId,
                name: this.name,
                avatarIdx: this.selectedAvatar,
                isAdmin: true,
                isObserver: false,
                selectedCard: null,
                role: this.selectedRole
            }
            this.$store.commit("setPlayers", [player])
        },
        async join() {
            this.$store.commit("clearData")
            if (this.name == null || this.name === "") {
                this.errorMessage = "Yor name is empty!"
                return
            }

            if (this.name.length > 15) {
                this.errorMessage = "Yor name is too long!"
                return
            }

            await this.$recaptchaLoaded()
            const token = await this.$recaptcha('homepage')

            axios.post("/rest/room/join/" + this.$route.params.id, {
                name: this.name.trim(),
                avatarIdx: this.selectedAvatar,
                role: this.selectedRole,
                token: token
            })
                .then(response => response.data)
                .then(data => {
                    this.initPlayer(data.playerId)
                    this.$store.commit("join", data)
                })
                .then(() => this.$router.push({ name: "game" }))
                .catch(error => this.errorMessage = "Join room error! " + error)

            this.errorMessage = null
        },
        newRoom() {
            this.$router.push({ name: "create" })
        }
    },

    created() {
        this.selectedRole = this.roles[0]
        this.selectedAvatar = this.prepareAvatars[0]
        this.roles = Array.from(roles)
        this.selectedRole = this.roles[0]
    }
}
</script>
<style></style>