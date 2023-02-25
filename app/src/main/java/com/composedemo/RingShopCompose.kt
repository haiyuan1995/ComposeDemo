@file:OptIn(ExperimentalUnitApi::class, ExperimentalUnitApi::class)

package com.composedemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson


class RingShopActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ringData = Gson().fromJson(dataJson, RingProvideBean::class.java)
            val newData = mutableListOf<RingProvideBean.Data>()
            newData.addAll(ringData.data)
            newData.addAll(ringData.data)
            newData.addAll(ringData.data)
            newData.addAll(ringData.data)
            newData.addAll(ringData.data)
            Log.e(TAG, "onCreate: " + newData.size.toString())
            val ringViewModel: RingViewModel = viewModel()
            if (ringViewModel.showDialog.collectAsState().value) {
                DlgRingBuy(ringViewModel.dlgBuyData.value)
            }
            Surface(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                val testData = ringData.copy(data = newData)
                RingShopListScreen(testData)
            }

        }
    }
}

@Preview
@Composable
fun RingShopListPreview() {
    val ringData = Gson().fromJson(dataJson, RingProvideBean::class.java)
    val newData = mutableListOf<RingProvideBean.Data>()
//    repeat(20){
//        newData.addAll(ringData.data)
//    }
    Log.e(TAG, "onCreate: " + newData.size.toString())
    val ringViewModel: RingViewModel = viewModel()
    if (ringViewModel.showDialog.collectAsState().value) {
        DlgRingBuy(ringViewModel.dlgBuyData.value)
    }
    Surface(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        RingShopListScreen(ringData)
    }
}


@Composable
fun RingShopListScreen(ringData: RingProvideBean) {
    Column(content = {
        ringData.data.forEach {
            RingTypeItem(data = it)
        }
    })
}

@Composable
fun RingTypeItem(data: RingProvideBean.Data) {
    Column {
        Box(
            modifier = Modifier
                .padding(64.dp, 16.dp)
                .wrapContentSize()
                .clip(RoundedCornerShape(18.dp))
                .fillMaxWidth()
                .height(30.dp)
        ) {
            Image(painter = rememberAsyncImagePainter(data.image), "", modifier = Modifier.fillMaxSize())
            Text(modifier = Modifier.align(Alignment.Center), text = data.name, textAlign = TextAlign.Center)
        }

        LazyVerticalGrid(modifier = Modifier.wrapContentSize(), columns = GridCells.Fixed(2), content = {
            items(data.records, key = { it.id }) {
                RingDataItem(it)
            }
        }, contentPadding = PaddingValues(8.dp))
    }

}

@Composable
fun RingDataItem(
    data: RingProvideBean.Data.Record,
    viewModel: RingViewModel = viewModel()
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        modifier = Modifier
            .wrapContentSize()
            .padding(all = 8.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable(true, onClick = {
                viewModel.dlgBuyData.value = data
                viewModel.openDialog()
            })
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(64.dp)
        ) {
            Image(modifier = Modifier.size(64.dp), painter = rememberAsyncImagePainter(data.logo), contentDescription = "")
            Spacer(
                modifier = Modifier
                    .wrapContentSize()
                    .height(8.dp)
            )
            Text(modifier = Modifier.fillMaxSize(), text = data.name, textAlign = TextAlign.Center)
        }
    }
}

@Preview
@Composable
fun DlgPreview() {
    val viewModel: RingViewModel = viewModel()
    DlgRingBuy(data = viewModel.dlgBuyData.value)
}

@Composable
fun DlgRingBuy(data: RingProvideBean.Data.Record, viewModel: RingViewModel = viewModel()) {
    Dialog(onDismissRequest = {}, content = {
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.Top, modifier = Modifier.wrapContentSize()) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(16.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .wrapContentHeight(), painter = rememberAsyncImagePainter(model = data.image), contentDescription = ""
                    )
                    Image(
                        painter = rememberAsyncImagePainter(model = data.logo), contentDescription = "", modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(150.dp)
                            .align(Alignment.Center)
                    )
                }
                Row(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(16.dp)
                ) {
                    Text(text = data.name, color = Color.Black, fontSize = TextUnit(12f, TextUnitType.Sp))
                }
                Image(
                    painter = painterResource(id = R.drawable.close_ic), "", modifier = Modifier
                        .size(32.dp)
                        .clickable(enabled = true, onClick = {
                            viewModel.dismissDialog()
                        })
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
        }
    })
}


const val dataJson = "{\n" +
        "  \"code\": 1,\n" +
        "  \"msg\": null,\n" +
        "  \"data\": [\n" +
        "    {\n" +
        "      \"id\": 1,\n" +
        "      \"name\": \"珍藏系列\",\n" +
        "      \"image\": \"https://quanyubucket-1307450815.cos.ap-guangzhou.myqcloud.com/gift/image/2f0fe8ad-4eea-4761-92c4-feef2ed823ba.png\",\n" +
        "      \"records\": [\n" +
        "        {\n" +
        "          \"id\": 2,\n" +
        "          \"name\": \"珍藏1号\",\n" +
        "          \"type\": 1,\n" +
        "          \"typeName\": \"珍藏系列\",\n" +
        "          \"typeShortName\": \"珍藏\",\n" +
        "          \"content\": \"珍藏戒指1号介绍\",\n" +
        "          \"price1\": 25.00,\n" +
        "          \"price2\": 15.00,\n" +
        "          \"valDesc\": \"配戴同款戒指时亲密度增长速率+5%\",\n" +
        "          \"validity\": 3,\n" +
        "          \"logo\": \"https://quanyubucket-1307450815.cos.ap-guangzhou.myqcloud.com/gift/image/7c70b25b-e322-4002-a1db-fb5883db4d64.png\",\n" +
        "          \"image\": \"https://quanyubucket-1307450815.cos.ap-guangzhou.myqcloud.com/gift/image/694b690e-8547-438b-81d6-eefa880dd792.png\",\n" +
        "          \"seq\": 3\n" +
        "        }\n" +
        "      ]\n" +
        "    },\n" +
        "    {\n" +
        "      \"id\": 2,\n" +
        "      \"name\": \"心动系列\",\n" +
        "      \"image\": \"https://quanyubucket-1307450815.cos.ap-guangzhou.myqcloud.com/gift/image/f5f5108c-1714-4466-b318-ad0d6061899e.png\",\n" +
        "      \"records\": [\n" +
        "        {\n" +
        "          \"id\": 1,\n" +
        "          \"name\": \"心动1号\",\n" +
        "          \"type\": 2,\n" +
        "          \"typeName\": \"心动系列\",\n" +
        "          \"typeShortName\": \"心动\",\n" +
        "          \"content\": \"心动戒指1号 介绍\",\n" +
        "          \"price1\": 25.00,\n" +
        "          \"price2\": 15.00,\n" +
        "          \"valDesc\": \"配戴同款戒指时亲密度增长速率+5%\",\n" +
        "          \"validity\": 3,\n" +
        "          \"logo\": \"https://quanyubucket-1307450815.cos.ap-guangzhou.myqcloud.com/gift/image/7c114d64-7031-4594-b94b-73135dfd3575.gif\",\n" +
        "          \"image\": \"https://quanyubucket-1307450815.cos.ap-guangzhou.myqcloud.com/gift/image/6afad028-8a31-4b99-a841-70a6c9966e29.gif\",\n" +
        "          \"seq\": 1\n" +
        "        },\n" +
        "        {\n" +
        "          \"id\": 3,\n" +
        "          \"name\": \"心动2号\",\n" +
        "          \"type\": 2,\n" +
        "          \"typeName\": \"心动系列\",\n" +
        "          \"typeShortName\": \"心动\",\n" +
        "          \"content\": \"心动戒指2号介绍\",\n" +
        "          \"price1\": 35.00,\n" +
        "          \"price2\": 25.00,\n" +
        "          \"valDesc\": \"配戴同款戒指时亲密度增长速率+10%\",\n" +
        "          \"validity\": 3,\n" +
        "          \"logo\": \"https://quanyubucket-1307450815.cos.ap-guangzhou.myqcloud.com/gift/image/b3a0fb4d-dc71-425b-85e0-a76bdfb09fd4.jpg\",\n" +
        "          \"image\": \"https://quanyubucket-1307450815.cos.ap-guangzhou.myqcloud.com/gift/image/81cd4f13-3713-4ae2-9d87-b47e814a4633.jpg\",\n" +
        "          \"seq\": 2\n" +
        "        }\n" +
        "      ]\n" +
        "    }\n" +
        "  ],\n" +
        "  \"count\": 2,\n" +
        "  \"num\": null\n" +
        "}"


data class RingProvideBean(
    val code: Int,
    val count: Int,
    val `data`: List<Data>,
    val msg: Any,
    val num: Any
) {
    data class Data(
        val id: Int,
        val name: String,
        val image: String,
        val records: List<Record>
    ) {
        data class Record(
            val content: String, // (string)简介
            val id: String, // (int64)ID
            val image: String, // (string)背景图
            val logo: String, // (string)图片
            val name: String, // (string)装备名称
            val price1: String, // (number)男会员价格
            val price2: String, // (number)女会员价格
            val seq: String, // (int32)序号
            val type: Int, // (int32)装备类型
            val typeName: String, // (string)装备系列
            val valDesc: String, // (string)加成效果说明
            val validity: String, // (int32)有效期
            val typeShortName: String// "(string)物品系列短名"
        )
    }
}