package com.jc.android.sportscard.forhostapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jc.android.sportscard.di.AppComponent
import com.jc.android.sportscard.di.DaggerAppComponent
import com.jc.android.sportscard.repository.ContentRepository
import com.jc.android.sportscard.repository.Sport
import com.jc.android.sportscard.ui.theme.card_background_color
import javax.inject.Inject


interface SportsCard {

    @Composable
    fun showCard()

    companion object {
        fun getInstance(): SportsCard {
            return SportsCardLibraryClientImpl()
        }
    }
}

internal class SportsCardLibraryClientImpl : SportsCard {

    init {
        DaggerAppComponent.create().inject(this)
    }

    @Inject
    lateinit var contentRepository: ContentRepository

    @Composable
    override fun showCard() {

        val content = contentRepository.sports.collectAsState(initial = emptyList())

        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
                .defaultMinSize(200.dp).wrapContentSize(unbounded = false),
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
            colors = CardDefaults.cardColors(containerColor = card_background_color),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            if (content.value.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                val sportToShow = remember { mutableStateOf(getRandomSportToShow(content)) }
                ShowContent(sportToShow.value, onRefreshClick = {
                    sportToShow.value = getRandomSportToShow(content)
                })
            }
        }
    }

    private fun getRandomSportToShow(content: State<List<Sport>>): Sport {
        val randomSportsIndex = (0 until content.value.size).random()
        return content.value[randomSportsIndex]
    }

    @Composable
    private fun ShowContent(sportToShow: Sport, onRefreshClick: () -> Unit) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                text = sportToShow.name
            )
            Text(fontSize = 14.sp, color = Color.White, text = sportToShow.description)
            Button(
                onClick = { onRefreshClick() },
                shape = RoundedCornerShape(corner = CornerSize(12.dp))
            ) {
                Text(text = "Refresh")
            }
        }
    }
}

