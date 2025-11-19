package cz.quanti.spacex.features.rockets.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import quanti_space_x.composeapp.generated.resources.Res
import quanti_space_x.composeapp.generated.resources.description_back
import quanti_space_x.composeapp.generated.resources.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketTopBar(
    title: String,
    onBackClick: () -> Unit,
    backClickText: String,
    onLaunchClick: () -> Unit,
    isLaunchVisible: Boolean,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .clickable { onBackClick() }
                    .padding(start = 0.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(Res.string.description_back),
                )
                Text(
                    text = backClickText,
                )
            }
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            if (isLaunchVisible) {
                Text(
                    text = stringResource(Res.string.launch),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { onLaunchClick() },
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 2f),
            navigationIconContentColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.secondary,
            //subtitleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
