package com.project.di.module

import com.project.repository.GitRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    //provides a singleton instance
    single {
        GitRepositoryImpl(get())
    }
    //provides a new instance each time its called
    factory {  }
}