package io.jadu.nivi.presentation.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import io.jadu.nivi.presentation.navigation.AppRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun HomeNavigation(
    modifier: Modifier = Modifier
) {
    val homeBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(AppRoute.Home.Dashboard::class, AppRoute.Home.Dashboard.serializer())
                    subclass(AppRoute.Home.Settings::class, AppRoute.Home.Settings.serializer())
                    subclass(AppRoute.Home.Profile::class, AppRoute.Home.Profile.serializer())
                }
            }
        },
        AppRoute.Home.Dashboard
    )

    NavDisplay(
        backStack = homeBackStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AppRoute.Home.Dashboard> {
                //TODO:: IMPLEMENT THE COMPOSABLE
                homeBackStack.add(AppRoute.Home.Settings)
            }
            entry<AppRoute.Home.Settings> {
                //TODO:: IMPLEMENT THE COMPOSABLE
                homeBackStack.add(AppRoute.Home.Settings)
            }
            entry<AppRoute.Home.Profile> {
                //TODO:: IMPLEMENT THE COMPOSABLE
            }
        }
    )
}