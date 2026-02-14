package io.jadu.nivi.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jadu.nivi.presentation.components.InputTextField
import io.jadu.nivi.presentation.components.NiviButton
import io.jadu.nivi.presentation.components.showSnackBar
import io.jadu.nivi.presentation.theme.ElementsColors
import io.jadu.nivi.presentation.theme.Spacing
import io.jadu.nivi.presentation.theme.bodyLarge
import io.jadu.nivi.presentation.theme.h3TextStyle
import io.jadu.nivi.presentation.utils.HDivider
import io.jadu.nivi.presentation.utils.VSpacer
import io.jadu.nivi.presentation.utils.extensions.bounceClickable
import io.jadu.nivi.presentation.utils.extensions.createBoldAnnotatedString
import io.jadu.nivi.presentation.utils.extensions.noRippleClickable
import nivi.composeapp.generated.resources.Res
import nivi.composeapp.generated.resources.account_login_protection
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginScreen() {
    val focusManager = LocalFocusManager.current
    val isLoading = remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val signUpText = createBoldAnnotatedString(
        fullString = "Don't have an account? Sign Up",
        boldStrings = listOf("Sign Up"),
        nonBoldColor = ElementsColors.DarkGray.color,
        boldColor = ElementsColors.LinkColor.color,
        isBoldFontLarge = false
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.s16)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.account_login_protection),
                contentDescription = "Login Image",
                modifier = Modifier
                    .size(240.dp)
                    .padding(top =Spacing.s16),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Sign In",
                        style = h3TextStyle().copy(fontWeight = FontWeight.Bold)
                    )
                    VSpacer(Spacing.s8)
                    Text(
                        "Welcome back! Please enter your details.",
                        style = bodyLarge().copy(fontWeight = FontWeight.Normal)
                    )
                }
                VSpacer(Spacing.s8)
                InputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    label = "Email",
                    onValueChange = {
                        email = it
                    },
                    placeholder = "Enter your email",
                    leadingIcon = Icons.Default.Person
                )
                VSpacer(Spacing.s2)
                InputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    label = "Password",
                    onValueChange = {
                        password = it
                    },
                    placeholder = "Enter your password",
                    isPassword = true,
                    leadingIcon = Icons.Default.Lock
                )
                VSpacer(Spacing.s8)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Forgot Password?",
                        style = bodyLarge(),
                        color = ElementsColors.LinkColor.color,
                        modifier = Modifier
                            .padding(Spacing.s8)
                            .bounceClickable { /* Handle forgot password */ }
                    )
                }
                VSpacer(Spacing.s16)
                NiviButton(
                    modifier = Modifier,
                    text = "Login",
                    isEnabled = !email.isBlank() && !password.isBlank(),
                    onClick = {  }
                )
                VSpacer(Spacing.s16)
                OrDivider(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Or Continue with",
                    color = Color.LightGray,
                    textColor = Color.Gray,
                    thickness = Spacing.s1
                )
                VSpacer(Spacing.s16)
                NiviButton(
                    modifier = Modifier,
                    text = "Login with Google",
                    onClick = {
                        showSnackBar(
                            message = "Wrong Password",
                            positiveMessage = false
                        )
                    }
                )
                VSpacer()
                Text(
                    text = signUpText,
                    style = bodyLarge(),
                    modifier = Modifier
                        .padding(Spacing.s8)
                        .noRippleClickable {

                        }
                )
            }
        }
    }
}

@Composable
fun OrDivider(
    modifier: Modifier = Modifier,
    text: String = "Or Continue with",
    color: Color = Color.LightGray,
    textColor: Color = Color.Gray,
    thickness: Dp = Spacing.s1
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HDivider(
            modifier = Modifier
                .weight(1f)
                .height(thickness),
        )
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.padding(horizontal = Spacing.s8),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        HDivider(
            modifier = Modifier
                .weight(1f)
                .height(thickness),
        )
    }
}