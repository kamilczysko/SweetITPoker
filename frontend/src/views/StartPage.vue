<template>
    <div class='flex flex-col items-center'>
        <Header></Header>
        <div class='flex justify-center gap-0 xs:flex-col xs:items-center lg:flex-row'>
            <transition name="slide-left" appear delay>
                <Description class='xs:w-full lg:w-[45vw] xs:px-5 lg:pr-10'></Description>
            </transition>
            <div class='border-l-[1px] border-black h-[60vh] mr-5'></div>
            <transition name="slide-right" appear delay>
                <div class='font-secondary flex flex-col items-center xs:w-[90vw] lg:w-[45vw] xs:border-t-[1px] xs:border-l-0 lg:border-t-0 rounded-xl backdrop-blur-sm bg-slate-50 bg-opacity-20'>
                    <img src="../assets/sad.png" class='w-7 h-7 absolute right-10 -top-6 translate-x-full'>
                    <p class='text-center text-[red] font-light h-5'> {{ errorMessage }}</p>
                    <div class='flex justify-center relative'>
                        <h1 class='xs:mb-3 lg:mb-10 font-main xs:text-sm lg:text-2xl'>Create new room</h1>
                    </div>
                    <div class='border-spacing-3 flex flex-col gap-10 items-stretch w-[95%] rounded-xl'>
                        <TextInput label="Room name" v-on:inputText="setRoomName" />
                        <div
                            class=' shadow-xl rounded-xl flex flex-col gap-10 items-stretch px-5 pb-5 xs:gap-15 relative bg-black bg-opacity-5'>
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
            </transition>
        </div>
    </div>
</template>
<script>
const avatars = import.meta.globEager('../assets/avatars/*.png')
import roles from '../assets/roles.js'

import Description from '../components/Description.vue'
import CustomButton from '../components/CustomButton.vue'
import Choose from '../components/Choose.vue';
import ImageChoose from '../components/ImageChoose.vue';
import TextInput from '../components/TextInput.vue';
import Header from '../components/Header.vue';
import axios from 'axios'

export default {
    name: "StartPage",
    components: { TextInput, Choose, ImageChoose, CustomButton, Header, Description },
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
                this.errorMessage = "Your name is empty!"
                return
            }

            if (this.name.length > 15) {
                this.errorMessage = "Your name is too long!"
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
<style>
.slide-right-enter-from {
    opacity: 0;
    transform: translateY(200px);
}

.slide-right-enter-to {
    transition: all 0.3s ease;
}

.slide-left-enter-from {
    opacity: 0;
    transform: translateY(-200px);
}

.slide-left-enter-to {
    transition: all 0.3s ease;
}

/* .fade-enter-from {
    opacity: 0;
}

.fade-enter-to {
    transition: opacity 0.3s ease;
} */
</style>