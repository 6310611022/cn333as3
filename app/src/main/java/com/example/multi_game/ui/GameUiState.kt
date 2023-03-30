package com.example.multi_game.ui

import com.example.multi_game.ui.Question

data class GameUiState(
    val useQuestion: Question = Question("", listOf("", "", "", ""), ""),
    val numQue: Int = 1,
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val currentCount: Int = 0,
)