package com.example.multi_game.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

@Composable
fun EditNumberField(
    value: String,
    onValueChanged: (String) -> Unit,

    ) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(
            stringResource(com.example.multi_game.R.string.your_guess),
            fontFamily = Comfortaa,
            fontSize = 16.sp) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus()}
        ),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
    )
}

@Composable
fun NumberGuessingGameScreen(navController: NavController) {
    var amountInput by remember { mutableStateOf("") }
    val input = amountInput.toIntOrNull() ?: 0
    var count by remember { mutableStateOf(0) }
    var randomNum by remember { mutableStateOf(Random.nextInt(1, 1000)) }
    var ans by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(36.dp),

        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        Text(
            text = stringResource(com.example.multi_game.R.string.title),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 27.sp,
            fontFamily = Comfortaa
        )

        Spacer(Modifier.height(60.dp))

        Text(
            text = stringResource(com.example.multi_game.R.string.click_here),
            fontFamily = Comfortaa,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 16.sp,
        )

        Button(onClick = {
            randomNum = Random.nextInt(1, 1000)
        },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF968A)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(stringResource(com.example.multi_game.R.string.random),
                color = Color(0xFF4B3426),
                fontFamily = Comfortaa,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }

        Spacer(Modifier.height(40.dp))

        EditNumberField(
            value = amountInput,
            onValueChanged = { amountInput = it }
        )

        val result = NumberGuessingGame(amountInput = input, num = randomNum, count = count )

        Button(onClick = {
            count += 1
            ans = result.first },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF925946)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(stringResource(com.example.multi_game.R.string.submit),
                color = Color(0xFFF0D0C1),
                fontFamily = Comfortaa,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        count = result.second

        Spacer(Modifier.height(12.dp))
        Divider(color = Color(0xFFF4D4AE), modifier = Modifier.padding(horizontal = 80.dp))
        Spacer(Modifier.height(12.dp))

        Text(
            text = stringResource(com.example.multi_game.R.string.hint, ans),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Comfortaa,
            color = Color(0xFFFDAFAB),
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(12.dp))

        Button(onClick = {
            randomNum = Random.nextInt(1, 1000)
            count = 0
            amountInput = ""
            ans = "" },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF484569)),
            shape = RoundedCornerShape(20.dp),
        ) {
            Text(stringResource(com.example.multi_game.R.string.play_again),
                color = Color(0xFFD7BCE4),
                fontFamily = Comfortaa,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }
        Spacer(Modifier.height(30.dp))
    }
}

fun NumberGuessingGame(amountInput: Int, num: Int, count: Int): Pair<String, Int> {
    val random = num
    val input = amountInput
    val countNum = count
    var text = ""

    if (input == random) {
        text = "Congrats you did it!, Count before you win $countNum"

    }else if(input != 0) {
        if (input > random) {
            text = "Wrong, your number is too high!"
        }else if (input < random) {
            text = "Wrong, your number is too low!"
        }
    }
    return Pair(text, countNum)
}