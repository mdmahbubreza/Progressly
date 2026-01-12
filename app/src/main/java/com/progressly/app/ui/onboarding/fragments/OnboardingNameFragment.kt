package com.progressly.app.ui.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.progressly.app.data.preferences.PreferencesManager
import com.progressly.app.databinding.FragmentOnboardingNameBinding
import com.progressly.app.ui.onboarding.OnboardingActivity

class OnboardingNameFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingNameBinding
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingNameBinding.inflate(inflater, container, false)
        preferencesManager = PreferencesManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            if (name.isNotEmpty()) {
                // Save name temporarily
                val profile = preferencesManager.getUserProfile().copy(name = name)
                preferencesManager.setUserProfile(profile)
                (activity as OnboardingActivity).nextStep()
            }
        }
    }
}
