package com.codelabs.southsystem.eventos.features.events.presentation.detail

import android.content.Context
import android.view.View
import com.codelabs.southsystem.eventos.R
import com.codelabs.southsystem.eventos.databinding.FormCheckInBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class CheckInBottomSheetDialog(
    context: Context,
    private val onConfirmCheckIn: (String, String) -> Unit
) : BottomSheetDialog(context) {
    private val binding = FormCheckInBinding.inflate(layoutInflater)

    init {
        setContentView(binding.root)
        binding.btnConfirmCheckIn.setOnClickListener(::onConfirmCheckIn)
    }

    private fun onConfirmCheckIn(view: View) {
        if (binding.etName.text?.isBlank() == true) {
            binding.etName.error = context.getString(R.string.cannot_be_blank)
            return
        }

        if (binding.etEmail.text?.isBlank() == true) {
            binding.etEmail.error = context.getString(R.string.cannot_be_blank)
            return
        }

        onConfirmCheckIn(
            binding.etName.text.toString().trim(),
            binding.etEmail.text.toString().trim()
        )
    }

    fun setProgressState() {
        setCancelable(false)

        binding.etName.isEnabled = false
        binding.etEmail.isEnabled = false
        binding.btnConfirmCheckIn.apply {
            isEnabled = false
            text = context.getString(R.string.sending)
        }
    }

    fun setErrorState(message: String?) {
        setCancelable(true)

        binding.etName.isEnabled = true
        binding.etEmail.apply {
            isEnabled = true
            error = message ?: context.getString(R.string.invalid)
        }
        binding.btnConfirmCheckIn.apply {
            isEnabled = true
            text = context.getString(R.string.confirm_check_in)
        }
    }
}