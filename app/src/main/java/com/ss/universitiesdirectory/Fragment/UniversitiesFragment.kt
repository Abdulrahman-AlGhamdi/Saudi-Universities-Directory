package com.ss.universitiesdirectory.Fragment

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import com.ss.universitiesdirectory.Model.UniversitiesModel
import com.ss.universitiesdirectory.R
import com.ss.universitiesdirectory.databinding.FragmentUniversitiesBinding

class UniversitiesFragment : Fragment() {

    private var _binding: FragmentUniversitiesBinding? = null
    private val binding get() = _binding!!
    private lateinit var university: Array<String>
    private lateinit var reference: DatabaseReference
    private lateinit var vectorDrawable: AnimatedVectorDrawable
    private lateinit var animation: Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUniversitiesBinding.inflate(inflater, container, false)

        init()
        animations()
        universityButtons()

        return binding.root
    }

    private fun init() {
        reference = FirebaseDatabase.getInstance().reference
        animation = AnimationUtils.loadAnimation(requireContext(), R.anim.appear_animation)
        binding.universityLogo.animation = animation
        university = arrayOf("Saud", "Sattam", "Nora", "Imam", "Qassim", "Hail", "Shaqra",
                "Majmaah", "SaudiElectronic", "SaudHealth", "Jeddah", "Abdulaziz", "UmmAlQura",
                "Taif", "Taibah", "Islamic", "Fahd", "Faisal", "ImamAbdulrahman", "HafrBatin",
                "Jouf", "Tabuk", "NorthernBorders", "Albaha", "Najran", "Khalid", "Jazan", "Bisha")
        binding.centreUniversitiesScrollview.postDelayed({
            binding.centreUniversitiesScrollview.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        }, 100L)
        binding.westUniversitiesScrollview.postDelayed({
            binding.westUniversitiesScrollview.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        }, 100L)
        binding.eastUniversitiesScrollview.postDelayed({
            binding.eastUniversitiesScrollview.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        }, 100L)
        binding.northUniversitiesScrollview.postDelayed({
            binding.northUniversitiesScrollview.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        }, 100L)
        binding.southUniversitiesScrollview.postDelayed({
            binding.southUniversitiesScrollview.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        }, 100L)
    }

    private fun animations() {
        vectorDrawable = binding.universityLogo.drawable as AnimatedVectorDrawable
        vectorDrawable.start()
        vectorDrawable.registerAnimationCallback(object : Animatable2.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable) {
                vectorDrawable.start()
            }
        })
    }

    private fun universityButtons() {
        binding.saudUniversity.setOnClickListener {
            databaseManagement(university[0])
        }
        binding.sattamUniversity.setOnClickListener {
            databaseManagement(university[1])
        }
        binding.noraUniversity.setOnClickListener {
            databaseManagement(university[2])
        }
        binding.imamUniversity.setOnClickListener {
            databaseManagement(university[3])
        }
        binding.qassimUniversity.setOnClickListener {
            databaseManagement(university[4])
        }
        binding.hailUniversity.setOnClickListener {
            databaseManagement(university[5])
        }
        binding.shaqraUniversity.setOnClickListener {
            databaseManagement(university[6])
        }
        binding.majmaahUniversity.setOnClickListener {
            databaseManagement(university[7])
        }
        binding.saudiElectronicUniversity.setOnClickListener {
            databaseManagement(university[8])
        }
        binding.saudHealthUniversity.setOnClickListener {
            databaseManagement(university[9])
        }
        binding.jeddahUniversity.setOnClickListener {
            databaseManagement(university[10])
        }
        binding.abdulazizUniversity.setOnClickListener {
            databaseManagement(university[11])
        }
        binding.alquraUniversity.setOnClickListener {
            databaseManagement(university[12])
        }
        binding.taifUniversity.setOnClickListener {
            databaseManagement(university[13])
        }
        binding.taibahUniversity.setOnClickListener {
            databaseManagement(university[14])
        }
        binding.islamicUniversity.setOnClickListener {
            databaseManagement(university[15])
        }
        binding.fahdUniversity.setOnClickListener {
            databaseManagement(university[16])
        }
        binding.faisalUniversity.setOnClickListener {
            databaseManagement(university[17])
        }
        binding.imamAbdulrahmanUniversity.setOnClickListener {
            databaseManagement(university[18])
        }
        binding.hafrBatinUniversity.setOnClickListener {
            databaseManagement(university[19])
        }
        binding.joufUniversity.setOnClickListener {
            databaseManagement(university[20])
        }
        binding.tabukUniversity.setOnClickListener {
            databaseManagement(university[21])
        }
        binding.northUniversitiesScrollview.setOnClickListener {
            databaseManagement(university[22])
        }
        binding.albahaUniversity.setOnClickListener {
            databaseManagement(university[23])
        }
        binding.najranUniversity.setOnClickListener {
            databaseManagement(university[24])
        }
        binding.khalidUniversity.setOnClickListener {
            databaseManagement(university[25])
        }
        binding.jazanUniversity.setOnClickListener {
            databaseManagement(university[26])
        }
        binding.bishaUniversity.setOnClickListener {
            databaseManagement(university[27])
        }
    }

    private fun databaseManagement(model: String) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                findNavController().navigate(
                        UniversitiesFragmentDirections
                                .actionUniversitiesFragment3ToDetailsFragment(
                                        snapshot.child(model + "University")
                                                .getValue(UniversitiesModel::class.java)!!
                                )
                )
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}