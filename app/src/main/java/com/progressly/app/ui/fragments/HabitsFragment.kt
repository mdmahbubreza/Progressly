package com.progressly.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.progressly.app.R
import com.progressly.app.databinding.FragmentHabitsBinding
import com.progressly.app.ui.MainActivity
import com.progressly.app.ui.adapters.HabitAdapter
import com.progressly.app.ui.viewmodels.HabitViewModel
import com.progressly.app.ui.viewmodels.HabitViewModelFactory
import java.time.LocalDate

class HabitsFragment : Fragment() {

    private lateinit var binding: FragmentHabitsBinding
    private lateinit var viewModel: HabitViewModel
    private lateinit var adapter: HabitAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (activity as MainActivity).getHabitRepository()
        val factory = HabitViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HabitViewModel::class.java]

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = HabitAdapter(
            onHabitCompleted = { habit ->
                viewModel.completeHabit(habit.id)
            },
            onHabitDeleted = { habit ->
                viewModel.deleteHabit(habit)
            }
        )
        binding.rvHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHabits.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getAllHabits().observe(viewLifecycleOwner) { habits ->
            if (habits.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvHabits.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvHabits.visibility = View.VISIBLE
                adapter.submitList(habits)
            }
        }

        viewModel.getCompletedHabitsCountByDate(LocalDate.now()).observe(viewLifecycleOwner) { count ->
            binding.tvCompletedCount.text = "$count Habits Completed Today"
        }

        viewModel.getHabitsCount().observe(viewLifecycleOwner) { count ->
            binding.tvTotalHabits.text = "Total: $count Habits"
        }
    }

    private fun setupListeners() {
        binding.fabAddHabit.setOnClickListener {
            showAddHabitDialog()
        }
    }

    private fun showAddHabitDialog() {
        val name = binding.etHabitName.text.toString().trim()
        if (name.isNotEmpty()) {
            viewModel.addHabit(name)
            binding.etHabitName.text.clear()
        }
    }
}
