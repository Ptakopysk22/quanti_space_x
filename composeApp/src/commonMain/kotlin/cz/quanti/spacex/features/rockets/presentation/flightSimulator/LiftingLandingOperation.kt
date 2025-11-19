package cz.quanti.spacex.features.rockets.presentation.flightSimulator

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import quanti_space_x.composeapp.generated.resources.Res
import quanti_space_x.composeapp.generated.resources.description_rocket_flying

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LiftingLandingOperation(
    modifier: Modifier = Modifier,
    startAnimationPosition: Float,
    endAnimationPosition: Float,
    animationDuration: Int = 2000,
    offsetCorrection: Float,
    onOperationFinished: () -> Unit,
) {

    val startAnimation = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation.value = true
    }

    val offsetYAnimation by animateFloatAsState(
        targetValue = if (startAnimation.value) endAnimationPosition else startAnimationPosition,
        animationSpec = tween(animationDuration, easing = FastOutSlowInEasing),
        finishedListener = {
            onOperationFinished()
        }
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = Res.getUri("files/Rocket-Flying.svg"),
            contentDescription = stringResource(Res.string.description_rocket_flying),
            modifier = Modifier
                .graphicsLayer {
                    translationY = offsetYAnimation + offsetCorrection
                }
        )
    }
}