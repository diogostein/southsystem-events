package com.codelabs.southsystem.eventos.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.codelabs.southsystem.eventos.R
import com.codelabs.southsystem.eventos.databinding.WidgetContentStateViewBinding

class ContentStateView constructor(
        context: Context,
        attrs: AttributeSet?,
) : FrameLayout(context, attrs) {
    private val binding = WidgetContentStateViewBinding
        .inflate(LayoutInflater.from(context), this)

    val viewGroupContent get() = binding.viewGroupContent
    val includeError get() = binding.includeError
    val includeProgress get() = binding.includeProgress

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        when (child.id) {
            R.id.includeProgress,
            R.id.includeError,
            R.id.viewGroupContent ->
                super.addView(child, index, params)
            else ->
                binding.viewGroupContent.addView(child, index, params)
        }
    }

    fun setOnRetryClickListener(onRetryClickListener: () -> Unit) {
        binding.includeError.btnReload.setOnClickListener { onRetryClickListener() }
    }

    fun showContent(content: () -> Unit) {
        content()
        binding.viewGroupContent.visibility = VISIBLE
        binding.includeProgress.progressBar.visibility = GONE
        setErrorVisibility(GONE)
    }

    fun showProgressIndicator() {
        binding.viewGroupContent.visibility = GONE
        binding.includeProgress.progressBar.visibility = VISIBLE
        setErrorVisibility(GONE)
    }

    fun showError(message: String? = null) {
        binding.viewGroupContent.visibility = GONE
        binding.includeProgress.progressBar.visibility = GONE
        setErrorVisibility(VISIBLE, message)
    }

    private fun setErrorVisibility(visibility: Int, message: String? = null) {
        binding.includeError.apply {
            viewGroupError.visibility = visibility
            tvMessage.text = message
        }
    }
}