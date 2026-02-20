package io.jadu.nivi.presentation.utils.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

fun createBoldAnnotatedString(
    fullString: String,
    boldStrings: List<String>,
    nonBoldColor: Color? = null,
    boldColor: Color? = null,
    isBoldFontLarge: Boolean = false
): AnnotatedString {
    return buildAnnotatedString {
        append(fullString)

        val boldRanges = mutableListOf<IntRange>()

        boldStrings.forEach { boldText ->
            Regex(Regex.escape(boldText)).findAll(fullString).forEach { matchResult ->
                val range = matchResult.range
                boldRanges.add(range)
                addStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = if (isBoldFontLarge) 18.sp else TextUnit.Unspecified,
                        color = boldColor ?: Color.Unspecified
                    ),
                    start = range.first,
                    end = range.last + 1
                )
            }
        }

        nonBoldColor?.let { color ->
            var currentIndex = 0
            val sortedRanges = boldRanges.sortedBy { it.first }

            sortedRanges.forEach { range ->
                if (currentIndex < range.first) {
                    addStyle(
                        style = SpanStyle(color = color),
                        start = currentIndex,
                        end = range.first
                    )
                }
                currentIndex = range.last + 1
            }

            if (currentIndex < fullString.length) {
                addStyle(
                    style = SpanStyle(color = color),
                    start = currentIndex,
                    end = fullString.length
                )
            }
        }
    }
}

fun String?.orEmpty(): String {
    return if (this.isNullOrBlank()) "" else this
}