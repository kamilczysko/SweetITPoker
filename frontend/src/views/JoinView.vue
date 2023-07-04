<template>
    <div class="bg-background-first bg-cover xs:h-full md:h-screen">
        <Header></Header>
        <div class="px-[1vw] flex flex-row justify-center items-center">
            <div class="xs:flex xs:flex-col md:grid md:grid-cols-header">
                <Description></Description>
                <div class="h-full w-full"></div>
                <div>
                    <JoinForm @join="join" @create="create"></JoinForm>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import Header from '@/components/Header.vue'
import Description from '@/components/Description.vue'
import JoinForm from '../components/form/JoinForm.vue';
import axios from 'axios'
export default {
    name: "JoinView",
    components: { Header, Description, JoinForm },
    methods: {
        async join(data) {
            this.$store.commit("cleanup")
            const roomId = this.$route.params.id

            await this.$recaptchaLoaded()
            const token = await this.$recaptcha('homepage')

            await axios.post('/player', {
                roomId: roomId,
                player: {
                    name: data.name,
                    avatarIdx: data.avatarIdx,
                    role: data.role.value
                },
                token: token
            })
                .then(res => res.data.playerId)
                .then(playerId => this.$store.commit('joinRoom', { name: data.name, avatarIdx: data.avatarIdx, role: data.role.label, playerId: playerId, roomId: roomId }))

            axios.get('/room/' + roomId)
                .then(res => res.data)
                .then(data => this.$store.commit('loadRoom', data))
                .then(() => this.$router.push({ name: 'room', params: { id: this.$store.state.roomId } }))
        },
        create() {
            this.$router.push({ name: 'start' })
        }
    },
    mounted() {
        // axios.defaults.baseURL = 'http://localhost:8080';
    }
}
</script>