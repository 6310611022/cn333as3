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
    // Game UI state
    private val _uiState = MutableStateFlow(MathGameUniState())
    val uiState: StateFlow<MathGameUniState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    // Set of words used in the game
    private var question: MutableSet<MathQuestion> = mutableSetOf()
    private lateinit var useQuestion: MathQuestion

    init {
        resetGame()
    }

    /*
     * Re-initializes the game data to restart the game.
     */
    fun resetGame() {
        question.clear()
        _uiState.value = MathGameUniState(useQuestion = pickRandomQuestionAndShuffle())
    }

    /*
     * Update the user's guess
     */
    fun updateUserGuess(guessAns: String){
        userGuess = guessAns
    }
    /*
     * Checks if the user's guess is correct.
     * Increases the score accordingly.
     */

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
                    isGuessWrong = false,
                    useQuestion = pickRandomQuestionAndShuffle(),
                    numQue = currentState.numQue.inc(),
                    score = updatedScore ,
                    currentCount = currentState.currentCount.inc()
                )
            }
        }
    }

//    private fun shuffleCurrentQuestion(question: MathQuestion): MathQuestion {
//        val shuffleChoice = question.choice.toMutableList()
//        shuffleChoice.shuffle()
//        question.choice = shuffleChoice
//        return question
//    }

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