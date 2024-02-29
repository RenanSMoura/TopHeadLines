package dev.moura.test.challenge.ctw.ui.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.moura.test.challenge.ctw.data.source.network.model.EMPTY
import dev.moura.test.challenge.ctw.domain.model.HeadLine
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DATE_PATTERN = "dd.MM.yyyy. HH:mm:ss"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlineItem(headline: HeadLine, onClickAction: () -> Unit) {

    Card(
        modifier = Modifier.padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            onClickAction()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (headline.urlToImage.isNotEmpty()) {
                AsyncImageLoader(
                    data = headline.urlToImage,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .align(Alignment.Top)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = headline.title,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End),
                    text = headline.publishedAt.formatToPattern(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

fun Date?.formatToPattern(pattern: String = DATE_PATTERN): String {
    return try {
        val format = SimpleDateFormat(pattern, Locale.getDefault())
        format.format(this!!).toString()
    } catch (e: Exception) {
        Timber.tag("Formatting error On Date").w(e)
        EMPTY
    }
}
