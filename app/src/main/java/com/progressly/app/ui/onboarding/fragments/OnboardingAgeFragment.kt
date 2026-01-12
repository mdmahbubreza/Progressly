package com.progressly.app.ui.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.progressly.app.data.preferences.PreferencesManager
import com.progressly.app.databinding.FragmentOnboardingAgeBinding
import com.progressly.app.ui.onboarding.OnboardingActivity

class OnboardingAgeFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingAgeBinding
    private lateinit var preferencesManager: PreferencesManager
    private var selectedAge = 25

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingAgeBinding.inflate(inflater, container, false)
        preferencesManager = PreferencesManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seekBarAge.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedAge = progress + 15
                binding.tvAgeDisplay.text = "$selectedAge"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.btnNext.setOnClickListener {
            val profile = preferencesManager.getUserProfile().copy(age = selectedAge)
            preferencesManager.setUserProfile(profile)
            (activity as OnboardingActivity).nextStep()
        }

        binding.btnPrevious.setOnClickListener {
            (activity as OnboardingActivity).previousStep()
        }
    }
}
