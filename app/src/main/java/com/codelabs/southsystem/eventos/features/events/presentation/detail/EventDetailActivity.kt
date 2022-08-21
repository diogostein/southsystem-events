package com.codelabs.southsystem.eventos.features.events.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.codelabs.southsystem.eventos.R
import com.codelabs.southsystem.eventos.core.UiState
import com.codelabs.southsystem.eventos.core.helpers.GlideHelper
import com.codelabs.southsystem.eventos.core.utils.format
import com.codelabs.southsystem.eventos.core.utils.toBrazilianCurrency
import com.codelabs.southsystem.eventos.databinding.ActivityEventDetailBinding
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventDetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<EventDetailViewModel>()

    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            setSupportActionBar(it.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                is UiState.Loading ->
                    binding.contentStateView.showProgressIndicator()
                is UiState.Error ->
                    binding.contentStateView.showError(state.message)
                is UiState.Success ->
                    buildContent(state.value!!)
                else -> {}
            }
        }

        if (savedInstanceState == null) {
            viewModel.getEventDetail(intent.getStringExtra("id")!!)
        }
    }

    private fun buildContent(event: Event) {
        binding.collapsingToolbar.title = getString(R.string.event)

        GlideHelper.load(event.image, binding.ivCollapsing)

        binding.tvTitle.text = event.title
        binding.tvDescription.text = event.description
        binding.tvDate.text = event.date.format()
        binding.tvPrice.text = event.price.toBrazilianCurrency()

        binding.contentStateView.showContent()
    }

//    private fun showProgressIndicator() {
//        binding.includeProgress.progressBar.visibility = View.VISIBLE
//        binding.includeError.viewGroupError.visibility = View.GONE
//        binding.includeContent.contentEventDetail.visibility = View.GONE
//    }
//
//    private fun showError() {
//        binding.includeProgress.progressBar.visibility = View.GONE
//        binding.includeError.viewGroupError.visibility = View.VISIBLE
//        binding.includeContent.contentEventDetail.visibility = View.GONE
//    }
//
//    private fun showProgressIndicator() {
//        binding.includeProgress.progressBar.visibility = View.VISIBLE
//        binding.includeError.viewGroupError.visibility = View.GONE
//        binding.includeContent.contentEventDetail.visibility = View.GONE
//    }
}