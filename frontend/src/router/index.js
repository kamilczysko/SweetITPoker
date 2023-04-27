import { createRouter, createWebHistory } from 'vue-router'
import JoinView from '../views/JoinView.vue'
import NewRoomView from '../views/NewRoomView.vue'
import PokerTableView from '../views/PokerTableView.vue'
import StartPage from '../views/StartPage.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [

    {
      path: '/create',
      name: 'create',
      component: NewRoomView
    },    {
      path: '/',
      name: 'start',
      component: StartPage
    },{
      path: '/game',
      name: 'game',
      component: PokerTableView
    },
    {
      path: '/join/:id',
      name: 'join',
      component: JoinView
    }
  ]
})

export default router
