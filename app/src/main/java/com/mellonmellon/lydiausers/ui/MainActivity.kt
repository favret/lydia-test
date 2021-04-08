package com.mellonmellon.lydiausers.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mellonmellon.lydiausers.R


interface OnClickUserListener {
    fun onClickUserListener(userId: Int)
}

class MainActivity : AppCompatActivity(), OnClickUserListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userListFragment = UserListFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, userListFragment)
            .commit()
    }

    override fun onClickUserListener(userId: Int) {
        val userDetailFragment = UserDetailFragment.newInstance(userId)
        supportFragmentManager
            .beginTransaction()
            .addToBackStack("main")
            .replace(R.id.container, userDetailFragment)
            .commit()
    }


}
