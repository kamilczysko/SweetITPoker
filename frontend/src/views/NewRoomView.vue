<template>
    <div class='w-full h-full flex flex-col items-center justify-center font-secondary'>
        <p class='text-center text-[red] font-light h-5'> {{ errorMessage }}</p>
        <div class='h-3/6 w-1/4 relative'>
            <img src="../assets/sad.png" class='w-7 h-7 absolute right-5 -top-6 translate-x-full'>
            <div class='flex flex-col items-center rounded-lg py-10 shadow-bg3 shadow-xl bg-gradient-to-tr from-bg1 to-bg2'>
                <div class='flex justify-center relative w-full'>
                    <h1 class='mb-10 font-main text-xl'>Create new room!</h1>
                </div>
                <div class='rounded-md border-spacing-3 flex flex-col gap-7 items-stretch w-full px-5'>
                    <TextInput label="Room name" v-on:inputText="setRoomName" />

                    <div class='bg-bg3 bg-opacity-20 rounded-xl flex flex-col gap-5 items-stretch px-3 pb-4 mt-5'>
                        <p class='text-center font-extralight text-xs -mt-4'>User info</p>
                        <TextInput label="Your name" v-on:inputText="setName" />
                        <Choose label="Your role" :values="roles" v-on:choose="setRole" />
                        <ImageChoose label="Your avatar" :images="prepareAvatars" v-on:select="selectAvatar" />
                    </div>
                </div>
                <CustomButton label="Create new room!" class='mt-5' v-on:clicked="create" />
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
import axios from 'axios'
export default {
    name: "NewRoomView",
    components: { TextInput, Choose, ImageChoose, CustomButton },
    data() {
        return {
            roomName: "",
            name: "",
            roles: [],
            selectedRole: null,
            selectedAvatar: null,
            errorMessage: null,
            host: ""
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
        initPlayer(palyerId){
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
        create() {
            this.$store.commit("clearData")
            if (this.roomName == null || this.roomName == "") {
                this.errorMessage = "Room name is empty!"
                return
            }
            if (this.name == null || this.name == "") {
                this.errorMessage = "Yor name is empty!"
                return
            }

            axios.post(this.host + '/rest/room/create', {
                roomName: this.roomName,
                roomFounder: {
                    name: this.name,
                    avatarIdx: this.selectedAvatar,
                    role: this.selectedRole
                }
            }
            ).then(result => {
                this.$store.commit("initNewRoom", result.data)
                this.initPlayer(result.data.playerId)    
            })
                .then(() => this.$router.push({ name: "game" }))
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