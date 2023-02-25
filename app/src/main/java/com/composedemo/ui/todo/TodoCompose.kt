package com.composedemo.ui.todo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Airplay
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composedemo.R
import com.composedemo.ui.theme.PurpleGrey80
import com.composedemo.ui.theme.splashBackground
@Preview
@Composable
fun Preview() {
    Splash(10.dp, 10.dp)
}

@Composable
fun Splash(offsetState: Dp, alphaState: Dp) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = splashBackground), contentAlignment = Alignment.CenterStart
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = rememberVectorPainter(Icons.Filled.Airplay), contentDescription = "", modifier = Modifier.size(50.dp))
            Text(text = stringResource(id = R.string.app_name), modifier = Modifier
                .offset(y = offsetState)
                .alpha(alpha = alphaState.value),
            color= PurpleGrey80,
            fontWeight = FontWeight.Bold,
                maxLines = 1,
            style = MaterialTheme.typography.bodyMedium)
        }
    }
}