<script>
  import GameButton from './GameButton.vue'
  import Bank from './Bank.vue'
  import { mapGetters, mapState } from 'vuex'
  export default {
    components: {
      GameButton,
      Bank
    },
    computed: {
      isPlayerTurn () {
        return !this.isDealing && this.activeHandIndex > 0
      },
      ...mapState([
        'isDealing',
        'activeHandIndex'
      ]),
      ...mapGetters([
        'canSplit',
        'canDoubleDown'
      ])
    }
  }
  </script>

<template>
    <div class="controls-row">
      <div class="controls">
        <GameButton
          action="doubleDown"
          :is-enabled="canDoubleDown && isPlayerTurn"
        />
        <GameButton
          action="split"
          :is-enabled="canSplit && isPlayerTurn"
        />
        <Bank />
        <GameButton
          action="stand"
          :is-enabled="isPlayerTurn"
        />
        <GameButton
          action="hit"
          :is-enabled="isPlayerTurn"
        />
      </div>
    </div>
  </template>
  
  <style>
  .controls-row {
    text-align: center;
  }
  .controls {
    display: inline-flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
  }
  </style>