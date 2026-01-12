package com.progressly.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.progressly.app.R
import com.progressly.app.databinding.FragmentProfileBinding
import com.progressly.app.ui.MainActivity
import com.progressly.app.ui.viewmodels.ProfileViewModel
import com.progressly.app.ui.viewmodels.ProfileViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferencesManager = (activity as MainActivity).getPreferencesManager()
        val factory = ProfileViewModelFactory(
            preferencesManager,
            (activity as MainActivity).getTaskRepository(),
            (activity as MainActivity).getMealRepository(),
            (activity as MainActivity).getWaterRepository(),
            (activity as MainActivity).getHabitRepository()
        )
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        val userName = viewModel.getUserName()
        binding.tvUserName.text = "Hello, $userName! 👋"
    }

    private fun setupObservers() {
        viewModel.dailySummary.observe(viewLifecycleOwner) { summary ->
            binding.tvTasksCompleted.text = "${summary.tasksCompleted} Tasks"
            binding.tvMealsLogged.text = "${summary.mealsLogged} Meals"
            binding.tvWaterIntake.text = "${summary.waterIntake} ml Water"
            binding.tvHabitsCompleted.text = "${summary.habitsCompleted} Habits"
            binding.tvCaloriesCount.text = "${summary.totalCalories} kcal"
        }

        viewModel.getTaskStreaks().observe(viewLifecycleOwner) { streak ->
            if (streak != null) {
                binding.tvTaskStreak.text = "${streak.currentStreak} days"
            }
        }

        viewModel.getWaterStreaks().observe(viewLifecycleOwner) { streak ->
            if (streak != null) {
                binding.tvWaterStreak.text = "${streak.currentStreak} days"
            }
        }

        viewModel.getMealStreaks().observe(viewLifecycleOwner) { streak ->
            if (streak != null) {
                binding.tvMealStreak.text = "${streak.currentStreak} days"
            }
        }

        viewModel.getHabitStreaks().observe(viewLifecycleOwner) { streaks ->
            var totalHabitStreak = 0
            streaks.forEach { streak ->
                totalHabitStreak += streak.currentStreak
            }
            binding.tvHabitStreak.text = "$totalHabitStreak days combined"
        }

        viewModel.loadDailySummary()
    }
}
