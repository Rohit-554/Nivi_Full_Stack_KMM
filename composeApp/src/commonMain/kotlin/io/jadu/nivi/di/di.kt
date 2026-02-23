package io.jadu.nivi.di


import io.jadu.nivi.data.repository.AuthRepositoryImpl
import io.jadu.nivi.domain.manager.AuthManager
import io.jadu.nivi.domain.repository.AuthRepository
import io.jadu.nivi.domain.useCase.LoginUseCase
import io.jadu.nivi.domain.useCase.LogoutUseCase
import io.jadu.nivi.domain.useCase.SignupUseCase
import io.jadu.nivi.presentation.screens.OnboardingViewModel
import io.jadu.nivi.presentation.viewmodel.LoginViewModel
import io.jadu.nivi.presentation.viewmodel.SignupViewModel
import io.jadu.nivi.service.AuthService
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
private var koinRef: Koin? = null

fun initKoin(config: (KoinApplication) -> Unit = {}) {
    if(koinRef == null) {
        val app = startKoin {
            config(this)
            modules(appModule, platformModule())
        }
        koinRef = app.koin
    }
}

val appModule = module {
    single { AuthService() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single { AuthManager(get()) }
    single { OnboardingViewModel(get(), get()) }
    factory { LoginUseCase(get()) }
    factory { SignupUseCase(get()) }
    factory { LogoutUseCase(get()) }
    single { LoginViewModel(get(), get()) }
    single { SignupViewModel(get(), get()) }
}


fun resetKoin() {
    stopKoin()
    koinRef = null
    initKoin()
}

expect fun platformModule() : Module

fun getKoin(): Koin =
    koinRef ?: throw Throwable("Koin is not initialized. Call initKoin() first.")