package com.example.multi_game.ui

data class MathGameUniState (
    val useQuestion: MathQuestion = MathQuestion("", ""),
    val numQue: Int = 1,
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val currentCount: Int = 0,
    val isGuessWrong: Boolean = false,
        )