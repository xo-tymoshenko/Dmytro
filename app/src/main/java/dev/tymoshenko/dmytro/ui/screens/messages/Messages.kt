package dev.tymoshenko.dmytro.ui.screens.messages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.tymoshenko.dmytro.data.models.Message
import dev.tymoshenko.dmytro.data.models.NavStatus
import dev.tymoshenko.dmytro.data.models.placeholderList
import dev.tymoshenko.dmytro.ui.components.BottomBar
import dev.tymoshenko.dmytro.ui.components.ScrollingScreen
import dev.tymoshenko.dmytro.ui.components.TopBar
import dev.tymoshenko.dmytro.ui.screens.messages.components.MessagePreview
import dev.tymoshenko.dmytro.utils.helpers.isScrollingUp

@Composable
fun Messages() {
    val navStatus = remember { mutableStateOf(NavStatus.SHOWN) }
    val listState = rememberLazyListState()

    val isScrollingUp by listState.isScrollingUp()

    LaunchedEffect(isScrollingUp) {
        navStatus.value = if (isScrollingUp) NavStatus.SHOWN
        else NavStatus.HIDDEN
    }

    MessagesContent(
        navStatus = navStatus.value,
        listState = listState
    )
}

@Composable
private fun MessagesContent(
    navStatus: NavStatus,
    listState: LazyListState
) {
    ScrollingScreen(
        navStatus = navStatus,
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