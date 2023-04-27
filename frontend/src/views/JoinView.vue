<template>
    <div class='flex flex-col items-center'>
        <Header></Header>
        <div class='flex gap-1 justify-center'>
            <Description></Description>
            <div class='w-4 h-[60vh] border-black border-l-[1px] border-spacing-1'></div>
            <div class='font-secondary flex flex-col justify-center'>
                <p class='text-center text-[red] font-light h-5'> {{ errorMessage }}</p>
                <div class='flex flex-col gap-5 items-center'>
                    <h1 class='mb-10 font-main text-xl'>Join room!</h1>
                    <div class='flex flex-col xs:gap-10 md:gap-8 xs:w-[95vw] lg:w-3/4 border-b-gray-400 border-b-[1px] pb-3'>
                        <TextInput label="Your name" v-on:inputText="setName" />
                        <Choose label="Your role" :values="roles" v-on:choose="setRole" />
                        <ImageChoose label="Your avatar" :images="prepareAvatars" v-on:select="selectAvatar" />
                    </div>
                    <div class='mt-4 flex flex-col gap-3 items-center xs:w-4/5 md:w-2/3'>
                        <CustomButton label="Join!" v-on:clicked="join" />
                        <p class='font-extralight'>or</p>
                        <CustomButton label="Create new one!" v-on:clicked="newRoom" />
                    </div>
                    <small class="text-xs w-8/12 font-extralight text-center mt-3">
                        This site is protected by reCAPTCHA and the Google
                        <a class='text-blue-500' href="https://policies.google.com/privacy">Privacy Policy</a> and
                        <a class='text-blue-500' href="https://policies.google.com/terms">Terms of Service</a> apply.
                    </small>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
const avatars = import.meta.globEager('../assets/avatars/*.png')
import roles from '../assets/roles.js'

import Description from '../components/Description.vue';
import CustomButton from '../components/CustomButton.vue'
import Choose from '../components/Choose.vue';
import ImageChoose from '../components/ImageChoose.vue';
import TextInput from '../components/TextInput.vue';
import Header from '../components/Header.vue';

import axios from 'axios';

export default {
    name: "JoinView",
    components: { TextInput, Choose, ImageChoose, CustomButton, Header, Description },
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
                this.errorMessage = "Your name is empty!"
                return
            }

            if (this.name.length > 15) {
                this.errorMessage = "Your name is too long!"
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
                .catch(error => this.errorMessage = "Cannot join room! Room probably doesn't exits anymore.")
                

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