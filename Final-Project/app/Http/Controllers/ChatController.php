<?php

namespace App\Http\Controllers;

use App\Http\Requests\ProfileUpdateRequest;
use App\Models\Chat;
use App\Models\User;
use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Redirect;
use Inertia\Inertia;

class ChatController extends Controller
{

    public function __construct() {
        $this->middleware('auth');
    }

    public function chat_with(User $user) {
        $user_a = auth()->user();
        $user_b = $user;
        $chat = $user_a->chats()->whereHas('users', function ($query) use ($user_b) {
            $query->where('chat_user.user_id', $user_b->id);
        })->first();
        if (!$chat) {
            $chat = Chat::create();
            $chat->users()->sync([$user_a->id, $user_b->id]);
        }
        //redirect()->route('chat.show', $chat);
        return Redirect::route('chat.show', $chat);
    }
    
    public function show(Chat $chat) {
        abort_unless($chat->users->contains(auth()->id()), 403);
        return view('chat', [
            'chat' => $chat,]);
    }
}
