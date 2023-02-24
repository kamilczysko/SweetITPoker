import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createStore } from 'vuex'

import './assets/main.css'

const store = createStore({
    state () {
      return {
        isVotingFinished: false
      }
    },
    mutations: {
      setVotingFinished (state, votingState) {
        state.isVotingFinished = votingState
      }
    }
  })


const app = createApp(App)

app.use(router)
app.use(store)

app.mount('#app')
