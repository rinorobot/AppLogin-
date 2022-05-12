package com.example.applogin.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.applogin.R
import com.example.applogin.databinding.FragmentGalleryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    private val db = FirebaseFirestore.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)
        auth = FirebaseAuth.getInstance()

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root


        galleryViewModel.text.observe(viewLifecycleOwner) {

        }

        val correo = auth.currentUser?.email.toString()



        db.collection("users").document(correo).get().addOnSuccessListener {
            binding.tvNombreBienvenido.text = it.get("nombre") as String?
            binding.tvCorreoBienvenido.text = it.get("correo") as String?
            binding.tvApellidoBienvenido.text = it.get("apellido") as String?
        }



        binding.btnCerrar.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            auth.signOut()
            Toast.makeText(requireContext(),"Se ha cerrado la sesión.",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_nav_gallery_to_nav_home)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}