<script>
  import Hand from './Hand.vue'
  import Controls from './Controls.vue'
  import GameOverButton from './GameOverButton.vue'
  import { mapState, mapGetters } from 'vuex'
  export default {
    components: {
      Hand,
      Controls,
      GameOverButton
    },
    computed: {
      ...mapState([
        'activeHandIndex',
        'hands',
        'showDrawer'
      ]),
      ...mapGetters(['isGameOver'])
    },
    methods: {
      closeDrawer () {
        this.$store.commit('toggleDrawer', { show: false })
      }
    }
  }
  </script>

<template>
    <main
      @click="closeDrawer"
      :class="{ 'is-dimmed': showDrawer }"
      class="game-area"
    >
      <section class="dealer-side">
        <Hand
          v-if="hands.length"
          :hand="hands[0]"
          :index="0"
        />
      </section>
      <section class="player-side">
        <Hand
          v-for="(hand, i) in hands"
          :key ="i"
          v-if="i > 0"
          :hand="hand"
          :index="i"
        />
      </section>
      <GameOverButton v-if="isGameOver" />
      <Controls :class="{ 'no-pointer-events': showDrawer }" />
    </main>
  </template>
  
  <style>
  .game-area {
    height: 100%;
    display: flex;
    flex: 1;
    flex-direction: column;
    z-index: 50;
  }
  .is-dimmed {
    opacity: 0.5;
  }
  .no-pointer-events {
    pointer-events: none;
  }
  .dealer-side {
    margin-top: 1rem;
    display: flex;
    flex-flow: row nowrap;
    justify-content: center;
    align-items: center;
    min-height: 12.4rem;
  }
  .player-side {
    flex: 1 0;
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-around;
    align-items: center;
  }
  </style>