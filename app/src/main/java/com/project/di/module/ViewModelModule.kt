package com.project.di.module

import com.project.viewmodel.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UserViewModel(get())
    }
}