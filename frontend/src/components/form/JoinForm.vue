<template>
    <div
        class='font-secondary flex flex-col justify-center rounded-xl backdrop-blur-sm bg-slate-50 bg-opacity-30 w-[95%] mx-auto relative'>
        <p class="border-2 border-red-500  font-secondary text-red-700 bg-opacity-70 rounded-2xl bg-slate-300 mb-3 min-w-1/2 text-center absolute mx-auto -top-10 z-50 px-3"
            v-show="error != null">{{ error }}</p>
        <div class='flex flex-col gap-5 items-center pt-3'>
            <h1 class='mb-8 font-main xs:text-lg lg:text-2xl'>Join room!</h1>
            <div class='flex flex-col xs:gap-10 md:gap-8 w-4/5 py-3 bg-black bg-opacity-5 rounded-lg shadow-xl '>
                <TextInput label="Your name" @inputText="setName" />
                <Choose label="Your role" :values="roles" @choose="setRole" />
                <ImageChoose label="Your avatar" :images="prepareAvatars" @select="setAvatar" />
            </div>
            <div class='flex flex-col gap-1 items-center xs:w-4/5 md:w-2/3'>
                <p :class="{ [`invisible`]: !isLoading }" class='text-center text-sm font-extralight'>
                    Loading...
                </p>
                <CustomButton label="Join!" @clicked="join" class=' bg-opacity-70' />
                <p class='font-extralight'>or</p>
                <CustomButton label="Create new one!" v-on:clicked="newRoom" class=' bg-opacity-70' />
            </div>
            <CaptchaInfo></CaptchaInfo>
        </div>
    </div>
</template>
<script>
const avatars = import.meta.globEager('../../assets/avatars/*.png')
import roles from '../../assets/roles.js'

import TextInput from '@/components/inputs/TextInput.vue'
import Choose from '@/components/inputs/Choose.vue'
import ImageChoose from '@/components/inputs/ImageChoose.vue'
import CustomButton from '../controls/CustomButton.vue'
import CaptchaInfo from '../CaptchaInfo.vue'

export default {
    name: "JoinForm",
    components: { TextInput, Choose, ImageChoose, CustomButton, CaptchaInfo },
    data() {
        return {
            roles: [],
            selectedRole: null,
            isLoading: false,
            playerData: {},
            error: null
        }
    },
    methods: {
        prepareAvatars() {
            return Object.values(avatars)
                .map(a => a.default)
        },
        setName(name) {
            this.playerData.name = name
        },
        setAvatar(index) {
            this.playerData.avatarIdx = index
        },
        setRole(role) {
            this.playerData.role = role
        },
        join() {
            this.error = this.validateInputs()
            if(this.error) {
                return
            }
            this.error = null
            if(this.isLoading){
                return
            }
            this.isLoading = true
            this.$emit('join', this.playerData)
        },
        validateInputs() {
            if (this.playerData.name == null || this.playerData.name.trim() == "") {
                return "Player name is empty!"
            }
            if (this.playerData.name.length > 18) {
                return "Player name is too long! (15 characters max)"
            }
            return null
        },
        newRoom() {
            this.$emit('create')
        }
    },
    created() {
        this.roles = Array.from(roles)


    }
}
</script>