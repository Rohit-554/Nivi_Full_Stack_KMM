package io.jadu.nivi.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import io.jadu.nivi.presentation.components.CustomSnackbarHost
import io.jadu.nivi.presentation.screens.home.HomeNavigation
import io.jadu.nivi.presentation.screens.onBoardingScreens.OnBoardingNavigation
import io.jadu.nivi.presentation.theme.Spacing
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun AppNavRoute(
    modifier: Modifier = Modifier,
    startDestination: AppRoute,
) {
    val rootBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(AppRoute.OnBoarding::class, AppRoute.OnBoarding.serializer())
                    subclass(AppRoute.Home::class, AppRoute.Home.serializer())
                }
            }
        },
        // If user is logged in, start at Home, otherwise start at OnBoarding
         if (startDestination == AppRoute.Home) AppRoute.Home else AppRoute.OnBoarding
    )

    Box(modifier = Modifier.fillMaxSize()) {
        CustomSnackbarHost(
            modifier =
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = Spacing.s48)
                    .zIndex(99999f)
        )
        NavDisplay (
            modifier = modifier,
            backStack = rootBackStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<AppRoute.OnBoarding> {
                    OnBoardingNavigation(
                        startDestination = startDestination,
                        onLogin = {
                            rootBackStack.remove(AppRoute.OnBoarding)
                            rootBackStack.add(AppRoute.Home)
                        }
                    )
                }

                entry<AppRoute.Home> {
                    HomeNavigation()
                }
            }
        )
    }

}