import { createRouter, createWebHistory } from 'vue-router'
import JoinView from '../views/JoinView.vue'
import NewRoomView from '../views/NewRoomView.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'newRoom',
      component: NewRoomView
    },
    {
      path: '/join',
      name: 'join',
      component: JoinView
    }
  ]
})

export default router
