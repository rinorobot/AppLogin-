package com.example.applogin.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.applogin.R
import com.example.applogin.databinding.FragmentSlideshowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = FirebaseAuth.getInstance()


        binding.btnRegresar.setOnClickListener {
            findNavController().navigate(R.id.action_nav_slideshow_to_nav_home)
        }

        slideshowViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegistrarse.setOnClickListener {
            registrarse()
        }
    }

    fun registrarse(){
        val nombre: String = view?.findViewById<EditText>(R.id.etNameRegistro)?.text.toString().trim()
        val apellido: String = view?.findViewById<EditText>(R.id.etApellidoRegistro)?.text.toString().trim()
        val correo: String = view?.findViewById<EditText>(R.id.etCorreoRegistro)?.text.toString().trim().toLowerCase()
        val password: String = view?.findViewById<EditText>(R.id.etPasswordRegistro)?.text.toString().trim()

        auth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener {
            if (it.isSuccessful){

                db.collection("users").document(correo).set(
                    hashMapOf("nombre" to nombre,"apellido" to apellido, "correo" to correo, "password" to password)
                )

                Toast.makeText(requireContext(),"Registro exitoso", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_nav_slideshow_to_nav_home)
            }
            else{
                Toast.makeText(requireContext(),"Error en el registro"+it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }



}