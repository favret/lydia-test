package com.mellonmellon.lydiausers.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mellonmellon.lydiausers.R
import com.mellonmellon.lydiausers.data.entities.User
import com.mellonmellon.lydiausers.ui.OnClickUserListener

class ListUserAdapter(private var dataSet: List<User>): RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    var clickUserListener: OnClickUserListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.user_list_adapter, parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.count()
    }

    override fun getItemId(position: Int): Long {
        return dataSet[position].uid.toLong()
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(holder) {
            firstNameTextView.text = dataSet[position].name.first?.capitalize(Locale.current)
            lastNameTextView.text = dataSet[position].name.last?.capitalize(Locale.current)
            emailTextView.text = dataSet[position].email
            listHolder.setOnClickListener {
                clickUserListener?.onClickUserListener(dataSet[position].uid)
            }
        }
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstNameTextView: TextView = view.findViewById(R.id.list_first_name)
        val lastNameTextView: TextView = view.findViewById(R.id.list_last_name)
        val emailTextView: TextView = view.findViewById(R.id.list_email)
        val listHolder: ConstraintLayout = view.findViewById(R.id.list_holder)
    }

    fun changeUsers(users: List<User>) {
        dataSet = users
        notifyDataSetChanged()
    }
}