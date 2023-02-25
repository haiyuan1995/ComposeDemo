package com.composedemo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composedemo.R



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LoginPage() {
    val imageBitmap = ImageBitmap.imageResource(id = R.drawable.avatar)

    val deleteIcon = rememberVectorPainter(Icons.Rounded.Delete)
    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.background(Color.White)) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    bitmap = imageBitmap, contentDescription = "", contentScale = ContentScale.FillWidth, modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(ArcImageShapes(100f))
                )
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .padding(0.dp)
                        .clip(CircleShape)
                        .background(Color(206, 236, 250, 121))
                        .size(130.dp, 130.dp)
                ) {
                    Image(
                        bitmap = imageBitmap, contentDescription = "", contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .background(color = Color(0XFF0DBEBF), shape = CircleShape)
                            .padding(3.dp)
                            .clip(CircleShape)
                            .shadow(elevation = 150.dp, clip = true)
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "ComposeUnit 登陆", fontSize = 18.sp)
                    Text(text = "更多精彩，更多体验", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            ) {
                TextField(
                    value = "   ConmposeUnit",
                    onValueChange = {},
                    shape = RoundedCornerShape(18.dp),
                    colors = textFieldColors(unfocusedIndicatorColor = Color.Transparent, focusedIndicatorColor =Color.Transparent , containerColor = Color.White),
                    modifier = Modifier.border(1.dp, Color(111, 111, 111, 66), RoundedCornerShape(18.dp))

                    , leadingIcon = {
                        Image(
                            painter = rememberVectorPainter(image = Icons.Outlined.VerifiedUser), contentDescription = "", contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                        )
                    }, trailingIcon = {
                        Image(
                            painter = deleteIcon, contentDescription = "", contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                        )
                    })
            }
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                TextField(
                    value = "   ConmposeUnit",
                    onValueChange = {},
                    shape = RoundedCornerShape(18.dp),
                    colors = textFieldColors(unfocusedIndicatorColor = Color.Transparent,focusedIndicatorColor =Color.Transparent, containerColor = Color.White),
                    modifier = Modifier.border(1.dp, Color(111, 111, 111, 66), RoundedCornerShape(18.dp))
                    , leadingIcon = {
                        Image(
                            painter = rememberVectorPainter(image = Icons.Outlined.VerifiedUser), contentDescription = "", contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                        )
                    }, trailingIcon = {
                        Image(
                            painter = deleteIcon, contentDescription = "", contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                        )
                    })
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp, vertical = 20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = true, onCheckedChange = {}, colors = CheckboxDefaults.colors(checkedColor = Color(0XFF0DBEBF)))
                        Text(text = "自动登录", fontSize = 12.sp, color = Color.Gray)
                    }
                    Text(text = "用户注册", fontSize = 12.sp, color = Color(0XFF0DBEBF), modifier = Modifier.border(1.dp, color = Color(0XFF0DBEBF), shape = LineUndFunction))
                }
            }

            Column(modifier=Modifier.fillMaxWidth().weight(1f),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "第三方登录", modifier = Modifier.padding(10.dp), fontSize = 12.sp, color = Color.Gray)
                Image(
                    bitmap = imageBitmap, contentDescription = "", contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .background(color = Color(0XFF0DBEBF), shape = CircleShape)
                        .padding(3.dp)
                        .clip(CircleShape)
                        .shadow(elevation = 150.dp, clip = true)
                )
            }
        }
    }
}

//下划线设置
@Stable
val LineUndFunction: Shape = object : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path()
        path.apply {
            moveTo(0f, size.height - 2f)
            lineTo(size.width, size.height - 2f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            path.close()
            return Outline.Generic(path)
        }
    }
}

@Stable
class ArcImageShapes(var radian: Float = 100f) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path()
        path.apply {
            moveTo(0f, 0f)
            lineTo(0f, size.height - radian)
            quadraticBezierTo(size.width / 2, size.height, size.width, size.height - radian)
            lineTo(size.width, 0f)
            path.close()
            return Outline.Generic(path)
        }
    }
}
