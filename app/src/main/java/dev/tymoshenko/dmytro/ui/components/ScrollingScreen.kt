package dev.tymoshenko.dmytro.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
        topBar = topBar,
        navStatus = navStatus,
        bottomBar = bottomBar,
        content = content
    )
}

@Composable
private fun ScrollingScreenContent(
    navStatus: NavStatus,

    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .clipToBounds()
    ) {
        androidx.compose.animation.AnimatedVisibility(
            visible = if (navStatus == NavStatus.SHOWN) true else false,
            enter = expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top,
                animationSpec = tween(500)
            ),
            exit = shrinkVertically(animationSpec = tween(500)),
        ){
            topBar?.invoke()
        }

        Box(

            modifier = Modifier
                .weight(1F)

        ) {
            content.invoke()
        }

        androidx.compose.animation.AnimatedVisibility(
            visible = if (navStatus == NavStatus.SHOWN) true else false,
            enter = slideInVertically() + expandVertically(
                expandFrom = Alignment.Bottom,
                animationSpec = tween(500)
            ),
            exit = shrinkVertically(animationSpec = tween(500)),
        ){
            bottomBar?.invoke()
        }
    }
}