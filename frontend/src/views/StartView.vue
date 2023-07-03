<template>
    <div class="bg-background-first bg-cover xs:h-full md:h-screen">
        <Header></Header>
        <div class="px-[1vw] flex flex-row justify-center items-center">
            <div class="xs:flex xs:flex-col md:grid md:grid-cols-header">
                <Description></Description>
                <div class="h-full w-full shadow-black"></div>
                <div class="flex flex-col justify-center items-center relative">
                    <p class="border-2 border-red-500  font-secondary text-red-700 h-7 bg-opacity-70 rounded-2xl bg-slate-300 mb-3 min-w-1/2 text-center absolute mx-auto -top-10 z-50 px-3" v-show="error != null">{{ error }}</p>
                    <StartForm @startRoom="createNewRoom"></StartForm>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import Header from '@/components/Header.vue'
import Description from '@/components/Description.vue'
import StartForm from '@/components/form/StartForm.vue'
import axios from 'axios'

export default {
    name: "StartView",
    components: { Header, Description, StartForm },
    data() {
        return {
            error: null
        }
    },
    methods: {
        async createNewRoom(newRoomData) {
            this.$store.commit("cleanup")
            
            if(newRoomData.name == null || newRoomData.name.trim() == "") {
                this.error = "Room name is empty!"
                return
            }
            if(newRoomData.roomCreator.name == null  || newRoomData.roomCreator.name.trim() == "") {
                this.error = "Player name is empty!"
                return
            }

            await this.$recaptchaLoaded()
            const token = await this.$recaptcha('homepage')
            
            newRoomData.token = token

            axios.post("/room", newRoomData)
                .then(result => result.data)
                .then(ids => this.$store.commit('initRoom', { ids: ids, data: newRoomData }))
                .then(() => this.$router.push({ name: 'room', params: { id: this.$store.state.roomId } }))
                .catch(error => this.error = "Cannot create room")
        }
    },
    mounted() {
        axios.defaults.baseURL = 'http://localhost:8080';
    }
}
</script>