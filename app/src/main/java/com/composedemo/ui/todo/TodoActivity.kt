package com.composedemo.ui.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class TodoActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            
        }
    }
}

@Preview(showBackground = true)
@Composable
fun test() {
    AirQualityGradientBar(airQuality = 30)
}

@Composable
fun AirQualityGradientBar(airQuality: Int) {
    val gradientColors = listOf(
        Color.Red,
        Color.Magenta,
        Color.Blue,
        Color.Cyan,
        Color.Green,
        Color.Yellow,
        Color.Red
    )

    val gradient = Brush.horizontalGradient(
        colors = gradientColors,
        0f,
    )

    Box(modifier = Modifier
        .clip(RoundedCornerShape(0.5f))
        .background(gradient)
        .height(10.dp)
        .fillMaxWidth()

    ) {
        val position = airQuality.toFloat() / 100
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White)
                .size(10.dp)
                .clip(CircleShape)
                .align(Alignment.TopStart)
                .offset(x = (position * (LocalDensity.current.density * 10)).dp)
        )
    }
}