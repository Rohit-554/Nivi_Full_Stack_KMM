package io.jadu.nivi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import io.jadu.nivi.presentation.screens.home.HomeNavigation
import io.jadu.nivi.presentation.screens.onBoardingScreens.OnBoardingNavigation
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun AppNavRoute(
    modifier: Modifier = Modifier
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
        AppRoute.OnBoarding
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