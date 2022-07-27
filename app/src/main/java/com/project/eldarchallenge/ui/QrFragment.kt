package com.project.eldarchallenge.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.eldarchallenge.R
import com.project.eldarchallenge.databinding.FragmentQrBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QrFragment : Fragment() {
    private lateinit var binding: FragmentQrBinding
    private val qrViewModel by viewModels<QrViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.spinnerLoading.visibility = View.VISIBLE

        qrViewModel.qrResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.btnRetry.visibility = View.GONE
                binding.spinnerLoading.visibility = View.GONE
                // read bytes from service response and set QR image
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                binding.ivQr.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        bmp,
                        binding.ivQr.width,
                        binding.ivQr.height,
                        false
                    )
                )
            }
        }

        qrViewModel.qrErrorResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.spinnerLoading.visibility = View.GONE
                binding.tvEmptyState.text = getString(R.string.qr_fragment_empty_state)
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.btnRetry.visibility = View.VISIBLE
                binding.btnRetry.setOnClickListener {
                    qrViewModel.getQrResponse(userData())
                    binding.spinnerLoading.visibility = View.VISIBLE
                    binding.tvEmptyState.visibility = View.GONE
                    binding.btnRetry.visibility = View.GONE
                }
            }
        }

        qrViewModel.getQrResponse(userData())
    }

    private fun userData(): String {
        val sharedPref = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val name = sharedPref?.getString("name", "")
        val lastName = sharedPref?.getString("lastName", "")
        return name + lastName
    }
}