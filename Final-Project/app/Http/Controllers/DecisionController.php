<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class DecisionController extends Controller
{
    public function __construct() {

        $this->middleware('auth');
    }
    public function sent(Request $request) {

        /*$decision = auth()->user()->decisions()->create([
            'decision' => $request->decision,
        ]);->load('user');

        broadcast(new DecisionMade($decision))->toOthers();

        return $decision;*/
    }
}
