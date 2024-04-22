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
import com.example.anectodus.databinding.FragmentAddJokeBinding
import com.example.anectodus.presentation.JokeApp
import com.example.anectodus.presentation.viewModels.viewModelFactory.ViewModelFactory
import com.example.anectodus.presentation.viewModels.AddJokeFragmentViewModel
import com.example.anectodus.presentation.viewModels.states.AddJoke
import com.example.anectodus.presentation.viewModels.states.Error
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject


class AddJokeFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as JokeApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding : FragmentAddJokeBinding?= null
    private val binding : FragmentAddJokeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")


    lateinit var viewModel: AddJokeFragmentViewModel

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddJokeBinding.inflate(inflater,container,false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[AddJokeFragmentViewModel::class.java]
        launchViewModel()
        binding.button.setOnClickListener {
           viewModel.addJoke(binding.editTextText.text.toString())
        }
    }

    private fun launchViewModel(){
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is Error -> {Toast.makeText(requireActivity().application,"Input correct value", Toast.LENGTH_SHORT).show()}
                is AddJoke -> {
                    it.addJoke
                    launchHomeFragment()
                }
            }
        }
    }

    private fun launchHomeFragment(){
        findNavController().navigate(R.id.action_addJokeFragment_to_homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
    }

}