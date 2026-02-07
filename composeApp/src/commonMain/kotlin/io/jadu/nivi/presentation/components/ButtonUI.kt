package io.jadu.nivi.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import io.jadu.nivi.presentation.theme.ElementsColors
import io.jadu.nivi.presentation.theme.Spacing
import io.jadu.nivi.presentation.theme.bodyLarge
import io.jadu.nivi.presentation.utils.extensions.bounceClickable
import io.jadu.nivi.presentation.utils.squircleShape.SquircleShape

@Composable
fun ButtonUI(
    text: String = "",
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonType: ButtonType = ButtonType.Filled,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: ImageVector? = null,
    shape: Shape = SquircleShape(40f),
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    textStyle: TextStyle = bodyLarge().copy(fontWeight = FontWeight.Bold),
    contentPadding: PaddingValues = PaddingValues(horizontal = Spacing.s16, vertical = Spacing.s12),
    iconSize: Dp = Spacing.s20
) {
    val backgroundColor = when (buttonType) {
        ButtonType.Filled -> if (enabled) containerColor else ElementsColors.ButtonDisabled.color
        ButtonType.Outlined -> Color.Transparent
        ButtonType.Tonal -> if (enabled) containerColor.copy(alpha = 0.2f) else ElementsColors.ButtonDisabled.color
        ButtonType.Text -> Color.Transparent
        ButtonType.IconOnly -> if (enabled) containerColor else ElementsColors.ButtonDisabled.color
    }

    val borderStroke = when (buttonType) {
        ButtonType.Outlined -> BorderStroke(Spacing.s1, containerColor)
        else -> null
    }


    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .bounceClickable {
                onClick()
            }
            .then(if (borderStroke != null) Modifier.border(borderStroke, shape) else Modifier)
            .padding(contentPadding)
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                color = contentColor,
                strokeWidth = Spacing.s2,
                modifier = Modifier
                    .size(Spacing.s18)
                    .align(Alignment.Center)
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.Center)
            ) {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(iconSize)
                    )
                    if (text.isNotEmpty()) Spacer(modifier = Modifier.width(Spacing.s8))
                }
                if (text.isNotEmpty()) {
                    Text(
                        text = text,
                        style = textStyle,
                        color = contentColor
                    )
                }
            }
        }
    }
}

enum class ButtonType {
    Filled,
    Outlined,
    Tonal,
    Text,
    IconOnly
}

@Composable
fun NiviButton(text:String, onClick: () -> Unit, modifier: Modifier = Modifier, isEnabled: Boolean = true) {
    ButtonUI(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.s16)
            .height(Spacing.s12),
        onClick = onClick,
        text = text,
        enabled = isEnabled,
    )
}

@Composable
@Preview
fun ButtonUIPreview() {
    ButtonUI(
        modifier = Modifier.fillMaxWidth(),
        onClick = {},
        text = "Click Me",
    )
}
