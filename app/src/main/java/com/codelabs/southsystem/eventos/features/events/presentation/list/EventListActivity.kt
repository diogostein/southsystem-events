package com.codelabs.southsystem.eventos.features.events.presentation.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.codelabs.southsystem.eventos.R
import com.codelabs.southsystem.eventos.core.UiState
import com.codelabs.southsystem.eventos.databinding.ActivityEventListBinding
import com.codelabs.southsystem.eventos.features.events.domain.entities.Event
import com.codelabs.southsystem.eventos.features.events.presentation.detail.EventDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventListActivity : AppCompatActivity() {
    private val viewModel by viewModels<EventListViewModel>()

    private lateinit var binding: ActivityEventListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        title = getString(R.string.events)

        viewModel.state.observe(this) { state ->
            when (state) {
                is UiState.Loading ->
                    binding.listStateView.showProgressIndicator()
                is UiState.Error ->
                    binding.listStateView.showError(state.message)
                is UiState.Success ->
                    binding.listStateView.showRecyclerView(EventListAdapter(state.value!!, ::onItemClick))
                else -> {}
            }
        }
    }

    private fun onItemClick(event: Event) {
        Intent(this, EventDetailActivity::class.java).let { intent ->
            intent.putExtra("id", event.id)
            startActivity(intent)
        }
    }
}