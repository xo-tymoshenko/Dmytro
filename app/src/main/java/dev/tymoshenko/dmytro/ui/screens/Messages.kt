package dev.tymoshenko.dmytro.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import dev.tymoshenko.dmytro.data.models.Message
import dev.tymoshenko.dmytro.data.models.MutableScaffoldState
import dev.tymoshenko.dmytro.data.models.placeholderList
import dev.tymoshenko.dmytro.data.models.rememberScrollingScaffoldState
import dev.tymoshenko.dmytro.ui.screens.components.BottomBar
import dev.tymoshenko.dmytro.ui.components.ScrollingScreen
import dev.tymoshenko.dmytro.ui.screens.components.TopBar
import dev.tymoshenko.dmytro.ui.screens.components.MessagePreview

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun Messages() {
    val density = LocalDensity.current

    val scaffoldState = rememberScrollingScaffoldState()
    val listState = rememberLazyListState()

    val isScrollingUp = listState.isScrollingUp()

    LaunchedEffect(isScrollingUp) {
        scaffoldState.setDirection(isScrollingUp = isScrollingUp)
    }

    MessagesContent(
        scaffoldState = scaffoldState,
        listState = listState
    )
}

@Composable
fun MessagesContent(
    scaffoldState: MutableScaffoldState,
    listState: LazyListState
) {
    ScrollingScreen(
        scaffoldState = scaffoldState,
        topBar = { TopBar() },
        bottomBar = { BottomBar() }
    ) {
        LazyColumn(
            state = listState
        ) {
            items(placeholderList()) { item: Message ->
                MessagePreview(
                    text = item.text
                )
            }
        }
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}