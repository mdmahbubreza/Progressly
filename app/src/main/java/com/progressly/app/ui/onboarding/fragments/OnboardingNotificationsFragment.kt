package com.progressly.app.ui.onboarding.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.progressly.app.data.preferences.PreferencesManager
import com.progressly.app.databinding.FragmentOnboardingNotificationsBinding
import com.progressly.app.ui.onboarding.OnboardingActivity

class OnboardingNotificationsFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingNotificationsBinding
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingNotificationsBinding.inflate(inflater, container, false)
        preferencesManager = PreferencesManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEnable.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        NOTIFICATION_PERMISSION_CODE
                    )
                } else {
                    savePreferencesAndContinue(true)
                }
            } else {
                savePreferencesAndContinue(true)
            }
        }

        binding.btnSkip.setOnClickListener {
            savePreferencesAndContinue(false)
        }

        binding.btnPrevious.setOnClickListener {
            (activity as OnboardingActivity).previousStep()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            val granted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            savePreferencesAndContinue(granted)
        }
    }

    private fun savePreferencesAndContinue(enabled: Boolean) {
        val profile = preferencesManager.getUserProfile().copy(notificationsEnabled = enabled)
        preferencesManager.setUserProfile(profile)
        preferencesManager.setOnboardingCompleted(true)
        (activity as OnboardingActivity).nextStep()
    }

    companion object {
        private const val NOTIFICATION_PERMISSION_CODE = 101
    }
}
