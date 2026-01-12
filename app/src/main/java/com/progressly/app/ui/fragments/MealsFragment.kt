package com.progressly.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.progressly.app.R
import com.progressly.app.databinding.FragmentMealsBinding
import com.progressly.app.ui.MainActivity
import com.progressly.app.ui.adapters.MealAdapter
import com.progressly.app.ui.viewmodels.MealViewModel
import com.progressly.app.ui.viewmodels.MealViewModelFactory
import java.time.LocalDate

class MealsFragment : Fragment() {

    private lateinit var binding: FragmentMealsBinding
    private lateinit var viewModel: MealViewModel
    private lateinit var adapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (activity as MainActivity).getMealRepository()
        val factory = MealViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MealViewModel::class.java]

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = MealAdapter(
            onMealDeleted = { meal ->
                viewModel.deleteMeal(meal)
            }
        )
        binding.rvMeals.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMeals.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getMealsByDate(LocalDate.now()).observe(viewLifecycleOwner) { meals ->
            if (meals.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvMeals.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvMeals.visibility = View.VISIBLE
                adapter.submitList(meals)
            }
        }

        viewModel.getTotalCaloriesByDate(LocalDate.now()).observe(viewLifecycleOwner) { calories ->
            binding.tvCaloriesCount.text = "${calories ?: 0} Calories Today"
        }

        viewModel.getMealsCountByDate(LocalDate.now()).observe(viewLifecycleOwner) { count ->
            binding.tvMealsCount.text = "$count Meals Logged"
        }
    }

    private fun setupListeners() {
        binding.fabAddMeal.setOnClickListener {
            showAddMealDialog()
        }
    }

    private fun showAddMealDialog() {
        val name = binding.etMealName.text.toString().trim()
        val calories = binding.etCalories.text.toString().toIntOrNull() ?: 0
        
        if (name.isNotEmpty()) {
            viewModel.addMeal(name, calories)
            binding.etMealName.text.clear()
            binding.etCalories.text.clear()
        }
    }
}
