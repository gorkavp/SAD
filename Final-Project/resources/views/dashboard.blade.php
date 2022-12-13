<x-app-layout>
    <x-slot name="header">
        <h2 class="font-semibold text-xl text-gray-800 leading-tight">
            {{ __('Welcome to YowChat!') }}
        </h2>
    </x-slot>

    <div class="py-12">
        <div class="max-w-7xl mx-auto sm:px-6 lg:px-8">
            <div class="bg-white overflow-hidden shadow-sm sm:rounded-lg">
                <div class="p-6 bg-white border-b border-gray-200">
                    Please enter the user_id of the person you want to chat with.
                </div>
            </div>
            <div style="margin-top: 18px">
                <form class="msger-inputarea" method="get">
                    <input type="text" id="user_id" class="msger-input" placeholder="user_id">
                    <button type="submit" class="btnChat" onclick="myfunction()">Chat</button>
                </form>
            </div>
        </div>
    </div>

</x-app-layout>
