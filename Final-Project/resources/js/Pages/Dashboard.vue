<script setup>
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import { Head } from '@inertiajs/inertia-vue3';
import PrimaryButton from '@/Components/PrimaryButton.vue';
import TitleScreen from '@/components/TitleScreen.vue'
import Sidebar from '@/components/SideBar.vue'
import Stars from '@/components/Stars.vue'
import GameArea from '@/components/GameArea.vue'
import MenuButton from '@/components/MenuButton.vue'
</script>

<template>
    <Head title="Dashboardd" />

    <AuthenticatedLayout>
        <template #header>
            <h2 class="font-semibold text-xl text-gray-800 leading-tight">Blackjack</h2>
        </template>
        <PrimaryButton class="ml-4 mt-52">
            Stand
        </PrimaryButton>
    </AuthenticatedLayout>
</template>