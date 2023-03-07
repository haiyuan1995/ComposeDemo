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
fun PageTow(navController: NavHostController, name: String?, age: Int) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Text(text ="这是页面2")
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "传递过来的参数$name,年龄$age")

        Button(onClick = {
            //返回到页面1
            navController.popBackStack()
        }) {
            Text(text ="返回到页面1",modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)
        }
    }
}