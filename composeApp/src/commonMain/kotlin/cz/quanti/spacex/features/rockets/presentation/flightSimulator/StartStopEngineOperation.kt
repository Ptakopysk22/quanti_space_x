package cz.quanti.spacex.features.rockets.presentation.flightSimulator

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import quanti_space_x.composeapp.generated.resources.Res
import quanti_space_x.composeapp.generated.resources.description_rocket_flying
import quanti_space_x.composeapp.generated.resources.description_rocket_on_ground

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StartStopEngineOperation(
    modifier: Modifier = Modifier,
    isLiftingOff: Boolean,
    offsetGround: Float,
    animationDuration: Int = 2000,
    offsetCorrection: Float,
    onOperationFinished: () -> Unit,
) {

    val alphaAnim = remember { Animatable(if (isLiftingOff) 0f else 1f) }

    LaunchedEffect(isLiftingOff) {
        val target = if (isLiftingOff) 1f else 0f

        if (alphaAnim.value == target) return@LaunchedEffect

        alphaAnim.animateTo(
            targetValue = target,
            animationSpec = tween(
                durationMillis = animationDuration,
                easing = FastOutSlowInEasing
            )
        )
        onOperationFinished()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        AsyncImage(
            model = Res.getUri("files/Rocket-Idle.svg"),
            contentDescription = stringResource(Res.string.description_rocket_on_ground),
            modifier = Modifier
                .graphicsLayer {
                    alpha = 1f - alphaAnim.value
                    translationY = offsetGround
                }
        )

        AsyncImage(
            model = Res.getUri("files/Rocket-Flying.svg"),
            contentDescription = stringResource(Res.string.description_rocket_flying),
            modifier = Modifier
                .graphicsLayer {
                    alpha = alphaAnim.value
                    translationY = offsetGround + offsetCorrection
                }
        )
    }
}