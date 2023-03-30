package com.example.multi_game.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.multi_game.R
import com.example.multi_game.ui.theme.Montserrat

@Composable
fun HomeScreenApp(navController: NavController) {

    val image = painterResource(R.drawable.gamepad)

    Column(
        modifier = Modifier
            .padding(36.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            textAlign = TextAlign.Center,
            fontSize = 27.sp,
            fontFamily = Montserrat
        )


        Spacer(modifier = Modifier.height(20.dp))

        Image(painter = image, contentDescription = null)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(45.dp)
                .padding(start = 8.dp),
            onClick = { navController.navigate("Game1") },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE6B5B8)),
            shape = RoundedCornerShape(20.dp),
        ) {
            Text(
                text = stringResource(R.string.Game1),
                color = Color(0xFF533637),
                fontSize = 18.sp,
                fontFamily = Montserrat
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(45.dp)
                .padding(start = 8.dp),
            onClick = {navController.navigate("Game2")},
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA7767C)),
            shape = RoundedCornerShape(20.dp),
        ) {
            Text(
                text = stringResource(R.string.Game2),
                color = Color(0xFFF0D0C1),
                fontSize = 18.sp,
                fontFamily = Montserrat
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(45.dp)
                .padding(start = 8.dp),
            onClick = {navController.navigate("Game3")},
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF48404D)),
            shape = RoundedCornerShape(20.dp),
        ) {
            Text(
                text = stringResource(R.string.Game3),
                color = Color(0xFFFAE5E2),
                fontSize = 18.sp,
                fontFamily = Montserrat
            )
        }

    }
}

