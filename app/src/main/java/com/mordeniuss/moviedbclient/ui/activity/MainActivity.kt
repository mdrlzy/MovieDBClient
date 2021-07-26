package com.mordeniuss.moviedbclient.ui.activity

import android.os.Bundle
import com.mordeniuss.moviedbclient.R
import com.mordeniuss.moviedbclient.mvp.presenter.MainPresenter
import com.mordeniuss.moviedbclient.mvp.view.MainView
import com.mordeniuss.moviedbclient.ui.App
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val presenter by moxyPresenter {
        MainPresenter().apply { App.instance.appComponent.inject(this) }
    }

    val navigator = SupportAppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instance.appComponent.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun init() {}
}