package com.example.anectodus.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.anectodus.R
import com.example.anectodus.databinding.FragmentAccauntBinding
import com.example.anectodus.databinding.FragmentHomeBinding
import com.example.anectodus.presentation.JokeApp
import com.example.anectodus.presentation.activity.MainActivity
import com.example.anectodus.presentation.recyclerView.AccountFragmentAdapter
import com.example.anectodus.presentation.viewModels.AccountViewModel
import com.example.anectodus.presentation.viewModels.AuthorizationViewModel
import com.example.anectodus.presentation.viewModels.viewModelFactory.ViewModelFactory
import javax.inject.Inject


class AccountFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as JokeApp).component
    }

    lateinit var viewModel: AccountViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding : FragmentAccauntBinding?= null
    private val binding : FragmentAccauntBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    private lateinit var accountAdapter: AccountFragmentAdapter

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
        _binding = FragmentAccauntBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(AccountViewModel::class.java)
        launchViewPager()

        viewModel.userName.observe(viewLifecycleOwner){
            binding.textView2.text = it
        }

    }

    private fun launchViewPager() {
        accountAdapter = AccountFragmentAdapter(requireActivity())
        binding.postViewPager.adapter = accountAdapter
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}