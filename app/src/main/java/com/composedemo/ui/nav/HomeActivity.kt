package com.composedemo.ui.nav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
               MainNavHost()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost() {
//    val navController = rememberAnimatedNavController()
//    AnimatedNavHost(navController = navController, startDestination = RouterConfig.ROUTE_PAGE_ONE, enterTransition = {
//        slideInHorizontally (animationSpec = tween(1000),initialOffsetX = { -it } ) }, exitTransition = {
//        slideOutHorizontally()
//    }) {
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = RouterConfig.ROUTE_PAGE_ONE) {
        composable(RouterConfig.ROUTE_PAGE_ONE) {
            PageOne(navController)
        }
        composable("${RouterConfig.ROUTE_PAGE_TOW}/{${ParamsConfig.PARAMS_NAME}}/{${ParamsConfig.PARAMS_AGE}}",
            arguments = listOf(
                navArgument(ParamsConfig.PARAMS_NAME){},
                navArgument(ParamsConfig.PARAMS_AGE){type= NavType.IntType}
            )) {
                    val argument= requireNotNull(it.arguments)
                    val name = argument.getString(ParamsConfig.PARAMS_NAME)
                    val age= argument.getInt(ParamsConfig.PARAMS_AGE)

            PageTow(navController,name, age)
        }
    }
}