import { createRouter, createWebHistory } from 'vue-router'
import StartView from '@/views/StartView.vue'
import JoinView from '@/views/JoinView.vue'
import RoomView from '@/views/RoomView.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'start',
      component: StartView
    },
    {
      path: '/join/:id',
      name: 'join',
      component: JoinView
    },
    {
      path: '/room/:id',
      name: 'room',
      component: RoomView
    }
  ]
})

export default router
