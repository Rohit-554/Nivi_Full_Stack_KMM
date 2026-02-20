package io.jadu.nivi.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.jadu.nivi.presentation.components.InputTextField
import io.jadu.nivi.presentation.components.NiviButton
import io.jadu.nivi.presentation.components.showSnackBar
import io.jadu.nivi.presentation.theme.ElementsColors
import io.jadu.nivi.presentation.theme.MajorColors
import io.jadu.nivi.presentation.theme.Spacing
import io.jadu.nivi.presentation.theme.bodyLarge
import io.jadu.nivi.presentation.theme.h3TextStyle
import io.jadu.nivi.presentation.utils.VSpacer
import io.jadu.nivi.presentation.utils.extensions.bounceClickable
import io.jadu.nivi.presentation.utils.extensions.createBoldAnnotatedString
import io.jadu.nivi.presentation.utils.extensions.noRippleClickable
import io.jadu.nivi.presentation.utils.extensions.orEmpty
import io.jadu.nivi.presentation.viewmodel.SignupEvent
import io.jadu.nivi.presentation.viewmodel.SignupViewModel
import kotlinx.coroutines.launch
import nivi.composeapp.generated.resources.Res
import nivi.composeapp.generated.resources.signup_
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SignUpScreen(
    signupViewModel: SignupViewModel = koinInject(),
    onLoginClick: () -> Unit,
    onSignUpSuccess: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val uiState by signupViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        signupViewModel.event.collect { event ->
            when(event) {
                is SignupEvent.Error -> showSnackBar(message = event.message, positiveMessage = false)
                is SignupEvent.Success -> onSignUpSuccess()
            }
        }
    }

    val haveAnAccountText = createBoldAnnotatedString(
        fullString = "Already have an account? Sign In",
        boldStrings = listOf("Sign In"),
        nonBoldColor = ElementsColors.DarkGray.color,
        boldColor = ElementsColors.LinkColor.color,
        isBoldFontLarge = false
    )

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val showScrollIndicator = scrollState.value == 0


    Scaffold { contentPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(Res.drawable.signup_),
                    contentDescription = "Signup Image",
                    modifier = Modifier
                        .size(240.dp),
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
                            "Sign Up",
                            style = h3TextStyle().copy(fontWeight = FontWeight.Bold)
                        )
                        VSpacer(Spacing.s8)
                        Text(
                            "Nivi Family is Excited to have you!",
                            style = bodyLarge().copy(fontWeight = FontWeight.Normal)
                        )
                    }
                    VSpacer(Spacing.s8)
                    InputTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.registerRequest?.email.orEmpty(),
                        label = "Email",
                        onValueChange = signupViewModel::updateEmail,
                        placeholder = "Enter your email",
                        leadingIcon = Icons.Default.Person
                    )
                    VSpacer(Spacing.s8)
                    InputTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.registerRequest?.password.orEmpty(),
                        label = "Password",
                        onValueChange = signupViewModel::updatePassword,
                        placeholder = "Enter your password",
                        isPassword = true,
                        leadingIcon = Icons.Default.Lock
                    )
                    VSpacer(Spacing.s8)
                    InputTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.confirmPassword,
                        label = "Confirm Password",
                        onValueChange = signupViewModel::updateConfirmPassword,
                        placeholder = "Confirm your password",
                        isPassword = true,
                        leadingIcon = Icons.Default.Lock
                    )
                    VSpacer(Spacing.s20)
                    Spacer(modifier = Modifier.weight(1f))
                    NiviButton(
                        modifier = Modifier,
                        text = "Create Account",
                        isEnabled = uiState.isEnabled,
                        onClick = { signupViewModel.signup() }
                    )
                    VSpacer(Spacing.s4)
                    Text(
                        text = haveAnAccountText,
                        style = bodyLarge(),
                        modifier = Modifier
                            .padding(Spacing.s8)
                            .noRippleClickable { onLoginClick() }
                    )
                }
            }

            if(showScrollIndicator && scrollState.maxValue > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 20.dp, bottom = 30.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MajorColors.White.color)
                        .bounceClickable {
                            coroutineScope.launch {
                                scrollState.animateScrollTo(scrollState.maxValue)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Scroll Down",
                        modifier = Modifier
                            .size(28.dp)
                            .alpha(0.5f)
                    )
                }
            }
        }

        if(uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        ElementsColors.GrayBorderColor.color
                    )
            ) {
                CircularWavyProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = MajorColors.TrustBlue.color
                )
            }
        }
    }

}