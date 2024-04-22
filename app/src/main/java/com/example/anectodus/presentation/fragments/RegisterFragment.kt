package com.example.anectodus.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.anectodus.R
import com.example.anectodus.databinding.FragmentAuthorizationBinding
import com.example.anectodus.databinding.FragmentRegisterBinding
import com.example.anectodus.presentation.JokeApp
import com.example.anectodus.presentation.viewModels.AuthorizationViewModel
import com.example.anectodus.presentation.viewModels.RegistrationViewModel
import com.example.anectodus.presentation.viewModels.states.AuthResult
import com.example.anectodus.presentation.viewModels.viewModelFactory.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject


class RegisterFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as JokeApp).component
    }

    private var _binding: FragmentRegisterBinding? = null
    protected val binding get() = _binding ?: throw RuntimeException("")

    lateinit var viewModel: RegistrationViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputList = listOf(
            binding.signUpEmail,
            binding.signUpPasswordLayout
        )
        viewModel = ViewModelProvider(this,viewModelFactory).get(RegistrationViewModel::class.java)
        viewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                AuthResult.Loading -> binding.progressBarRegistration.visibility = View.VISIBLE
                is AuthResult.Error -> {
                    binding.progressBarRegistration.visibility = View.GONE
                    Toast.makeText(requireContext(), it.e.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                is AuthResult.Success -> {
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
                }
            }
        }

        binding.startSignUp.setOnClickListener {
            val allValidation = inputList.map { it.isValid() }
            if (allValidation.all { it }) {
                viewModel.sendCredentials(
                    email = binding.signUpEmail.text(),
                    password = binding.signUpPasswordLayout.text(),
                    userName = binding.signUpUsername.text()
                )
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    
}