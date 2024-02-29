package dev.moura.test.challenge.ctw.ui.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorSnackBar(
    snackBarHostState: SnackbarHostState,
    errorMessage: String?
) {
    SnackbarHost(snackBarHostState) { data ->
        CustomSnackBar(data)
    }

    LaunchedEffect(snackBarHostState) {
        snackBarHostState.showSnackbar(
            message = errorMessage ?: "",
            withDismissAction = true,
            duration = SnackbarDuration.Short
        )
    }
}

@Composable
private fun CustomSnackBar(snackbarData: SnackbarData) {
    Surface(
        shape = RoundedCornerShape(15.dp),
        shadowElevation = 8.dp,
        color = Color.Red,
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                Icons.Outlined.Info,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )

            Text(
                text = snackbarData.visuals.message,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    snackbarData.performAction()
                }
            ) {
                Icon(
                    Icons.Filled.Close,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
    }
}
