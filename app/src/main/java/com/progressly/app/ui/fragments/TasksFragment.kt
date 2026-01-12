package com.progressly.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.progressly.app.R
import com.progressly.app.data.database.entities.TaskEntity
import com.progressly.app.databinding.FragmentTasksBinding
import com.progressly.app.ui.MainActivity
import com.progressly.app.ui.adapters.TaskAdapter
import com.progressly.app.ui.viewmodels.TaskViewModel
import com.progressly.app.ui.viewmodels.TaskViewModelFactory
import java.time.LocalDate

class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (activity as MainActivity).getTaskRepository()
        val factory = TaskViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(
            onTaskCompleted = { task ->
                viewModel.completeTask(task)
            },
            onTaskDeleted = { task ->
                viewModel.deleteTask(task)
            }
        )
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getTasksByDate(LocalDate.now()).observe(viewLifecycleOwner) { tasks ->
            if (tasks.isEmpty()) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.rvTasks.visibility = View.GONE
            } else {
                binding.tvEmptyState.visibility = View.GONE
                binding.rvTasks.visibility = View.VISIBLE
                adapter.submitList(tasks)
            }
        }

        viewModel.getCompletedTasksCount(LocalDate.now()).observe(viewLifecycleOwner) { count ->
            binding.tvCompletedCount.text = "$count Tasks Done Today"
        }
    }

    private fun setupListeners() {
        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val title = binding.etTaskName.text.toString().trim()
        if (title.isNotEmpty()) {
            viewModel.addTask(title)
            binding.etTaskName.text.clear()
        }
    }
}
