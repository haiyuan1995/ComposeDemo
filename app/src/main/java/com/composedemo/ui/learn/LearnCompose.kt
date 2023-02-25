package com.composedemo.ui.learn

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composedemo.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow


@Preview
@Composable
fun Preview() {
    Column() {
        InputText()
        ConversationAction(MsgData.messages)
    }
}

@Composable
fun ConversationAction(messages: List<MessageData>) {

    LazyColumn() {
        items(messages) {
            key(it.msg) {
                MessageCard(messageData = it)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun InputText() {
    var text by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    //当文本发起变化的时候，启动flow获取文本变化1秒内最后的值
    LaunchedEffect(key1 = text) {
        flow {
            emit(text)
        }.debounce(1000).collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            Log.e("TAG", "InputText: $text")
        }
    }

    TextField(value = text, onValueChange = {
        text = it
    }, label = { Text(text = "InputText") },
        trailingIcon = {
            ClickableText(text = AnnotatedString("x"), onClick = {
                text = ""
            })
        }
    )
}

@Composable
fun MessageCard(messageData: MessageData) {
    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor: Color by animateColorAsState(
        targetValue = if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    )

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
            }

    ) {
        Image(
            painter = painterResource(id = R.drawable.query_ic), contentDescription = "",
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .padding(4.dp)
                .border(width = 1.dp, brush = Brush.linearGradient(listOf(Color.Blue, Color.Green, Color.Red), start = Offset.Zero, end = Offset.Infinite), shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column() {
            Text(
                text = messageData.title,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color = surfaceColor,
                shape = MaterialTheme.shapes.small,
                shadowElevation = 1.dp,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(4.dp),
                    text = messageData.msg,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }

        }
    }
}

data class MessageData(val title: String, val msg: String)
object MsgData {
    private const val author = "Jetpack Compose 博物馆"
    val messages = listOf(
        MessageData(author, "我们开始更新啦"),
        MessageData(author, "为了给广大的读者一个更好的体验，从今天起，我们公众号决定陆续发一些其他作者的高质量文章"),
        MessageData(author, "每逢佳节倍思亲，从今天起，参加我们公众号活动的同学可以获得精美礼品一份！！"),
        MessageData(author, "荣华梦一场，功名纸半张，是非海波千丈，马蹄踏碎禁街霜，听几度头鸡唱"),
        MessageData(author, "唤归来，西湖山上野猿哀。二十年多少风流怪，花落花开。望云霄拜将台，袖星斗安邦策，破烟月迷魂寨。酸斋笑我，我笑酸斋"),
        MessageData(author, "伤心尽处露笑颜，醉里孤单写狂欢。两路殊途情何奈，三千弱水忧忘川。花开彼岸朦胧色，月过长空爽朗天。青鸟思飞无侧羽，重山万水亦徒然"),
        MessageData(author, "又到绿杨曾折处，不语垂鞭，踏遍清秋路。衰草连天无意绪，雁声远向萧关去。恨天涯行役苦，只恨西风，吹梦成今古。明日客程还几许，沾衣况是新寒雨"),
        MessageData(author, "莫笑农家腊酒浑，丰年留客足鸡豚。山重水复疑无路，柳暗花明又一村。箫鼓追随春社近，衣冠简朴古风存。从今若许闲乘月，拄杖无时夜叩门"),
        MessageData(author, "我们开始更新啦"),
        MessageData(author, "为了给广大的读者一个更好的体验，从今天起，我们公众号决定陆续发一些其他作者的高质量文章"),
        MessageData(author, "每逢佳节倍思亲，从今天起，参加我们公众号活动的同学可以获得精美礼品一份！！"),
        MessageData(author, "荣华梦一场，功名纸半张，是非海波千丈，马蹄踏碎禁街霜，听几度头鸡唱"),
        MessageData(author, "唤归来，西湖山上野猿哀。二十年多少风流怪，花落花开。望云霄拜将台，袖星斗安邦策，破烟月迷魂寨。酸斋笑我，我笑酸斋"),
        MessageData(author, "伤心尽处露笑颜，醉里孤单写狂欢。两路殊途情何奈，三千弱水忧忘川。花开彼岸朦胧色，月过长空爽朗天。青鸟思飞无侧羽，重山万水亦徒然"),
        MessageData(author, "又到绿杨曾折处，不语垂鞭，踏遍清秋路。衰草连天无意绪，雁声远向萧关去。恨天涯行役苦，只恨西风，吹梦成今古。明日客程还几许，沾衣况是新寒雨"),
        MessageData(author, "莫笑农家腊酒浑，丰年留客足鸡豚。山重水复疑无路，柳暗花明又一村。箫鼓追随春社近，衣冠简朴古风存。从今若许闲乘月，拄杖无时夜叩门")
    )
}