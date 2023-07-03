<script setup>
import { RouterView } from 'vue-router'
import CookiesPrompt from './components/CookiesPrompt.vue';
</script>

<template>
  <div class='min-w-screen min-h-screen'>
    <RouterView />
  </div>
  <CookiesPrompt></CookiesPrompt>
</template>

<style>
.grecaptcha-badge {
  opacity: 0;
}
</style>
