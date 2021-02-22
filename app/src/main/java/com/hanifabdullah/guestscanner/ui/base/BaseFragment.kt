package com.hanifabdullah.guestscanner.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hanifabdullah.data.helper.OnAuthorizationFailed
import com.hanifabdullah.data.local.SharedPrefManager
import com.hanifabdullah.data.repository.RemoteRepository
import com.hanifabdullah.data.ui.DialogLoading
import com.hanifabdullah.guestscanner.model.Repository
import com.hanifabdullah.guestscanner.ui.main.MainActivity

abstract class BaseFragment : Fragment() {

    lateinit var pref: SharedPrefManager
    lateinit var remoteRepository: RemoteRepository
    lateinit var repository: Repository

    lateinit var loading: DialogLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pref = SharedPrefManager(requireContext())
        remoteRepository = RemoteRepository(requireContext(), object : OnAuthorizationFailed {
            override fun onAuthorizationFailed() {
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                activity?.finish()
            }
        })
        repository = Repository(pref, remoteRepository)

        loading = DialogLoading(requireContext())
    }

}