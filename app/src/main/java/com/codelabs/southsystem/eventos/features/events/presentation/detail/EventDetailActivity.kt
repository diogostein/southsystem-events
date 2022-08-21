package com.codelabs.southsystem.eventos.features.events.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.codelabs.southsystem.eventos.R
import com.codelabs.southsystem.eventos.core.UiState
import com.codelabs.southsystem.eventos.core.helpers.GlideHelper
import com.codelabs.southsystem.eventos.core.utils.format
import com.codelabs.southsystem.eventos.core.utils.toBrazilianCurrency
import com.codelabs.southsystem.eventos.databinding.ActivityEventDetailBinding
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventDetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<EventDetailViewModel>()

    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var eventId: String

    private var bottomSheetDialog: CheckInBottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            setSupportActionBar(it.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        eventId = intent.getStringExtra("id")!!

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

        viewModel.checkInState.observe(this) { state ->
            when (state) {
                is UiState.Loading ->
                    bottomSheetDialog?.setProgressState()
                is UiState.Error ->
                    bottomSheetDialog?.setErrorState(state.message)
                is UiState.Success ->
                    onCheckInPerformed()
                else -> {}
            }
        }

        viewModel.eventAddress.observe(this) { eventAddress ->
            binding.tvAddress.apply {
                text = eventAddress.formattedAddress
                visibility = View.VISIBLE
            }
        }

        binding.contentStateView.setOnRetryClickListener { viewModel.getEventDetail(eventId) }

        if (savedInstanceState == null) {
            viewModel.getEventDetail(eventId)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun buildContent(event: Event) {
        binding.contentStateView.showContent {
            binding.collapsingToolbar.title = getString(R.string.event)

            GlideHelper.load(event.image, binding.ivCollapsing)

            binding.tvTitle.text = event.title
            binding.tvDescription.text = event.description
            binding.tvDate.text = event.date.format()
            binding.tvPrice.text = event.price.toBrazilianCurrency()
            binding.btnCheckIn.setOnClickListener(::onCheckInClicked)
            binding.btnShare.setOnClickListener { performShare(event) }

            viewModel.getEventAddress(event.latitude, event.longitude)
        }
    }

    private fun onCheckInClicked(view: View) {
        bottomSheetDialog = CheckInBottomSheetDialog(this) { name, email ->
            viewModel.performCheckIn(eventId, name, email)
        }.also { it.show() }
    }

    private fun onCheckInPerformed() {
        bottomSheetDialog?.dismiss()
        binding.btnCheckIn.isEnabled = false
        Snackbar.make(binding.root,
            getString(R.string.checked_in_successfully), Snackbar.LENGTH_LONG).show()
    }

    private fun performShare(event: Event) {
        val sendIntent = Intent().apply {
            val presenter = EventSharePresenter(event, viewModel.eventAddress.value)
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, presenter.subject)
            putExtra(Intent.EXTRA_TEXT, presenter.text)
            type = "text/plain"
        }

        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(sendIntent, null))
        } else {
            Snackbar.make(binding.root, getString(R.string.no_app_found), Snackbar.LENGTH_LONG).show()
        }
    }

}