package com.mellonmellon.lydiausers.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.mellonmellon.lydiausers.R
import com.mellonmellon.lydiausers.data.entities.User
import com.mellonmellon.lydiausers.viewmodels.UserDetailViewModel
import kotlinx.android.synthetic.main.user_detail.*
import java.util.*


class UserDetailFragment: Fragment() {


    private lateinit var viewModel: UserDetailViewModel
    private lateinit var phoneToCall: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = arguments?.getInt("userId") ?: 0
        viewModel = UserDetailViewModel(userId)
        observe()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_detail, container, false)
    }

    private fun observe() {
        val userObserver = Observer<User> { result ->
            if(result != null)
                majUI(result)
        }
        viewModel.user.observe(this, userObserver)
    }

    private fun majUI(user: User) {
        val circularProgress = CircularProgressDrawable(requireActivity())
        circularProgress.strokeWidth = 5f
        circularProgress.centerRadius = 30f
        circularProgress.start()

        Glide
            .with(this)
            .load(user.picture.medium)
            .circleCrop()
            .placeholder(circularProgress)
            .into(detail_picture)

        detail_title_name.text = user.name.title?.capitalize(Locale.current)
        detail_first_name.text = user.name.first?.capitalize(Locale.current)
        detail_last_name.text = user.name.last?.capitalize(Locale.current)
        date_of_birth.text = getDate(user.dob)

        detail_email.text = user.email
        detail_email.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        detail_tel.text = user.phone
        detail_tel.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        detail_cell.text = user.cell
        detail_cell.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        detail_tel.setOnClickListener {
            phoneToCall = transformPhone(user.phone)
            call()
        }

        detail_cell.setOnClickListener {
            phoneToCall = transformPhone(user.cell)
            call()
        }

        detail_email.setOnClickListener {
            composeEmail(arrayOf(user.email))
        }

        detail_address.text = user.location.street?.capitalize(Locale.current)
        detail_city.text = user.location.city?.capitalize(Locale.current)
        detail_postcode.text = user.location.postcode
        detail_state.text = user.location.state?.capitalize(Locale.current)

        val res = requireActivity().applicationContext.resources.getIdentifier(String.format("country%1s", user.nat.toLowerCase(
            java.util.Locale.getDefault()
        )
        ), "drawable", requireActivity().packageName)

        if(res != 0) {
            val circularProgressNat = CircularProgressDrawable(requireActivity())
            circularProgressNat.strokeWidth = 5f
            circularProgressNat.centerRadius = 30f
            circularProgressNat.start()

            Glide
                .with(this)
                .load(res)
                .fitCenter()
                .placeholder(circularProgressNat)
                .into(detail_nationality)
        } else {
            detail_nationality_txt.text = user.nat
        }

        button_maps.setOnClickListener {
            goToMaps(String.format("%1s %2s", user.location.street, user.location.city))
        }
    }

    private fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(java.util.Locale.getDefault())
        calendar.timeInMillis = timestamp * 1000L
        return DateFormat.format("dd/MM/yyyy",calendar).toString()
    }

    private fun transformPhone(phoneNumber: String): String {
        return phoneNumber.replace("(", "00").replace(")", "").replace("-", "")
    }

    private fun call() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity().baseContext,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CALL_PHONE),
                101
            )
            return
        }
       callPhoneNumber(phoneToCall)
    }

    private fun callPhoneNumber(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse(String.format("tel:%1s", transformPhone(phoneNumber)))
        startActivity(callIntent)
    }

    private fun goToMaps(address: String) {
        val gmmIntentUri =
            Uri.parse(String.format("geo:0,0?q=%1s", address))
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun composeEmail(
        address: Array<String>
    ) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, address)
        if(intent.resolveActivity(requireActivity().packageManager) != null)
            startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 101)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                callPhoneNumber(phoneToCall)
            else
                Toast.makeText(requireContext().applicationContext, "Permission not Granted", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        fun newInstance(userId: Int): UserDetailFragment {
            val fragment = UserDetailFragment()
            val args = Bundle()
            args.putInt("userId", userId)
            fragment.arguments = args
            return fragment
        }
    }
}