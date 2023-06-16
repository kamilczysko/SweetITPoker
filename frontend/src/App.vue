<script setup>
import { RouterView } from 'vue-router'
import CookiesPrompt from './components/CookiesPrompt.vue';
</script>

<template>
  <main class='min-h-screen min-w-full scrollbar-hide'>
    <RouterView />
  </main>
  <CookiesPrompt></CookiesPrompt>
</template>

<style>
.grecaptcha-badge {
  opacity: 0;
}
</style>
