package com.ss.universitiesdirectory.ui.theme

import android.app.AlertDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentThemeBinding

class ThemeFragment : DialogFragment(R.layout.fragment_theme) {

    private lateinit var binding: FragmentThemeBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentThemeBinding.inflate(LayoutInflater.from(requireContext()))

        val builder = AlertDialog.Builder(requireContext()).apply {
            this.setView(binding.root)
            this.setTitle(R.string.preference_theme_title)
        }

        init()

        return builder.create()
    }

    private fun init() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        if (sharedPreferences.getBoolean("theme", false))
            binding.dark.isChecked = true else binding.light.isChecked = true

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.dark.id  -> setTheme(radioButton = binding.dark, darkMode = true)
                binding.light.id -> setTheme(radioButton = binding.light, darkMode = false)
            }
        }
    }

    private fun setTheme(radioButton: RadioButton, darkMode: Boolean) {
        radioButton.isChecked = true

        sharedPreferences.edit().putBoolean("theme", darkMode).apply()

        if (darkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        dismiss()
    }
}