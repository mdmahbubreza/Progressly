package com.progressly.app.ui.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.progressly.app.data.preferences.PreferencesManager
import com.progressly.app.databinding.FragmentOnboardingGenderBinding
import com.progressly.app.ui.onboarding.OnboardingActivity

class OnboardingGenderFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingGenderBinding
    private lateinit var preferencesManager: PreferencesManager
    private var selectedGender = "Male"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingGenderBinding.inflate(inflater, container, false)
        preferencesManager = PreferencesManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rbMale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) selectedGender = "Male"
        }

        binding.rbFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) selectedGender = "Female"
        }

        binding.rbOther.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) selectedGender = "Other"
        }

        binding.btnNext.setOnClickListener {
            val profile = preferencesManager.getUserProfile().copy(gender = selectedGender)
            preferencesManager.setUserProfile(profile)
            (activity as OnboardingActivity).nextStep()
        }

        binding.btnPrevious.setOnClickListener {
            (activity as OnboardingActivity).previousStep()
        }
    }
}
