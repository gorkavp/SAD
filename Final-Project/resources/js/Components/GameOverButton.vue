<script>
  import GameButton from './GameButton.vue'
  export default {
    components: {
      GameButton
    }
  }
  </script>

<template>
    <div class="game-over">
      <GameButton
        action="restart"
        :is-enabled="true"
      />
    </div>
  </template>
    
  <style>
  .game-over {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 1rem;
  }
  </style>