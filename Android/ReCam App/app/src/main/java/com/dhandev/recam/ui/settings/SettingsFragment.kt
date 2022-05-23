package com.dhandev.recam.ui.settings

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.dhandev.recam.MainActivity
import com.dhandev.recam.R
import com.dhandev.recam.TokenPreference
import com.dhandev.recam.databinding.FragmentSettingsBinding
import com.dhandev.recam.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import java.util.*

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private lateinit var sharedPred : SharedPreferences
    private lateinit var auth: FirebaseAuth
    private var kodeBahasa = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

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
            keluar.setOnClickListener {
                val user = Firebase.auth.currentUser
                user?.let {
                    val name = user.displayName
                    val BottomSheetDialog = BottomSheetMaterialDialog.Builder(requireActivity())
                        .setTitle("Keluar?")
                        .setMessage("$name, Kamu yakin mau keluar?")
                        .setCancelable(true)
                        .setPositiveButton("Keluar", R.drawable.ic_baseline_done_24){dialog, which ->
                            auth.signOut()
                            startActivity(Intent(requireContext(), LoginActivity::class.java))
                            activity?.finish() }
                        .setNegativeButton("Batal", R.drawable.ic_baseline_close_24) { dialog, which ->
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