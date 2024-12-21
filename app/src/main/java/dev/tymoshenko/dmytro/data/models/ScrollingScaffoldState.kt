package dev.tymoshenko.dmytro.data.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

interface ScaffoldState {
    val barsStatus: State<BarsStatus>
    val contentTopOffset: State<Dp>
}

interface MutableScaffoldState: ScaffoldState {
    override val barsStatus: State<BarsStatus>
    override val contentTopOffset: State<Dp>

    fun setDirection(isScrollingUp: Boolean)
    fun setContentTopOffset(value: Dp)
    fun getContentTopOffsetState(): State<Dp>
    fun getBarsStatusState(): State<BarsStatus>
}

private class ScrollingScaffoldState: MutableScaffoldState {
    override val barsStatus: MutableState<BarsStatus> =
        mutableStateOf(BarsStatus.SHOWN)

    override val contentTopOffset: MutableState<Dp> =
        mutableStateOf(0.dp)

    override fun setDirection(isScrollingUp: Boolean) {
        barsStatus.value = when {
            isScrollingUp -> BarsStatus.SHOWN
            else -> BarsStatus.HIDDEN
        }
    }

    override fun setContentTopOffset(value: Dp) {
        contentTopOffset.value = value
    }

    override fun getContentTopOffsetState(): State<Dp> {
        return contentTopOffset
    }

    override fun getBarsStatusState(): State<BarsStatus> {
        return barsStatus
    }
}

fun mutableScrollingScaffoldState() : MutableScaffoldState {
    return ScrollingScaffoldState()
}

@Composable
fun rememberScrollingScaffoldState() = remember {
    mutableScrollingScaffoldState()
}