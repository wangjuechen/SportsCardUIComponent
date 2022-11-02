package com.jc.android.atomicassignment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jc.android.sportscard.forhostapp.SportsCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportsCard.getInstance().showCard()
        }
    }
}