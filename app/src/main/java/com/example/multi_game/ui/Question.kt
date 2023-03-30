package com.example.multi_game.ui

data class Question (
    val question: String,
    var choice: List<String>,
    val answer: String
)