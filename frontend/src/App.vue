<script setup>
import { RouterView } from 'vue-router'
import CookiesPrompt from './components/CookiesPrompt.vue';
</script>

<template>
  <main class='h-screen w-screen bg-gradient-to-tr from-[#D9AFD9] to-[#97D9E1]'>
    <RouterView />
  </main>
  <CookiesPrompt></CookiesPrompt>
</template>

<style>
.grecaptcha-badge {
  opacity:0;
}
</style>
