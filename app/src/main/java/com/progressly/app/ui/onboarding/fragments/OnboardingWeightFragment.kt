package com.progressly.app.ui.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.progressly.app.data.preferences.PreferencesManager
import com.progressly.app.databinding.FragmentOnboardingWeightBinding
import com.progressly.app.ui.onboarding.OnboardingActivity

class OnboardingWeightFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingWeightBinding
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingWeightBinding.inflate(inflater, container, false)
        preferencesManager = PreferencesManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            val weight = binding.etWeight.text.toString().trim().toIntOrNull() ?: 0
            val profile = preferencesManager.getUserProfile().copy(weight = weight)
            preferencesManager.setUserProfile(profile)

            // Calculate daily water goal
            if (weight > 0) {
                val dailyGoal = preferencesManager.calculateDailyWaterGoal(
                    profile.age,
                    profile.gender,
                    weight
                )
                preferencesManager.setDailyWaterGoal(dailyGoal)
            }

            (activity as OnboardingActivity).nextStep()
        }

        binding.btnPrevious.setOnClickListener {
            (activity as OnboardingActivity).previousStep()
        }

        binding.btnSkip.setOnClickListener {
            (activity as OnboardingActivity).nextStep()
        }
    }
}
