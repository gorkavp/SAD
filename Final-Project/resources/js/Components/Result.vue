<script>
  import Blackjack from './Blackjack.vue'
  import Bust from './Bust.vue'
  import Lose from './Lose.vue'
  import Push from './Push.vue'
  import Win from './Win.vue'
  export default {
    components: {
      Blackjack,
      Bust,
      Lose,
      Push,
      Win
    },
    props: {
      result: {
        type: String,
        required: false
      }
    }
  }
  </script>

<template>
    <transition name="balloon">
      <div
        v-if="result"
        class="hand-result"
      >
        <svg :is="'result-' + result.toLowerCase()"></svg>
      </div>
    </transition>
  </template>
  
  <style>
  .hand-result {
    position: absolute;
    width: 26rem;
    top: 2rem;
    left: calc(50% - 13rem);
    text-align: center;
  }
  .hand-result svg {
    width: 26rem;
  }
  .balloon-enter-active {
    animation: balloon-in ease-in-out 0.6s;
    transform-origin: 50% 100%;
  }
  .balloon-leave-active {
    transition: all 0.1s;
    transform-origin: 50% 100%;
  }
  .balloon-leave-to {
    transform: scale(0);
    opacity: 0;
  }
  @keyframes balloon-in {
    0% {
      transform: scale(0.00) skewX(0deg) rotate(-20deg);
    }
    40% {
      transform: scale(1.00) skewX(-5deg) rotate(10deg);
    }
    50% {
      transform: skewX(5deg) rotate(-5deg);
    }
    60% {
      transform: skewX(-4deg) rotate(3deg);
    }
    70% {
      transform: skewX(4deg) rotate(-1deg);
    }
    80% {
      transform: skewX(-3deg) rotate(0deg);
    }
    85% {
      transform: skewX(3deg);
    }
    90% {
      transform: skewX(-2deg);
    }
    95% {
      transform: skewX(2deg);
    }
    100% {
      transform: skewX(0deg);
    }
  }
  </style>