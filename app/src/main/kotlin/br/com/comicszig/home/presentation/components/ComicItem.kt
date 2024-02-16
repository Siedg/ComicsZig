package br.com.comicszig.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import br.com.comicszig.ui.theme.Dimensions
import br.com.comicszig.ui.theme.Dimensions.dimen_128dp
import br.com.comicszig.ui.theme.Dimensions.dimen_16dp
import br.com.comicszig.ui.theme.Dimensions.dimen_8dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun ComicItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    imagePath: String,
    extension: String? = null
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$imagePath.$extension".replace("http", "https"))
                .size(Size.ORIGINAL)
                .build()
        )

        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        } else {
            Image(
                modifier = Modifier
                    .size(dimen_128dp)
                    .clip(RoundedCornerShape(dimen_8dp))
                    .border(
                        width = Dimensions.dimen_1dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(Dimensions.dimen_8dp)
                    ),
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }

        Column {
            Text(
                modifier = Modifier
                    .padding(start = dimen_16dp),
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            description?.let {
                Text(
                    modifier = Modifier.padding(start = dimen_16dp),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
