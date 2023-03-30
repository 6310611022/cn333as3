package com.example.multi_game.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.multi_game.R
import com.example.multi_game.ui.theme.MultiGameTheme

@Composable
fun MathGameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: MathGameViewModel = viewModel(),
    navController: NavController
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GameStatus(
            questionCount = gameUiState.numQue,
            score = gameUiState.score
        )
        GameLayout(
            currentQuestion = gameUiState.useQuestion,
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            userGuess = gameViewModel.userGuess,
            onKeyboardDone = { gameViewModel.checkUserAnswer() },
            isGuessWrong = gameUiState.isGuessWrong
        )

        Button(modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(start = 8.dp),
            onClick = {gameViewModel.checkUserAnswer()}
        ){Text(stringResource(R.string.submit))}

        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = { gameViewModel.resetGame() }
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
        }
    }
}

@Composable
fun GameStatus(questionCount: Int, score: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .size(48.dp)
    ) {
        Text(
            text = stringResource(R.string.num_que, questionCount),
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(R.string.score, score),
            fontSize = 18.sp,
        )
    }
}

@Composable
fun GameLayout(
    modifier: Modifier = Modifier,
    currentQuestion: MathQuestion,
    gameViewModel: MathGameViewModel = viewModel(),
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    isGuessWrong: Boolean
) {
    Column(
        modifier = Modifier
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = currentQuestion.question,
            fontSize = 24.sp,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(30.dp)
        )
    }
    Spacer(modifier = modifier.height(60.dp))

    OutlinedTextField(
        value = userGuess,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onUserGuessChanged,
        label = {
            if (isGuessWrong) {
                Text(stringResource(R.string.wrong_guess))
            } else {
                Text(stringResource(R.string.enter_your_answer))
            }
        },
        isError = isGuessWrong,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardDone() }
        ),
    )
}

@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {},
        title = { Text(
            stringResource(R.string.congratulations),
            fontSize = 18.sp,) },
        text = { Text(
            stringResource(R.string.youScored, score),
            fontSize = 16.sp,)
        },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit),
                    fontSize = 16.sp,)
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.play_again),
                    fontSize = 16.sp,)
            }
        }
    )
}
