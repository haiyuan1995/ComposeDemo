package com.composedemo.sample

import android.graphics.Color
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.composedemo.R
import java.util.regex.Pattern

@Composable
fun MviSearchScreen(mviViewModel: MviViewModel) {
    val viewState by mviViewModel.viewState.collectAsState()
    val isShowToast by remember {
       derivedStateOf {viewState.result.isNotEmpty()}
    }
    val context= LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .background(androidx.compose.ui.graphics.Color.White)) {
        Box(modifier = Modifier.weight(1f)){
            SearchBarScreen { input ->
                mviViewModel.onAction(UiAction.SearchInput(input))
            }
        }
        Box(modifier = Modifier.weight(1f)){
            SearchResultScreen(viewState.result,viewState.isLoading,viewState.key)
        }
        DisposableEffect(key1 = isShowToast, effect ={
            if (isShowToast){
                Toast.makeText(context, viewState.result.toString(), Toast.LENGTH_SHORT).show()
            }
            onDispose {

            }
        } )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(onConfirm: (input: String) -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(150.dp))


        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.avatar),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(20.dp))

        var textState by remember { mutableStateOf("") }

        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            placeholder = {
                Text(text = "Search in wanandroid.com")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    onConfirm(textState)
                }
            ),
            leadingIcon = {
                Image(imageVector = Icons.Default.Search, contentDescription = null)
            },
        )

        Spacer(modifier = Modifier.weight(1f))

    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultScreen(
    result: List<ArticleBean> = emptyList(),
    isLoading: Boolean = false,
    input: String = ""
) {

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {

        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Result of searching [$input]:",
                fontSize = 20.sp
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp, top = 5.dp)
                    .height(0.5.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                result.forEach { articleBean ->
                    ResultItem(articleBean.title ?: "", articleBean.desc ?: "", input)
                }
            }

        }

    }
}


@Composable
fun ResultItem(title: String, desc: String = "", keyWorlds: String) {

    Column(Modifier.padding(top = 5.dp, bottom = 5.dp)) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth(),
            factory = {
                TextView(it).apply {
                    textSize = 16f
                    setTextColor(Color.BLUE)
                    text = highlight(title, keyWorlds)
                }
            }) {

        }

        AndroidView(
            modifier = Modifier
                .fillMaxWidth(),
            factory = {
                TextView(it).apply {
                    textSize = 12f
                    text = Html.fromHtml(desc)
                }
            }) {

        }

//        Text(
//            text = desc,
//            fontSize = 12.sp,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 2.dp)
//
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResultItem() {
    Column {

        ResultItem("I Love Java", "Java is so cool", keyWorlds = "java")
        ResultItem("I Love JavaScript", keyWorlds = "java")
        ResultItem("I Love JavaServerPage", keyWorlds = "java")
    }
}

fun highlight(text: String, _target: String): Spannable {
    val prefix = "<em class='highlight'>"
    val postfix = "</em>"
    val hasEmTag = text.indexOf(prefix) >= 0

    val toBeMatched =
        text.replace(if (hasEmTag) "$prefix$_target$postfix" else _target, _target, true)

    val spannable =
        SpannableStringBuilder(
            if (hasEmTag) text.replace(
                "$prefix$_target$postfix",
                text.substring(text.indexOf(prefix) + prefix.length until text.indexOf(postfix)),
                true
            ) else text
        )

    val m = Pattern.compile(_target).matcher(toBeMatched)
    while (m.find()) {
        val span = ForegroundColorSpan(Color.RED)
        spannable.setSpan(span, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    spannable.setSpan(UnderlineSpan(),0, toBeMatched.length, 0)
    return spannable
}