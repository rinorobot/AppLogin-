package com.example.applogin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.applogin.R
import com.example.applogin.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = FirebaseAuth.getInstance()



        val btnLogin = view?.findViewById<Button>(R.id.btnLogin)

        btnLogin?.setOnClickListener {
                login()
        }

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        homeViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            login()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun login(){
        val correo: String = view?.findViewById<EditText>(R.id.etNombre)?.text.toString()
        val password: String = view?.findViewById<EditText>(R.id.etPassword)?.text.toString()


        auth.signInWithEmailAndPassword(correo,password).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(requireContext(),"Login exitoso", Toast.LENGTH_LONG).show()

            }
            else{
                Toast.makeText(requireContext(),"Error en el login"+it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        auth.signOut()
        val user: FirebaseUser? = auth.currentUser
        if (user==null){
              findNavController().navigate(R.id.action_nav_home_to_nav_slideshow)
        }  else{
             findNavController().navigate(R.id.action_nav_home_to_nav_gallery)
        }

    }


}