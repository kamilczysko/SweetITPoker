<template>
    <div class='flex flex-col items-center'>
        <Header></Header>

        <div class='flex gap-1 justify-center'>
            <article class='w-1/3 mx-5'>
                <div class='flex flex-col justify-center items-center gap-5'>
                    <h2 class='text-2xl'>Your perfect planning tool</h2>
                    <p class='font-secondary text-md font-extralight text-center'>
                        <b>UwUstimates</b> is a planning poker tool for development teams. It's a easy to use tool, helping you and your team to estimate and manage tasks in the Agile methodology.
                    </p>
                    <div class='mt-5 self-start'>
                        <!-- <h2 class='text-xl font-secondary'>With this tool you can became:</h2> -->
                        <ul class='font-secondary text-xl list-outside list-disc ml-20'>
                            <li class='p-3'>Be more accurate</li>
                            <li class='p-3'>Play it anywhere</li>
                            <li class='p-3'>Be more proffesional</li>
                        </ul>
                    </div>
                </div>
                
            </article>
            <div class='w-4 h-[60vh] border-black border-l-[1px] border-spacing-1'></div>
            <div class='font-secondary flex flex-col items-center justify-center'>
                <div class='flex justify-center relative'>
                    <h1 class='xs:mb-3 lg:mb-10 font-main xs:text-sm lg:text-2xl'>Create new room</h1>
                </div>
                <div class='rounded-md border-spacing-3 flex flex-col gap-7 items-stretch w-full px-5'>
                    <TextInput label="Room name" v-on:inputText="setRoomName" />

                    <div
                        class='bg-bg3 bg-opacity-20 rounded-xl flex flex-col lg:gap-5 xs:gap-10 items-stretch px-3 pb-4 mt-5 xs:gap-15 relative'>
                        <img src="../assets/sad.png" class='w-7 h-7 absolute right-10 -top-6 translate-x-full'>
                        <p class='text-center font-extralight text-xs -mt-4'>User info</p>
                        <TextInput label="Your name" v-on:inputText="setName" />
                        <Choose label="Your role" :values="roles" v-on:choose="setRole" />
                        <ImageChoose label="Your avatar" :images="prepareAvatars" v-on:select="selectAvatar" />
                    </div>
                </div>
                <div class='xs:w-4/5 md:w-1/2 flex flex-col items-center justify-center gap-0'>
                    <p :class="{ [`invisible`]: !isLoading }" class='text-center h-1 text-sm font-extralight'>
                        Loading...
                    </p>
                    <CustomButton :class="{ ['opacity-60']: isLoading }" label="Create new room!" class='mt-5'
                        v-on:clicked="create" />
                </div>
                <small class="text-xs w-8/12 font-extralight text-center mt-3">
                    This site is protected by reCAPTCHA and the Google
                    <a class='text-blue-500' href="https://policies.google.com/privacy">Privacy Policy</a> and
                    <a class='text-blue-500' href="https://policies.google.com/terms">Terms of Service</a> apply.
                </small>

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
import axios from 'axios'

export default {
    name: "StartPage",
    components: { TextInput, Choose, ImageChoose, CustomButton, Header },
    data() {
        return {
            roomName: "",
            name: "",
            roles: [],
            selectedRole: null,
            selectedAvatar: null,
            errorMessage: null,
            isLoading: false
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
        setRoomName(roomName) {
            this.roomName = roomName
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
        async create() {
            if (this.isLoading) {
                return
            }

            this.$store.commit("clearData")
            if (this.roomName == null || this.roomName == "") {
                this.errorMessage = "Room name is empty!"
                return
            }

            if (this.roomName.length > 15) {
                this.errorMessage = "Room name is too long!"
                return
            }

            if (this.name == null || this.name == "") {
                this.errorMessage = "Yor name is empty!"
                return
            }

            if (this.name.length > 15) {
                this.errorMessage = "Yor name is too long!"
                return
            }

            this.isLoading = true
            await this.$recaptchaLoaded()
            const token = await this.$recaptcha('homepage')

            axios.post('/rest/room/create', {
                token: token,
                roomName: this.roomName.trim(),
                roomFounder: {
                    name: this.name.trim(),
                    avatarIdx: this.selectedAvatar,
                    role: this.selectedRole
                }
            }
            ).then(result => {
                this.$store.commit("initNewRoom", result.data)
                this.initPlayer(result.data.playerId)
                this.$router.push({ name: "game" })
            })
                .catch((error) => this.errorMessage = "Network error! Refresh page" + error)

            this.errorMessage = null
        }
    },

    created() {
        this.roles = Array.from(roles)
        this.selectedRole = this.roles[0]
        this.selectedAvatar = 0
        this.selectedRole = this.roles[0]
    },
    computed: {
        getErrorMessage() {
            return this.errorMessage
        }
    }
}
</script>
<style></style>