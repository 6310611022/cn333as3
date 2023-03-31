package com.example.multi_game.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.multi_game.R
import com.example.multi_game.ui.theme.Comfortaa
import com.example.multi_game.ui.theme.Montserrat
import com.example.multi_game.ui.theme.MultiGameTheme

@Composable
fun MathGameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: MathGameViewModel = viewModel(),
    navController: NavController
) {
    val image = painterResource(R.drawable.mathematics)
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
            onClick = {gameViewModel.checkUserAnswer()} ,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFC8862)),
            shape = RoundedCornerShape(20.dp)
        ){Text(stringResource(R.string.submit),
            color = Color(0xFFFFEABB),
            fontSize = 18.sp,
            fontFamily = Montserrat)
        }

        Spacer(modifier = Modifier.height(50.dp))

        Image(painter = image, contentDescription = null, modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(20.dp))

        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = { gameViewModel.resetGame() }
            )
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
            fontFamily = Montserrat)
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(R.string.score, score),
            fontSize = 18.sp,
            fontFamily = Montserrat)
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
            fontFamily = Montserrat,
            fontSize = 24.sp,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(30.dp)
        )
    }
    Spacer(modifier = modifier.height(10.dp))

    TextField(
        value = userGuess,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onUserGuessChanged,
        label = {
            if (isGuessWrong) {
                Text(stringResource(R.string.wrong_guess),
                    fontFamily = Montserrat,
                    fontSize = 16.sp,
                    color = Color(0xFFB72803),)
            } else {
                Text(stringResource(R.string.enter_your_answer),
                    fontFamily = Montserrat,
                    fontSize = 16.sp,
                    color = Color(0xFF033495),)
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
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
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
            fontSize = 18.sp,
            fontFamily = Montserrat) },
        text = { Text(
            stringResource(R.string.youScored, score),
            fontSize = 16.sp,
            fontFamily = Montserrat)
        },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit),
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    color = Color(0xFF16A5A3))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.play_again),
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    color = Color(0xFFDE5B6D))
            }
        }
    )
}
