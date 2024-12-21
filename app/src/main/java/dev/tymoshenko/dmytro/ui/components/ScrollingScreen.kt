package dev.tymoshenko.dmytro.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import dev.tymoshenko.dmytro.data.models.NavStatus

@Composable
fun ScrollingScreen(
    navStatus: NavStatus,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val topBarHeightState = remember { mutableStateOf(0.dp) }
    val bottomBarHeightState = remember { mutableStateOf(0.dp) }

    val topBarOffset = animateDpAsState(
        targetValue = when (navStatus) {
            NavStatus.SHOWN -> 0.dp
            NavStatus.HIDDEN -> topBarHeightState.value
        },
        label = "null",
        animationSpec = tween(500)
    )

    val bottomBarOffset = animateDpAsState(
        targetValue = when (navStatus) {
            NavStatus.SHOWN -> 0.dp
            NavStatus.HIDDEN -> bottomBarHeightState.value
        },
        label = "null",
        animationSpec = tween(500)
    )

    ScrollingScreenContent(
        topBarHeightState = topBarHeightState,
        bottomBarHeightState = bottomBarHeightState,
        topBarOffset = topBarOffset.value,
        bottomBarOffset = bottomBarOffset.value,
        topBar = topBar,
        bottomBar = bottomBar,
        content = content
    )
}

@Composable
fun ScrollingScreenContent(
    topBarHeightState: MutableState<Dp>,
    bottomBarHeightState: MutableState<Dp>,
    topBarOffset: Dp,
    bottomBarOffset: Dp,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .clipToBounds()
    ) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .onGloballyPositioned {
                    with(density) { topBarHeightState.value = it.size.height.toDp() }
                }
                .offset(y = -topBarOffset)
        ) {
            topBar?.invoke()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    // Тут Padding must be non-negative виключення, якщо без макс.
                    // Хз чому.
                    top = max(0.dp, topBarHeightState.value - topBarOffset),
                    bottom = max(0.dp, bottomBarHeightState.value - bottomBarOffset)
                )
        ) {
            content.invoke()
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .onGloballyPositioned {
                    with(density) { bottomBarHeightState.value = it.size.height.toDp() }
                }
                .offset(y = bottomBarOffset)
        ) {
            bottomBar?.invoke()
        }
    }
}