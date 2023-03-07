package com.composedemo.ui.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun PageOne(navController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Text(text ="这是页面1")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
        //点击跳转到页面2
            navController.navigate("${RouterConfig.ROUTE_PAGE_TOW}/张三/18")
         }) {
            Text(text ="跳转到页面2",modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)
        }
    }
}