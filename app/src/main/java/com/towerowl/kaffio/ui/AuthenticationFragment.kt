package com.towerowl.kaffio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.towerowl.kaffio.App
import com.towerowl.kaffio.R
import com.towerowl.kaffio.data.User
import kotlinx.android.synthetic.main.authentication_fragment.*

class AuthenticationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.authentication_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authentication_login.setOnClickListener {
            authentication_username.text?.toString()?.also { username ->
                if (username.isEmpty()) return@setOnClickListener

                App.dagger.authenticationViewModel().login(User(name = username))
            }
        }
    }
}