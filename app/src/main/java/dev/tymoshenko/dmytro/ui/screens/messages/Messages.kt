package dev.tymoshenko.dmytro.ui.screens.messages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import dev.tymoshenko.dmytro.data.models.Message
import dev.tymoshenko.dmytro.data.models.NavStatus
import dev.tymoshenko.dmytro.data.models.placeholderList
import dev.tymoshenko.dmytro.ui.components.BottomBar
import dev.tymoshenko.dmytro.ui.components.ScrollingScreen
import dev.tymoshenko.dmytro.ui.components.TopBar
import dev.tymoshenko.dmytro.ui.screens.messages.components.MessagePreview

@Composable
fun Messages() {
    val listState = rememberLazyListState()

    val navStatus = remember(listState) {
        derivedStateOf {
            when {
                listState.firstVisibleItemScrollOffset == 0 && listState.firstVisibleItemIndex == 0 -> NavStatus.SHOWN
                listState.lastScrolledBackward -> NavStatus.SHOWN
                else -> NavStatus.HIDDEN
            }
        }
    }

//    val isScrollingUp by listState.isScrollingUp()
//    val navStatus = remember(isScrollingUp) {
//        mutableStateOf(
//            if (isScrollingUp) NavStatus.SHOWN
//            else NavStatus.HIDDEN
//        )
//    }

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