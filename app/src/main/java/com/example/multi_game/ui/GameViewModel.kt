/*
 * Copyright (c)2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.multi_game.ui

import androidx.compose.material.AlertDialog
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.multi_game.R
import com.example.multi_game.data.SCORE_INCREASE
import com.example.multi_game.data.allQuestions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel containing the app data and methods to process the data
 */
class GameViewModel : ViewModel() {

    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Set of words used in the game
    private var question: MutableSet<Question> = mutableSetOf()
    private lateinit var useQuestion: Question

    init {
        resetGame()
    }

    /*
     * Re-initializes the game data to restart the game.
     */
    fun resetGame() {
        question.clear()
        _uiState.value = GameUiState(useQuestion = pickRandomQuestionAndShuffle())
    }

    /*
     * Update the user's guess
     */
    /*
     * Checks if the user's guess is correct.
     * Increases the score accordingly.
     */

    fun checkUserAnswer(userAnswer: String) {
        var updatedScore = 0
        if (userAnswer.equals(useQuestion.answer)) {
            updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)

        }else {
            updatedScore = _uiState.value.score
            updateGameState(updatedScore)

        }

    }

    /*
     * Skip to next word
     */


    /*
     * Picks a new currentWord and currentScrambledWord and updates UiState according to
     * current game state.
     */
    private fun updateGameState(updatedScore: Int) {
        if (question.size == 10){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    score = updatedScore ,
                    isGameOver = true
                )
            }
        } else{
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    useQuestion = pickRandomQuestionAndShuffle(),
                    numQue = currentState.numQue.inc(),
                    score = updatedScore ,
                    currentCount = currentState.currentCount.inc()
                )
            }
        }
    }

    private fun shuffleCurrentQuestion(question: Question): Question {
        val shuffleChoice = question.choice.toMutableList()
        shuffleChoice.shuffle()
        question.choice = shuffleChoice
        return question
    }

    private fun pickRandomQuestionAndShuffle(): Question {
        useQuestion = allQuestions.random()

        for (i in question) {
            if (useQuestion.equals(i)) {
                return pickRandomQuestionAndShuffle()
            }
        }
        question.add(useQuestion)
        return shuffleCurrentQuestion(useQuestion)
    }
}
