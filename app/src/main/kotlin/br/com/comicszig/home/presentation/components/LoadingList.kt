package br.com.comicszig.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import br.com.comicszig.ui.theme.Dimensions.dimen_16dp
import br.com.comicszig.ui.theme.Dimensions.dimen_8dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun LoadingList() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(dimen_16dp),
        verticalArrangement = Arrangement.spacedBy(dimen_16dp),
        content = {
            items(10) {
                LoadingCard()
            }
        }
    )
}

@Composable
private fun LoadingCard() {
    Box(
        modifier = Modifier
            .aspectRatio(328f / 120)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(dimen_8dp))
            .placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                ),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )
    )
}
