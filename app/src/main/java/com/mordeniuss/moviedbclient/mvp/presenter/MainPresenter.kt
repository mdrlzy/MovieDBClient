package com.mordeniuss.moviedbclient.mvp.presenter

import com.mordeniuss.moviedbclient.mvp.view.MainView
import com.mordeniuss.moviedbclient.navigation.Screens
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter: MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        router.replaceScreen(Screens.SearchScreen())
    }
}