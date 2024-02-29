package dev.moura.test.challenge.ctw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import dev.moura.test.challenge.ctw.ui.HeadLinesApp
import dev.moura.test.challenge.ctw.ui.theme.CTWTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CTWTheme {
                Surface {
                    HeadLinesApp()
                }
            }
        }
    }
}
