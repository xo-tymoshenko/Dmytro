package dev.tymoshenko.dmytro.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.tymoshenko.dmytro.data.models.NavStatus

@Composable
fun ScrollingScreen(
    navStatus: NavStatus,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
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
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .clipToBounds()
    ) {
        androidx.compose.animation.AnimatedVisibility(
            visible = navStatus == NavStatus.SHOWN,
            enter = expandVertically(animationSpec = tween(500), expandFrom = Alignment.Bottom),
            exit = shrinkVertically(animationSpec = tween(500), shrinkTowards = Alignment.Bottom)
        ) {
            topBar?.invoke()
        }

        Box(
            modifier = Modifier
                .weight(1F)
        ) {
            content.invoke()
        }

        androidx.compose.animation.AnimatedVisibility(
            visible = navStatus == NavStatus.SHOWN,
            enter = expandVertically(animationSpec = tween(500), expandFrom = Alignment.Top),
            exit = shrinkVertically(animationSpec = tween(500), shrinkTowards = Alignment.Top)
        ) {
            bottomBar?.invoke()
        }
    }
}