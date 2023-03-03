package com.composedemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composedemo.ui.theme.ComposeDemoTheme

//compose 和 原生view 互相嵌套
open class ComposeAndViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_compose_and_view)
        findViewById<ComposeView>(R.id.composeView).setContent {
            PreviewFun()
        }
    }
}

@Preview
@Composable
fun PreviewFun() {
    ComposeDemoTheme {
        Column {
            ImageButton("Android")
            ClickCountButton()
        }

    }
}


@Composable
fun ImageButton(text: String) {
    val context = LocalContext.current
    val pressed = remember { mutableStateOf(false) }
    val animateFontSize = animateFloatAsState(targetValue = if (pressed.value) 16f else 12f)
    val sizeAnimate = animateSizeAsState(targetValue = if (pressed.value) Size(200F, 48F) else Size(100f, 48f))
    Surface(
        modifier = Modifier
            .background(color = Color.White)
            .padding(8.dp)
            .clip(CircleShape),
        shadowElevation = 1.dp,
    ) {
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .background(color = colorResource(id = R.color.teal_200))
            .padding(8.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        Toast
                            .makeText(context, "长按", Toast.LENGTH_SHORT)
                            .show()
                        pressed.value = !pressed.value
                    },
                    onPress = {
                        pressed.value = !pressed.value
                    },
                    onDoubleTap = {

                    },
                    onTap = {
                        Toast
                            .makeText(context, "点击", Toast.LENGTH_SHORT)
                            .show()
                    }

                )
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "contentDescription",
                modifier = Modifier
                    .size(animateDpAsState(targetValue = if (pressed.value) 48.dp else 32.dp).value)
                    .clip(CircleShape),
            )
            Box(
                contentAlignment = Alignment.CenterStart, modifier = Modifier
                    .defaultMinSize()
                    .fillMaxWidth()
            ) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(sizeAnimate.value.width.dp, sizeAnimate.value.height.dp),
                    color = colorResource(id = R.color.white), fontSize = animateFontSize.value.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Composable
fun ClickCountButton() {
    val count = remember { mutableStateOf(0) }
    val press = rememberSaveable { mutableStateOf(false) }

    Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "点击计算",
        modifier = Modifier
            .size(50.dp, 50.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        press.value = !press.value
                        if (tryAwaitRelease()) {
                            //按压被取消
                            press.value = !press.value
                        } else {
                            //按压被打断
                            press.value = !press.value
                        }
                    },
                )
            })
//文字显示动画
    AnimatedVisibility(
        visible = press.value,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        ) + fadeOut()
    ) {
        Text(
            text = "+1",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier.width(150.dp)
        )
    }
}


//状态提升例子
@Preview(showBackground = true)
@Composable
fun TextPreview() {
    val textState = remember { mutableStateOf("") }
    TextContent(text = textState.value, onTextChange = {
        textState.value = it
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextContent(text: String, onTextChange: (change: String) -> Unit) {
    Box(modifier = Modifier.wrapContentSize()) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = text, modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                , color = Color.Blue,textAlign= TextAlign.Start)
            TextField(
                value = text,
                onValueChange = onTextChange,
                label = { Text(text = "请输入内容") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }

    }
}
