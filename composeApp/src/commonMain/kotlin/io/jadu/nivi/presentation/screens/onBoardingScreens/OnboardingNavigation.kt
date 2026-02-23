package io.jadu.nivi.presentation.screens.onBoardingScreens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import io.jadu.nivi.presentation.navigation.AppRoute
import io.jadu.nivi.presentation.screens.OnboardingViewModel
import io.jadu.nivi.presentation.screens.auth.LoginScreen
import io.jadu.nivi.presentation.screens.auth.SignUpScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.koinInject

@Composable
fun OnBoardingNavigation(
    modifier: Modifier = Modifier,
    onBoardingVM: OnboardingViewModel = koinInject(),
    startDestination: AppRoute = AppRoute.OnBoarding.OnBoarding1,
    onLogin:() -> Unit,
) {
    val scope = rememberCoroutineScope ()
    val onBoardingBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(AppRoute.OnBoarding.Login::class, AppRoute.OnBoarding.Login.serializer())
                    subclass(AppRoute.OnBoarding.Signup::class, AppRoute.OnBoarding.Signup.serializer())
                    subclass(AppRoute.OnBoarding.OnBoarding1::class, AppRoute.OnBoarding.OnBoarding1.serializer())
                    subclass(AppRoute.OnBoarding.OnBoarding2::class, AppRoute.OnBoarding.OnBoarding2.serializer())
                    subclass(AppRoute.OnBoarding.Onboarding3::class, AppRoute.OnBoarding.Onboarding3.serializer())
                }
            }
        },
        startDestination
    )

    NavDisplay(
        backStack = onBoardingBackStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AppRoute.OnBoarding.OnBoarding1> {
                OnboardingScreen(
                    onFinishOnboarding = {
                        scope.launch { onBoardingVM.setUserStatus(false) }
                        onBoardingBackStack.add(AppRoute.OnBoarding.Login)
                        onBoardingBackStack.remove(AppRoute.OnBoarding.OnBoarding1)
                    }
                )
            }
            entry<AppRoute.OnBoarding.Login> {
                LoginScreen(
                    onLoginSuccess = {
                        onLogin()
                    },
                    onSignUpClick = {
                        onBoardingBackStack.add(AppRoute.OnBoarding.Signup)
                    }
                )
            }

            entry<AppRoute.OnBoarding.Signup> {
                SignUpScreen(
                    onLoginClick = {
                        onBoardingBackStack.remove(AppRoute.OnBoarding.Signup)
                    },
                    onSignUpSuccess = {
                        onLogin()
                    }
                )
            }
        }
    )
}