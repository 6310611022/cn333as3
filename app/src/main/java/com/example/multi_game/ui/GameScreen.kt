package com.example.multi_game.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multi_game.R
import com.example.multi_game.data.allQuestions
import com.example.multi_game.ui.Question
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.multi_game.ui.GameViewModel
import com.example.multi_game.ui.theme.Montserrat

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel(),
    navController: NavController
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GameStatus1(
            questionCount = gameUiState.numQue,
            score = gameUiState.score
        )
        GameLayout(
            currentQuestion = gameUiState.useQuestion,
        )
        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = { gameViewModel.resetGame() }
            )
        }
    }
}

@Composable
fun GameStatus1(questionCount: Int, score: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .size(48.dp)
    ) {
        Text(
            text = stringResource(R.string.num_que, questionCount),
            fontSize = 18.sp,
            fontFamily = Montserrat
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(R.string.score, score),
            fontSize = 18.sp,
            fontFamily = Montserrat
        )
    }
}

@Composable
fun GameLayout(
    modifier: Modifier = Modifier,
    currentQuestion: Question,
    gameViewModel: GameViewModel = viewModel(),
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = currentQuestion.question,
            fontSize = 24.sp,
            fontFamily = Montserrat,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(215.dp)
        )
    }
    Spacer(modifier = modifier.height(10.dp))

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Button(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(45.dp)
                .padding(start = 8.dp),
            onClick = { gameViewModel.checkUserAnswer(currentQuestion.choice[0]) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF912728)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = currentQuestion.choice[0],
                color = Color(0xFFCE8A3D),
                fontSize = 18.sp,
                fontFamily = Montserrat)
        }
        Button(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(45.dp)
                .padding(start = 8.dp),
            onClick = { gameViewModel.checkUserAnswer(currentQuestion.choice[1]) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF567633)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = currentQuestion.choice[1],
                color = Color(0xFFCCAF96),
                fontSize = 18.sp,
                fontFamily = Montserrat)
        }
        Button(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(45.dp)
                .padding(start = 8.dp),
            onClick = { gameViewModel.checkUserAnswer(currentQuestion.choice[2]) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFCE8A3D)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = currentQuestion.choice[2],
                color = Color(0xFF3F2F26),
                fontSize = 18.sp,
                fontFamily = Montserrat)
        }
        Button(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(45.dp)
                .padding(start = 8.dp),
            onClick = { gameViewModel.checkUserAnswer(currentQuestion.choice[3]) },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3F2F26)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(text = currentQuestion.choice[3],
                color = Color(0xFFCCAF96),
                fontSize = 18.sp,
                fontFamily = Montserrat)
        }
    }
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
        title = { Text(stringResource(R.string.congratulations),
            fontSize = 18.sp,
            fontFamily = Montserrat) },
        text = { Text(stringResource(R.string.youScored, score),
            fontSize = 16.sp,
            fontFamily = Montserrat)},
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit),
                    fontSize = 16.sp,
                    fontFamily = Montserrat)
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.play_again1),
                    fontSize = 16.sp,
                    fontFamily = Montserrat)
            }
        }
    )
}
