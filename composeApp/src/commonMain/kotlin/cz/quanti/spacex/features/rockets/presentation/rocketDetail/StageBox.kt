package cz.quanti.spacex.features.rockets.presentation.rocketDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cz.quanti.spacex.features.rockets.domain.model.RocketStage
import cz.quanti.spacex.features.rockets.domain.model.RocketStageType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import quanti_space_x.composeapp.generated.resources.Res
import quanti_space_x.composeapp.generated.resources.burn
import quanti_space_x.composeapp.generated.resources.burn_time
import quanti_space_x.composeapp.generated.resources.engine
import quanti_space_x.composeapp.generated.resources.engines_format
import quanti_space_x.composeapp.generated.resources.first_stage
import quanti_space_x.composeapp.generated.resources.fuel
import quanti_space_x.composeapp.generated.resources.fuel_amount
import quanti_space_x.composeapp.generated.resources.not_reusable
import quanti_space_x.composeapp.generated.resources.reusable
import quanti_space_x.composeapp.generated.resources.second_stage
import quanti_space_x.composeapp.generated.resources.seconds_burn_time_format
import quanti_space_x.composeapp.generated.resources.tons_of_fuel_format
import quanti_space_x.composeapp.generated.resources.unknown_stage

@Composable
fun StageBox(
    stage: RocketStage,
    modifier: Modifier = Modifier
) {

    val stageType = if (stage.type == RocketStageType.FIRST) {
        stringResource(Res.string.first_stage)
    } else if (stage.type == RocketStageType.SECOND) {
        stringResource(Res.string.second_stage)
    } else {
        stringResource(Res.string.unknown_stage)
    }

    Surface(
        modifier = modifier.fillMaxWidth().padding(vertical = 6.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stageType,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            StageBoxRow(
                icon = painterResource(Res.drawable.reusable),
                description = stringResource(if (stage.reusable) Res.string.reusable else Res.string.not_reusable),
                iconDescription = stringResource(Res.string.reusable),
            )
            StageBoxRow(
                icon = painterResource(Res.drawable.engine),
                description = stringResource(Res.string.engines_format, stage.engines),
                iconDescription = stringResource(Res.string.engine),
            )
            StageBoxRow(
                icon = painterResource(Res.drawable.fuel),
                description = stringResource(
                    Res.string.tons_of_fuel_format,
                    stage.fuelCapacity.toString()
                ),
                iconDescription = stringResource(Res.string.fuel_amount),
            )
            StageBoxRow(
                icon = painterResource(Res.drawable.burn),
                description = stringResource(
                    Res.string.seconds_burn_time_format,
                    stage.burnTime.toString()
                ),
                iconDescription = stringResource(Res.string.burn_time),
            )
        }
    }
}


@Composable
fun StageBoxRow(
    icon: Painter,
    description: String,
    iconDescription: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = icon,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = iconDescription,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = description)
    }
}