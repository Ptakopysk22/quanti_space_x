package cz.quanti.spacex.features.rockets.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cz.quanti.spacex.core.presentation.ProgressIndicator
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.stringResource
import quanti_space_x.composeapp.generated.resources.Res
import quanti_space_x.composeapp.generated.resources.error_loading_image
import quanti_space_x.composeapp.generated.resources.rocket_image

@Composable
fun RocketImage(url: String, modifier: Modifier = Modifier) {
    KamelImage(
        resource = {
            asyncPainterResource(url)
        },
        contentDescription = stringResource(Res.string.rocket_image),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Crop,
        onLoading = { progress ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                ProgressIndicator()
            }
        },
        onFailure = { error ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.error_loading_image),
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }
    )
}