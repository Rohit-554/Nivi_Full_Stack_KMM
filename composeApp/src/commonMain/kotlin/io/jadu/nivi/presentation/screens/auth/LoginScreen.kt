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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import io.jadu.nivi.presentation.utils.extensions.orEmpty
import io.jadu.nivi.presentation.viewmodel.LoginEvent
import io.jadu.nivi.presentation.viewmodel.LoginViewModel
import nivi.composeapp.generated.resources.Res
import nivi.composeapp.generated.resources.account_login_protection
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinInject(),
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val isLoading = remember { mutableStateOf(false) }
    val uiState by loginViewModel.uiState.collectAsState()



    LaunchedEffect(Unit) {
        loginViewModel.event.collect { event->
            when(event) {
                is LoginEvent.Error -> showSnackBar(message = event.message, positiveMessage = false)
                is LoginEvent.Success -> onLoginSuccess()
            }
        }
    }

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
                    value = uiState.loginRequest?.email.orEmpty(),
                    label = "Email",
                    onValueChange = loginViewModel::updateEmail,
                    placeholder = "Enter your email",
                    leadingIcon = Icons.Default.Person
                )
                VSpacer(Spacing.s2)
                InputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.loginRequest?.password.orEmpty(),
                    label = "Password",
                    onValueChange = loginViewModel::updatePassword,
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
                    isEnabled = uiState.isEnabled,
                    onClick = { loginViewModel.login() }
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
                            onSignUpClick()
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