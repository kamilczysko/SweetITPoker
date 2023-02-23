<template>
    <div class='w-full h-full flex flex-col items-center justify-center font-secondary'>
        <p class='text-center text-[red] font-light h-5'> {{ errorMessage }}</p>
        <div class='h-3/6 w-2/6 relative'>
            <img src="../assets/sad.png" class='w-7 h-7 absolute right-0 -top-6'>
            <div class='flex flex-col items-center rounded-lg p-10 shadow-bg3 shadow-xl bg-gradient-to-tr from-bg1 to-bg2'>
                <div class='flex justify-center relative w-full'>
                    <h1 class='mb-10 font-main text-xl'>Join room!</h1>
                </div>
                <div class='rounded-md border-spacing-3 flex flex-col gap-7 items-stretch'>
                    <TextInput label="Your name" v-on:inputText="setName" />
                    <Choose label="Your role" :values="roles" v-on:choose="setRole" />
                    <ImageChoose label="Your avatar" :images="prepareAvatars" v-on:select="selectAvatar" />
                </div>
                <div class='mt-8 flex flex-col items-center justify-center w-full'>
                    <CustomButton label="Join!" v-on:clicked="join" />
                    <p class='font-extralight'>or</p>
                    <CustomButton label="Create new one!" v-on:clicked="newRoom" />
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

export default {
    name: "JoinView",
    components: { TextInput, Choose, ImageChoose, CustomButton },
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
        selectAvatar(avatar) {
            this.selectedAvatar = avatar
        },
        getRoles() {
            return roles
        },
        join() {
            if (this.name == null || this.name === "") {
                this.errorMessage = "Yor name is empty!"
                return
            }
            this.errorMessage = null
        },
        newRoom() {
            this.$router.push({name: "create"})
        }
    },

    created() {
        this.selectedRole = this.roles[0]
        this.selectedAvatar = this.prepareAvatars[0]
        this.roles = Array.from(roles)
    }
}
</script>
<style></style>