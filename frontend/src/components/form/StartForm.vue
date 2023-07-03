<template>
    <div
        class='font-secondary flex flex-col items-center rounded-xl backdrop-blur-sm bg-slate-50 bg-opacity-30 w-[95%] mx-auto'>
        <div class='flex justify-center relative'>
            <h1 class='xs:mb-3 lg:mb-10 font-main xs:text-sm lg:text-2xl pt-3'>Create new room</h1>
        </div>
        <div class='border-spacing-3 flex flex-col gap-10 items-stretch w-[95%] rounded-xl'>
            <TextInput label="Room name" @inputText="setRoomName" />
            <div
                class=' shadow-xl rounded-xl flex flex-col gap-10 items-stretch px-5 pb-5 xs:gap-15 relative bg-black bg-opacity-5'>
                <p class='text-center underline underline-offset-4 text-xs -mt-4'>User info</p>
                <TextInput label="Your name" @inputText="setName" />
                <Choose label="Your role" :values="roles" @choose="setRole" />
                <ImageChoose label="Your avatar" :images="prepareAvatars" @select="setAvatar" />
            </div>
        </div>
        <div class='xs:w-4/5 md:w-1/2 flex flex-col items-center justify-center gap-0'>
            <p :class="{ [`invisible`]: !isLoading }" class='text-center h-1 text-sm font-extralight'>
                Loading...
            </p>
            <CustomButton @clicked="send" :class="{ ['opacity-60']: isLoading }" label="Create new room!"
                class='mt-5 bg-opacity-70' />
        </div>
        <CaptchaInfo></CaptchaInfo>
    </div>
</template>
<script>
const avatars = import.meta.globEager('../../assets/avatars/*.png')
import roles from '../../assets/roles.js'
import carddeck from '@/assets/card/carddeck.js'
import units from '@/assets/card/units.js'

import TextInput from '@/components/inputs/TextInput.vue'
import Choose from '@/components/inputs/Choose.vue'
import ImageChoose from '@/components/inputs/ImageChoose.vue'
import CaptchaInfo from '../CaptchaInfo.vue'
import CustomButton from '../controls/CustomButton.vue'
export default {
    name: "StartForm",
    components: { TextInput, Choose, ImageChoose, CaptchaInfo, CustomButton },
    data() {
        return {
            errorMessage: "sdf",
            roles: [],
            selectedRole: null,
            isLoading: false,
            playerData: {
            },
            roomData: {
                cardValues: carddeck[0].values,
                units: units[0].values
            }
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
            this.playerData.role = role.value
        },
        setRoomName(name) {
            this.roomData.name = name
        },
        setRoomCardValues(cardValues) {
            this.roomData.cardsValues = cardValues
        },
        setRoomCardUnits(units) {
            this.roomData.units = units
        },
        send() {
            const dto = {
                name: this.roomData.name,
                cardsValues: this.roomData.cardValues,
                units: this.roomData.units,
                roomCreator: this.playerData
            }
            this.$emit("startRoom", dto)
        }
    },
    created() {
        this.roles = Array.from(roles)
    }
}
</script>