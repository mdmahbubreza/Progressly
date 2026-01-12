package com.progressly.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.progressly.app.R
import com.progressly.app.databinding.FragmentWaterBinding
import com.progressly.app.ui.MainActivity
import com.progressly.app.ui.adapters.WaterLogAdapter
import com.progressly.app.ui.viewmodels.WaterViewModel
import com.progressly.app.ui.viewmodels.WaterViewModelFactory
import java.time.LocalDate

class WaterFragment : Fragment() {

    private lateinit var binding: FragmentWaterBinding
    private lateinit var viewModel: WaterViewModel
    private lateinit var adapter: WaterLogAdapter
    private var dailyGoal = 2000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (activity as MainActivity).getWaterRepository()
        val factory = WaterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[WaterViewModel::class.java]

        dailyGoal = (activity as MainActivity).getPreferencesManager().getDailyWaterGoal()

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = WaterLogAdapter(
            onWaterDeleted = { log ->
                viewModel.deleteWaterLog(log)
            }
        )
        binding.rvWaterLogs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWaterLogs.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getWaterLogsByDate(LocalDate.now()).observe(viewLifecycleOwner) { logs ->
            adapter.submitList(logs)
        }

        viewModel.getTotalWaterByDate(LocalDate.now()).observe(viewLifecycleOwner) { total ->
            val waterAmount = total ?: 0
            binding.tvWaterAmount.text = "$waterAmount/$dailyGoal ml"
            
            val progress = (waterAmount.toFloat() / dailyGoal * 100).toInt().coerceIn(0, 100)
            binding.pbWaterGoal.progress = progress
        }

        viewModel.getLatestWaterStreak().observe(viewLifecycleOwner) { streak ->
            if (streak != null) {
                binding.tvDailyStreak.text = "Current Streak: ${streak.currentStreak} days"
                binding.tvLongestStreak.text = "Longest: ${streak.longestStreak} days"
            }
        }
    }

    private fun setupListeners() {
        binding.btnLog250.setOnClickListener { viewModel.logWater(250) }
        binding.btnLog500.setOnClickListener { viewModel.logWater(500) }
        binding.btnLog750.setOnClickListener { viewModel.logWater(750) }
        
        binding.fabAddWater.setOnClickListener {
            val amount = binding.etWaterAmount.text.toString().toIntOrNull() ?: 0
            if (amount > 0) {
                viewModel.logWater(amount)
                binding.etWaterAmount.text.clear()
            }
        }
    }
}
