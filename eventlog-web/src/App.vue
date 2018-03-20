<template>
  <div id="app">
    <simplert :useRadius="true" :useIcon="true" ref="simplert">
    </simplert>
    <div class="mb-1">
      <router-link to="/">Log</router-link>
      <router-link to="/statistics">Statistics</router-link>
    </div>
    <router-view/>
  </div>
</template>

<script>
import Simplert from 'vue2-simplert'

import { EventBus } from '@/components/event-bus.js'

export default {
  name: 'App',
  components: {
    Simplert
  },
  mounted() {
    EventBus.$on('open-message', data => this.openMessage(data))
  },
  methods: {
    openMessage(data) {
      this.$refs.simplert.openSimplert(data)
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  margin-left: -300px;
  width: 600px;
  position: absolute;
  left: 50%;
}
</style>
