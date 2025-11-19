package cz.quanti.spacex.features.rockets.presentation.flightSimulator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import coil3.compose.AsyncImage
import cz.quanti.spacex.features.rockets.domain.model.RocketState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import quanti_space_x.composeapp.generated.resources.Res
import quanti_space_x.composeapp.generated.resources.description_rocket_flying
import quanti_space_x.composeapp.generated.resources.description_rocket_on_ground

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ConstantRocketImage(
    rocketState: RocketState,
    offsetSky: Float,
    offsetGround: Float,
    offsetCorrection: Float,
    modifier: Modifier = Modifier
) {
    val image: String
    val correction: Float
    if (rocketState == RocketState.ON_GROUND) {
        image = Res.getUri("files/Rocket-Idle.svg")
        correction = 0f
    } else {
        image = Res.getUri("files/Rocket-Flying.svg")
        correction = offsetCorrection
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = image,
            contentDescription = stringResource(if (rocketState == RocketState.ON_GROUND) Res.string.description_rocket_on_ground else Res.string.description_rocket_flying),
            modifier = Modifier
                .graphicsLayer {
                    translationY =
                        (if (rocketState == RocketState.ON_GROUND) offsetGround else offsetSky) + correction
                }
        )
    }
}