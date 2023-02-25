package com.composedemo

import android.app.Activity
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShareQRCode(activity: Activity) {
//    LinearLayoutQrCode(modifier = Modifier.constrainAs(llq) {
////        top.linkTo(parent.top)
////        start.linkTo(parent.start)
////        end.linkTo(parent.end)
////        bottom.linkTo(parent.bottom)
//    })
//
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LinearLayoutQrCode(modifier = Modifier)
        Spacer(modifier =Modifier.padding(bottom = 45.dp))
        BottomLayout(modifier = Modifier, context = activity)
    }
}

@Preview
@Composable
fun Preview(){
    val context= LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LinearLayoutQrCode(modifier = Modifier)
        Spacer(modifier =Modifier.padding(bottom = 45.dp))
        BottomLayout(modifier = Modifier, context = context as Activity)
    }
}


@Composable
fun LinearLayoutQrCode(modifier: Modifier) {
    Column(modifier = modifier
        .background(colorResource(id = R.color.white))
        .size(270.dp, 370.dp)) {
        Image(
            painter = painterResource(id = R.drawable.query_ic),
            contentDescription = "qr code",
            modifier = Modifier
                .size(53.dp, 53.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 13.dp)
        )
        Text(
            text = "邀请你加入",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(top = 10.dp),
            color = colorResource(id = R.color.teal_200),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "水电费-试一试1-施工队", color = colorResource(id = R.color.purple_200),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp)
        )

        Image(painter    = painterResource(id = R.drawable.query_ic), contentDescription = "",modifier = Modifier
            .size(200.dp, 200.dp)
            .align(alignment = Alignment.CenterHorizontally)
            .padding(top = 45.dp))
        Text(text = "扫码加入", color = colorResource(id = R.color.teal_200), fontSize = 14.sp, textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp))
    }
}

@Composable
fun BottomLayout(modifier: Modifier, context: Activity) {
    Column(
        modifier = modifier
            .height(167.dp)
            .background(colorResource(id = R.color.white_f5), shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 25.dp, 8.dp, 0.dp)

        ) {
            ImageButton(id = R.drawable.query_ic, text = "钉钉", modifier = Modifier.weight(1f))
            ImageButton(id = R.drawable.query_ic, text = "QQ", modifier = Modifier.weight(1f))
            ImageButton(id = R.drawable.query_ic, text = "微信好友", modifier = Modifier.weight(1f))
            ImageButton(id = R.drawable.query_ic, text = "朋友圈", modifier = Modifier.weight(1f))
            ImageButton(id = R.drawable.query_ic, text = "复制链接", modifier = Modifier.weight(1f))
            ImageButton(id = R.drawable.query_ic, text = "保存图片", modifier = Modifier.weight(1f))
        }
        //底部取消按钮
        Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {
            Button(
                onClick = {
                    Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show()
                    context.finish()
                }, modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(0.dp)
            ) {
                Text(text = "取消", color = Color.Black, fontSize = 16.sp, textAlign = TextAlign.Center, modifier =Modifier.fillMaxWidth())
            }
        }
    }

}


@Composable
fun ImageButton(@DrawableRes id: Int, text: String, modifier: Modifier) {
    Column(modifier = modifier.width(45.dp)) {
        Image(
            painter = painterResource(id = id), contentDescription = text, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            alignment = Alignment.Center
        )
        Text(text = text, fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}
