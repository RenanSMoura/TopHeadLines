package dev.moura.test.challenge.ctw.ui.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.moura.test.challenge.ctw.data.source.network.model.EMPTY
import dev.moura.test.challenge.ctw.ui.presentation.components.AsyncImageLoader
import dev.moura.test.challenge.ctw.ui.presentation.navigation.details.DETAILS_KEY
import dev.moura.test.challenge.ctw.ui.presentation.navigation.details.HeadLineDetails

@Composable
fun DetailsRoute(
    navController: NavHostController,
) {
    val detailsDestination = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<HeadLineDetails>(DETAILS_KEY)

    DetailsScreen(detailsDestination)
}

@Composable
internal fun DetailsScreen(detailsDestination: HeadLineDetails?) {
    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            AsyncImageLoader(
                data = detailsDestination?.imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = detailsDestination?.title ?: EMPTY,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.Black,
                )

                Text(
                    text = detailsDestination?.content ?: EMPTY,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
