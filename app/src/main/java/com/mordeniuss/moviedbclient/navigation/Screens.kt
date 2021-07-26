package com.mordeniuss.moviedbclient.navigation

import androidx.fragment.app.Fragment
import com.mordeniuss.moviedbclient.ui.fragment.SearchFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class SearchScreen: SupportAppScreen() {
        override fun getFragment() = SearchFragment()
    }
}