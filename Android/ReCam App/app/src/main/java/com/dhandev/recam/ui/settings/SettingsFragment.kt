package com.dhandev.recam.ui.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.dhandev.recam.*
import com.dhandev.recam.databinding.FragmentSettingsBinding
import com.dhandev.recam.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private lateinit var sharedPred : SharedPreferences
    private lateinit var auth: FirebaseAuth
    private var kodeBahasa = 0
    private var isDarkModeOn = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pref = SettingPreferences.getInstance(requireActivity().dataStore)
        val settingsViewModel =
            ViewModelProvider(this, ViewModelFactory(pref)).get(SettingsViewModel::class.java)

        sharedPred = this.requireActivity().getSharedPreferences("User", MODE_PRIVATE)
        kodeBahasa = sharedPred.getInt("kodeBahasa", 0)
        when(kodeBahasa){
            1 -> binding.ganti.isChecked = true
            0 -> binding.ganti.isChecked = false
        }

        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }

        binding.apply {

            ganti.setOnCheckedChangeListener { switch, isChecked ->
                if (isChecked) {
                    Toast.makeText(requireContext(), "Bahasa Indonesia", Toast.LENGTH_LONG).show()
                    setLang("in", 1)

                } else {
                    Toast.makeText(requireContext(), "English", Toast.LENGTH_LONG).show()
                    setLang("en", 0)
                }
            }
            settingsViewModel.getThemeSettings().observe(viewLifecycleOwner
            ) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            }
            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                settingsViewModel.saveThemeSetting(isChecked)
            }
            keluar.setOnClickListener {
                val user = Firebase.auth.currentUser
                user?.let {
                    val name = user.displayName
                    val BottomSheetDialog = BottomSheetMaterialDialog.Builder(requireActivity())
                        .setTitle(getString(R.string.keluar))
                        .setMessage(getString(R.string.desc_logout, name))
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.keluar), R.drawable.ic_baseline_done_24){dialog, which ->
                            auth.signOut()
                            startActivity(Intent(requireContext(), LoginActivity::class.java))
                            activity?.finish() }
                        .setNegativeButton(getString(R.string.Cancel), R.drawable.ic_baseline_close_24) { dialog, which ->
                            dialog.dismiss()
                        }
                        .setAnimation("logout.json")
                        .build()
                    BottomSheetDialog.show()
                    BottomSheetDialog.animationView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                }
            }
        }

        return root
    }

    private fun setLang(Lang: String, kodeBahasa : Int) {
        sharedPred = this.requireActivity().getSharedPreferences("User", MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPred.edit()
        editor.putInt("kodeBahasa", kodeBahasa)
        editor.putString("Bahasa", Lang)
        editor.apply()

        val config = resources.configuration
        val locale = Locale(Lang)

        Locale.setDefault(locale)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        requireActivity().recreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}