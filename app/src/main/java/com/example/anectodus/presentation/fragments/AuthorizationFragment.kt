package com.example.anectodus.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.example.anectodus.R
import com.example.anectodus.databinding.FragmentAuthorizationBinding
import com.example.anectodus.presentation.JokeApp
import com.example.anectodus.presentation.viewModels.AuthorizationViewModel
import com.example.anectodus.presentation.viewModels.HomeFragmentViewModel
import com.example.anectodus.presentation.viewModels.states.AuthResult
import com.example.anectodus.presentation.viewModels.viewModelFactory.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject


class AuthorizationFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as JokeApp).component
    }

    private var _binding: FragmentAuthorizationBinding? = null
    protected val binding get() = _binding ?: throw RuntimeException("")

    lateinit var viewModel: AuthorizationViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthorizationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inputList = listOf(
            binding.authMail,
            binding.authPassword
        )
        viewModel = ViewModelProvider(this,viewModelFactory).get(AuthorizationViewModel::class.java)

        viewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                AuthResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                is AuthResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.e.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                is AuthResult.Success -> {
                    findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                    requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
                }
            }
        }

        binding.signIn.setOnClickListener {
            val allValidation = inputList.map { it.isValid() }

            if (allValidation.all { it }) {
                viewModel.sendCredentials(
                    email = binding.authMail.text(),
                    password = binding.authPassword.text(),
                    userName = ""
                )
            }
        }
        binding.navigateToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registerFragment)
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
        }

    }


    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
    }


    companion object{
        fun newInstance() =    AuthorizationFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}