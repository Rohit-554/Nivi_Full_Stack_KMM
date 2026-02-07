package io.jadu.nivi.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.jadu.nivi.presentation.theme.ElementsColors
import io.jadu.nivi.presentation.theme.MajorColors
import io.jadu.nivi.presentation.theme.Spacing
import io.jadu.nivi.presentation.theme.bodyLarge
import io.jadu.nivi.presentation.theme.bodyNormal
import io.jadu.nivi.presentation.utils.VSpacer
import io.jadu.nivi.presentation.utils.extensions.bounceClickable
import io.jadu.nivi.presentation.utils.squircleShape.SquircleShape

@Composable
fun CustomDialog(
    onDismissRequest: () -> Unit,
    title: String? = null,
    text: String? = null,
    confirmButtonText: String? = null,
    onConfirm: (() -> Unit)? = null,
    dismissButtonText: String? = null,
    onDismiss: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column(
            modifier = Modifier
                .widthIn(min = 280.dp, max = 400.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = SquircleShape(Spacing.s16)
                )
                .border(
                    width = Spacing.s1,
                    color = ElementsColors.BorderLight.color,
                    shape = SquircleShape(Spacing.s16)
                )
                .padding(Spacing.s20),
            verticalArrangement = Arrangement.spacedBy(Spacing.s16)
        ) {
                // Title
                title?.let {
                    Text(
                        text = it,
                        style = bodyLarge().copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Text content
                text?.let {
                    Text(
                        text = it,
                        style = bodyNormal(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }

                // Custom content
                content()

                // Buttons
                if (confirmButtonText != null || dismissButtonText != null) {
                    VSpacer(Spacing.s8)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.s8, Alignment.End)
                    ) {
                        // Dismiss button
                        dismissButtonText?.let { buttonText ->
                            OutlinedButton(
                                onClick = {
                                    onDismiss?.invoke() ?: onDismissRequest()
                                },
                                modifier = Modifier.bounceClickable(),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = ElementsColors.TextSecondary.color
                                ),
                                border = BorderStroke(
                                    width = Spacing.s1,
                                    color = ElementsColors.TextSecondary.color
                                )
                            ) {
                                Text(
                                    text = buttonText,
                                    style = bodyNormal().copy(fontWeight = FontWeight.Medium)
                                )
                            }
                        }

                        // Confirm button
                        confirmButtonText?.let { buttonText ->
                            Button(
                                onClick = {
                                    onConfirm?.invoke()
                                },
                                modifier = Modifier.bounceClickable(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MajorColors.TrustBlue.color,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    text = buttonText,
                                    style = bodyNormal().copy(fontWeight = FontWeight.Medium)
                                )
                            }
                        }
                    }
                }
            }
        }
    }