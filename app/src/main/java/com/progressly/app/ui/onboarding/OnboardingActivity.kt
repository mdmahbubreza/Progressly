package com.progressly.app.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.progressly.app.R
import com.progressly.app.databinding.ActivityOnboardingBinding
import com.progressly.app.ui.MainActivity
import com.progressly.app.ui.onboarding.fragments.OnboardingNameFragment
import com.progressly.app.ui.onboarding.fragments.OnboardingAgeFragment
import com.progressly.app.ui.onboarding.fragments.OnboardingGenderFragment
import com.progressly.app.ui.onboarding.fragments.OnboardingWeightFragment
import com.progressly.app.ui.onboarding.fragments.OnboardingNotificationsFragment

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private var currentStep = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            showCurrentStep()
        }
    }

    private fun showCurrentStep() {
        val fragment: Fragment = when (currentStep) {
            0 -> OnboardingNameFragment()
            1 -> OnboardingAgeFragment()
            2 -> OnboardingGenderFragment()
            3 -> OnboardingWeightFragment()
            4 -> OnboardingNotificationsFragment()
            else -> OnboardingNameFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun nextStep() {
        if (currentStep < 4) {
            currentStep++
            showCurrentStep()
        } else {
            completeOnboarding()
        }
    }

    fun previousStep() {
        if (currentStep > 0) {
            currentStep--
            showCurrentStep()
        }
    }

    private fun completeOnboarding() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
