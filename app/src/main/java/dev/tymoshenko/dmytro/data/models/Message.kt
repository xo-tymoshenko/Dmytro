package dev.tymoshenko.dmytro.data.models

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class Message(val text: String)

fun placeholderList(): List<Message> {
    return buildList {
        repeat(100) {
            add(Message(text = UUID.randomUUID().toString()))
        }
    }
}
