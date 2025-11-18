package cz.quanti.spacex.features.rockets.presentation.rocketList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cz.quanti.spacex.core.presentation.formatDate
import cz.quanti.spacex.features.rockets.domain.model.Rocket
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import quanti_space_x.composeapp.generated.resources.Res
import quanti_space_x.composeapp.generated.resources.description_rocket_detail
import quanti_space_x.composeapp.generated.resources.description_rocket_icon
import quanti_space_x.composeapp.generated.resources.first_flight_format
import quanti_space_x.composeapp.generated.resources.rocket

@Composable
fun RocketList(
    rockets: List<Rocket>,
    onRocketClick: (Rocket) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier.padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        itemsIndexed(
            items = rockets,
            key = { _, record -> record.id }
        ) { index, rocket ->
            RocketListItem(
                rocket = rocket,
                modifier = Modifier.widthIn(700.dp).fillMaxWidth().padding(horizontal = 20.dp),
                onClick = { onRocketClick(it) },
                isFirst = index == 0,
                isLast = index == rockets.lastIndex,
            )
        }
    }
}

@Composable
fun RocketListItem(
    rocket: Rocket,
    isFirst: Boolean,
    isLast: Boolean,
    onClick: (Rocket) -> Unit,
    modifier: Modifier = Modifier,
) {
    val roundedCorner: Dp = 16.dp

    Surface(
        shape = RoundedCornerShape(
            topStart = if (isFirst) roundedCorner else 0.dp,
            topEnd = if (isFirst) roundedCorner else 0.dp,
            bottomStart = if (isLast) roundedCorner else 0.dp,
            bottomEnd = if (isLast) roundedCorner else 0.dp,
        ),
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(rocket) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(Res.drawable.rocket),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(Res.string.description_rocket_icon),
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = rocket.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = stringResource(
                        Res.string.first_flight_format,
                        formatDate(rocket.firstFlight)
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(Res.string.description_rocket_detail),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}