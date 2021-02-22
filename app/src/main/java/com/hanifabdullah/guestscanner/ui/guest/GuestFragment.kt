package com.hanifabdullah.guestscanner.ui.guest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hanifabdullah.data.model.StatusResponse
import com.hanifabdullah.data.ui.DialogMessage
import com.hanifabdullah.data.ui.DialogMessageListener
import com.hanifabdullah.guestscanner.R
import com.hanifabdullah.guestscanner.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_guest.*

class GuestFragment : BaseFragment() {

    lateinit var viewModelFactory: GuestViewModelFactory
    lateinit var viewModel: GuestViewModel

    lateinit var adapter: GuestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = GuestViewModelFactory(activity?.application!!, repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GuestViewModel::class.java)

        setupObserver()

        viewModel.getAllGuest()
    }

    private fun setupObserver() {
        viewModel.guest.observe(viewLifecycleOwner, Observer {
            when(it.statusRequest){
                StatusResponse.LOADING -> loading.show()
                StatusResponse.EMPTY -> {
                    loading.dismiss()

                    DialogMessage(requireContext())
                        .setTitle("Ups...")
                        .setMessage("Daftar Tamu Anda Masih Kosong")
                        .showDialog()
                }
                StatusResponse.SUCCESS -> {
                    loading.dismiss()
                    adapter = GuestAdapter(it.data ?: ArrayList())

                    guest_rv.layoutManager = LinearLayoutManager(requireContext())
                    guest_rv.adapter = adapter

                    guest_edt_filter.addTextChangedListener(object : TextWatcher{
                        override fun afterTextChanged(s: Editable?) {
                            adapter.filter.filter(s.toString())
                        }

                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {

                        }

                    })
                }
                StatusResponse.ERROR -> {
                    loading.dismiss()

                    DialogMessage(requireContext())
                        .setTitle("Ups ...")
                        .setMessage(it.failureModel?.msgShow)
                        .setTextButtonPrimary("Ulangi")
                        .setTextButtonSecondary("Batal")
                        .setListenerButtonPrimary(object : DialogMessageListener{
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                                viewModel.getAllGuest()
                            }
                        })
                        .setListenerButtonSecondary(object : DialogMessageListener{
                            override fun onClick(dialogMessage: DialogMessage) {
                                dialogMessage.dismiss()
                            }
                        }).showDialog()
                }
            }
        })
    }
}