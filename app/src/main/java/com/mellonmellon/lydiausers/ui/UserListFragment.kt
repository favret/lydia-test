package com.mellonmellon.lydiausers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mellonmellon.lydiausers.R
import com.mellonmellon.lydiausers.data.entities.User
import com.mellonmellon.lydiausers.ui.adapter.ListUserAdapter
import com.mellonmellon.lydiausers.viewmodels.UserListViewModel
import kotlinx.android.synthetic.main.user_list_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class UserListFragment: Fragment() {

    private val viewModel: UserListViewModel by viewModels()
    private lateinit var adapter: ListUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_list_fragment, container, false)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instantiateRecyclerView()
        instantiateButton()
        instantiateObserver()
    }

    @ExperimentalCoroutinesApi
    private fun instantiateRecyclerView() {
        list_users.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListUserAdapter(listOf())
        adapter.clickUserListener = activity as MainActivity
        list_users.adapter = adapter
    }

    @ExperimentalCoroutinesApi
    private fun instantiateObserver() {
        val userObserver = Observer<List<User>> { result ->
            if(result.isNotEmpty()) {
                hasInternetProblem(false)
                setLoading(false)
                instantiateRecyclerView()
                adapter.changeUsers(result)
            } else if (!viewModel.isInternetAvailable) {
                setLoading(false)
                hasInternetProblem(true)
            }
            list_users_page.text = viewModel.page.value.toString()
        }

        viewModel.users?.observe(viewLifecycleOwner, userObserver)
    }

    private fun instantiateButton() {
        btn_next.setOnClickListener {
            viewModel.page.value += 1
            setLoading(true)
            btn_previous.isEnabled = (viewModel.page.value > 1)
        }

        btn_previous.setOnClickListener {
            viewModel.page.value -=1
            setLoading(true)
            it.isEnabled = (viewModel.page.value > 1)
        }
    }

    private fun setLoading(loading: Boolean) {
        if (loading) {
            list_progress_bar.visibility = View.VISIBLE
        } else {
            list_progress_bar.visibility = View.INVISIBLE
        }
    }

    private fun hasInternetProblem(hasIt: Boolean) {
        if(hasIt) {
            image_internet.visibility = View.VISIBLE
            text_internet.visibility = View.VISIBLE
            list_users.visibility = View.INVISIBLE
        } else {
            image_internet.visibility = View.INVISIBLE
            text_internet.visibility = View.INVISIBLE
            list_users.visibility = View.VISIBLE
        }
    }
}