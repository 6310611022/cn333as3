package com.example.multi_game

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.multi_game.R
import com.example.multi_game.ui.GameScreen
import com.example.multi_game.ui.HomeScreenApp
import com.example.multi_game.ui.MathGameScreen
import com.example.multi_game.ui.theme.NumberGuessingGameScreen

enum class MultiScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    Game1(title = R.string.Game1),
    Game2(title = R.string.Game2),
    Game3(title = R.string.Game3)
}

/**
@Composable
fun HomeScreen(){

    val navController = rememberNavController()

    NavHost(navController, startDestination = MultiScreen.Home.name) {
        composable(MultiScreen.Home.name) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Greet.name) {
            GreetingScreen("Android",
                navController = navController
            )
        }
    }
}
*/

@Composable
fun MultiGameAppBar(
    currentScreen: MultiScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        }
    )
}

@Composable
fun HomeMultiScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = MultiScreen.valueOf(
        backStackEntry?.destination?.route ?: MultiScreen.Home.name
    )

    Scaffold(
        topBar = {
            MultiGameAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MultiScreen.Home.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = MultiScreen.Home.name) {
                HomeScreenApp(navController = navController)
            }
            composable(route = MultiScreen.Game1.name) {
                NumberGuessingGameScreen(navController = navController)
            }
            composable(route = MultiScreen.Game2.name) {
                GameScreen(navController = navController)
            }
            composable(route = MultiScreen.Game3.name) {
                MathGameScreen(navController = navController)
            }
        }
    }
}