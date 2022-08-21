package com.codelabs.southsystem.eventos.features.events.presentation.detail

import com.codelabs.southsystem.eventos.core.utils.format
import com.codelabs.southsystem.eventos.core.utils.toBrazilianCurrency
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event
import com.codelabs.southsystem.eventos.features.events.domain.entities.EventAddress
import java.lang.StringBuilder

data class EventSharePresenter(
    private val event: Event,
    private val eventAddress: EventAddress?,
) {
    val subject: String get() = "[EVENTO] ${event.title}"

    val text: String get() = buildText()

    private fun buildText(): String {
        return StringBuilder().apply {
            append("Data: ${event.date.format()}")
            append("\nEntrada: ${event.price.toBrazilianCurrency()}")
            if (eventAddress != null) append("\nLocal: ${eventAddress.formattedAddress}")
            append("\n\n${event.description}")
        }.toString()
    }
}
