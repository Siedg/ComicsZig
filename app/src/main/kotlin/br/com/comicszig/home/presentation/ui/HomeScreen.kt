package br.com.comicszig.home.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(

) {
    Scaffold(
        content = {
            Box(modifier = Modifier.padding(it)) {
                HomeContent()
            }
        }
    )
}
