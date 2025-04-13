package com.project

import android.app.Application
import com.project.di.module.appModule
import com.project.di.module.networkModule
import com.project.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration

/**
 * This is the main application class.
 */
class GitAppApplication: Application(), KoinStartup {

    @KoinExperimentalAPI
    override fun onKoinStartup() = koinConfiguration {
        androidContext(this@GitAppApplication)
        modules(appModule, networkModule, viewModelModule)
    }

}