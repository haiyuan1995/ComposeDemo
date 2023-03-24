package com.composedemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.composedemo.sample.MviActivity
import com.composedemo.ui.LoginActivity
import com.composedemo.ui.nav.HomeActivity
import com.composedemo.ui.theme.ComposeDemoTheme
import com.composedemo.ui.theme.Purple80
import com.composedemo.ui.theme.PurpleGrey40
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.padding(50.dp)) {
                        Button(modifier = Modifier.wrapContentSize(),shape = RoundedCornerShape(5.dp),
                            onClick = { startActivity(Intent(this@MainActivity, ShareQRCodeActivity::class.java)) }) {
                            Text(text = "Share")
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Button(modifier = Modifier.wrapContentSize(), shape = RoundedCornerShape(5.dp),
                            onClick = { startActivity(Intent(this@MainActivity, LoginActivity::class.java)) }) {
                            Text(text = "Login")
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Button(modifier = Modifier.wrapContentSize(), shape = RoundedCornerShape(5.dp),
                            onClick = { startActivity(Intent(this@MainActivity, ComposeAndViewActivity::class.java)) }) {
                            Text(text = "compose and view")
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Button(modifier = Modifier.wrapContentSize(), shape = RoundedCornerShape(5.dp),
                            onClick = { startActivity(Intent(this@MainActivity, HomeActivity::class.java)) }) {
                            Text(text = "compose nav")
                        }

                        Spacer(modifier = Modifier.size(10.dp))
                        Button(modifier = Modifier.wrapContentSize(), shape = RoundedCornerShape(5.dp),
                            onClick = { startActivity(Intent(this@MainActivity, MviActivity::class.java)) }) {
                            Text(text = "compose mvi")
                        }
                    }
                }
            }

        }

        lifecycleScope.launch {
            Log.e(TAG, "context"+(coroutineContext[Job]))
            val f = flow {
                for (i in 1..30) {
                    delay(500)
                    Log.e(TAG, "emit $i")
                    emit(i)
                }
            }.flowOn(Dispatchers.IO).filter {
                it % 2 == 0
            }.map {
                "2的余数转为其本身的3倍${it*3.0}"
            }
            withTimeoutOrNull(16000) {
                f.collect {
                    delay(500)
                    Log.e(TAG, "consume $it")
                }
            }
            Log.e(TAG, "cancel")
        }
    }
}


data class Card(val title: String, val content: String)

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun MessageCard(card: Card) {
    var isExpanded by remember { mutableStateOf(false) } // 创建一个能够检测卡片是否被展开的变量
// 创建一个能够根据 isExpanded 变量值而改变颜色的变量
    val surfaceColor by animateColorAsState(
        targetValue = if (isExpanded) Color(0xFFCCCCCC) else MaterialTheme.colorScheme.surface
    )
    Surface(shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(all = 8.dp)
            .clickable {// 添加一个新的 Modifier 扩展方法，可以让元素具有点击的效果
                isExpanded = !isExpanded
            },
        color = surfaceColor
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.query_ic),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Purple80, CircleShape)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Column {
                Text(text = card.title, color = PurpleGrey40, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = card.content, style = MaterialTheme.typography.bodyMedium,
                    // 修改 maxLines 参数，在默认情况下，只显示一行文本内容
                    maxLines = if (isExpanded)Int.MAX_VALUE else 1,
                    // Composable 大小的动画效果
                    modifier = Modifier.animateContentSize())
            }
        }
    }
}

data class MessageData(val title: String, val msg: String)

@Composable
fun Conversation(messages: List<MessageData>) {
    LazyColumn(contentPadding = PaddingValues(0.dp,8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(messages) {
            MessageCard(card = Card(it.title, it.msg))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeDemoTheme {
        Column {
            MessageCard(card = Card("珠海天气", "今天晴，27° "))
            Conversation(messages = MsgData.messages)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavPreView() {
    BottomNav(selected = 1)
}
@Composable
fun BottomNav(selected:Int) {
    Row {
        BottomNavItem(if (selected==0) R.drawable.query_ic else R.drawable.query_ic,"交友", modifier = Modifier.weight(1f))
        BottomNavItem(if (selected==1) R.drawable.query_ic else R.drawable.query_ic,"动态",modifier = Modifier.weight(1f))
        BottomNavItem(if (selected==2) R.drawable.query_ic else R.drawable.query_ic,"消息",modifier = Modifier.weight(1f))
        BottomNavItem(if (selected==3) R.drawable.query_ic else R.drawable.query_ic,"我的",modifier = Modifier.weight(1f))
    }
}

@Composable
fun BottomNavItem(@DrawableRes iconId:Int,title:String,modifier: Modifier){
    Column(horizontalAlignment = CenterHorizontally, modifier = modifier
        .padding(8.dp)
        .background(Color.Yellow)) {
        Icon(painter = painterResource(id = iconId) ,
            contentDescription =title,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(24.dp)
        )
        Text(text = title, fontSize = 11.sp, textAlign = TextAlign.Center)
    }
}


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
        MessageData(author, "莫笑农家腊酒浑，丰年留客足鸡豚。山重水复疑无路，柳暗花明又一村。箫鼓追随春社近，衣冠简朴古风存。从今若许闲乘月，拄杖无时夜叩门") ,
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