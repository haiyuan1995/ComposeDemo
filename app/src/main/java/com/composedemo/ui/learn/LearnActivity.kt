package com.composedemo.ui.learn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import com.composedemo.ui.theme.ComposeDemoTheme

class LearnActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                Column {
                    InputText()
                    ConversationAction(messages =MsgData.messages)
                }

            }
        }
    }
}