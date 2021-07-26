package com.mordeniuss.moviedbclient.di.module

import com.mordeniuss.moviedbclient.ui.App
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App {
        return app
    }

}
