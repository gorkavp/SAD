<script setup>
import AuthenticatedLayout from '@/Layouts/AuthenticatedLayout.vue';
import { Head, Link } from '@inertiajs/inertia-vue3';
</script>

<template>
    <Head title="Dashboard" />

    <AuthenticatedLayout>
        <template #header>
            <h2 class="font-semibold text-xl text-gray-800 leading-tight">Blackjack</h2>
        </template>
        <Link :href="route('gameroom')" class="grid place-items-center h-screen underline">Game Room </Link>
        
    </AuthenticatedLayout>
</template>