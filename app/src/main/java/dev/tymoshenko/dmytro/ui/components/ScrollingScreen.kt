package dev.tymoshenko.dmytro.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tymoshenko.dmytro.data.models.BarsStatus
import dev.tymoshenko.dmytro.data.models.MutableScaffoldState

@Composable
fun ScrollingScreen(
    scaffoldState: MutableScaffoldState,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val topBarHeight = remember { mutableStateOf(0.dp) }
    val bottomBarHeight = remember { mutableStateOf(0.dp) }

    val setTopBarHeight = remember {
        { height: Dp ->
            topBarHeight.value = height
        }
    }
    val setBottomBarHeight = remember {
        { height: Dp ->
            bottomBarHeight.value = height
        }
    }

    val topBarOffset = animateDpAsState(
        targetValue = when (scaffoldState.getBarsStatusState().value) {
            BarsStatus.SHOWN -> 0.dp
            BarsStatus.HIDDEN -> -topBarHeight.value
        },
        label = "null",
        animationSpec = tween(500)
    )


    val bottomBarOffset = animateDpAsState(
        targetValue = when (scaffoldState.getBarsStatusState().value) {
            BarsStatus.SHOWN -> 0.dp
            BarsStatus.HIDDEN -> bottomBarHeight.value
        },
        label = "null",
        animationSpec = tween(500)
    )

    ScrollingScreenContent(
        topBarHeight = topBarHeight.value,
        bottomBarHeight = bottomBarHeight.value,
        topBarOffset = topBarOffset.value,
        bottomBarOffset = bottomBarOffset.value,
        setTopBarHeight = setTopBarHeight,
        setBottomBarHeight = setBottomBarHeight,
        topBar = topBar,
        bottomBar = bottomBar,
        content = content
    )
}

@Composable
fun ScrollingScreenContent(
    topBarHeight: Dp,
    bottomBarHeight: Dp,
    topBarOffset: Dp,
    bottomBarOffset: Dp,
    setTopBarHeight: (Dp) -> Unit,
    setBottomBarHeight: (Dp) -> Unit,
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
                .onSizeChanged { size ->
                    with(density) {
                        setTopBarHeight.invoke(size.height.toDp())
                    }
                }
                .offset(y = topBarOffset)
        ) {
            topBar?.invoke()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = bottomBarHeight - bottomBarOffset,
                    top = topBarHeight + topBarOffset
                )
        ) {
            content.invoke()
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .onSizeChanged { size ->
                    with(density) {
                        setBottomBarHeight.invoke(size.height.toDp())
                    }
                }
                .offset(y = bottomBarOffset)
        ) {
            bottomBar?.invoke()
        }
    }
}