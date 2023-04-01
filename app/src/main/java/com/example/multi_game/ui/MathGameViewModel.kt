package com.example.multi_game.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.multi_game.data.SCORE_INCREASE
import com.example.multi_game.data.allMathQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MathGameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MathGameUniState())
    val uiState: StateFlow<MathGameUniState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set


    private var question: MutableSet<MathQuestion> = mutableSetOf()
    private lateinit var useQuestion: MathQuestion

    init {
        resetGame()
    }

    fun resetGame() {
        question.clear()
        _uiState.value = MathGameUniState(useQuestion = pickRandomQuestionAndShuffle())
    }

    fun updateUserGuess(guessAns: String){
        userGuess = guessAns
    }
    fun checkUserAnswer() {
        if (userGuess.equals(useQuestion.answer)) {
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        }else {
            _uiState.update { currentState ->
                currentState.copy(isGuessWrong = true)}
        }
        updateUserGuess("")
    }


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
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessWrong = false,
                    useQuestion = pickRandomQuestionAndShuffle(),
                    numQue = currentState.numQue.inc(),
                    score = updatedScore ,
                    currentCount = currentState.currentCount.inc()
                )
            }
        }
    }

    private fun pickRandomQuestionAndShuffle(): MathQuestion {
        useQuestion = allMathQuestion.random()

        for (i in question) {
            if (useQuestion.equals(i)) {
                return pickRandomQuestionAndShuffle()
            }
        }
        question.add(useQuestion)
        return useQuestion
    }
}