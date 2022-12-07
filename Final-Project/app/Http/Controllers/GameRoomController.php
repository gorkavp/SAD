<?php

namespace App\Http\Controllers;

use App\Http\Requests\ProfileUpdateRequest;
use App\Models\GameRoom;
use App\Models\User;
use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Redirect;
use Inertia\Inertia;

class GameRoomController extends Controller
{

    public function __construct() {
        $this->middleware('auth');
    }
    
    public function show(GameRoom $gameroom) {
        abort_unless($gameroom->users->contains(auth()->id()), 403);
        return Inertia::render('GameRoom', [
            'gameroom' => $gameroom,
        ]);
    }
}