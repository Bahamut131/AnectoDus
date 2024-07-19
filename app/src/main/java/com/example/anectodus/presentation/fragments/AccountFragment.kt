package com.example.anectodus.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.anectodus.R
import com.example.anectodus.databinding.FragmentAccauntBinding
import com.example.anectodus.presentation.JokeApp
import com.example.anectodus.presentation.activity.MainActivity
import com.example.anectodus.presentation.recyclerView.AccountFragmentAdapter
import com.example.anectodus.presentation.viewModels.AccountViewModel
import com.example.anectodus.presentation.viewModels.AuthorizationViewModel
import com.example.anectodus.presentation.viewModels.viewModelFactory.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject


class AccountFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as JokeApp).component
    }

    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory).get(AccountViewModel::class.java)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding : FragmentAccauntBinding?= null
    private val binding : FragmentAccauntBinding
        get() = _binding ?: throw RuntimeException("AccountFragment == null")

    private lateinit var accountAdapter: AccountFragmentAdapter

    private lateinit var tabLayout : TabLayout

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
        launchViewPager()

        viewModel.userName.observe(viewLifecycleOwner){
            binding.textView2.text = it
        }

    }

    private fun launchViewPager() {
        accountAdapter = AccountFragmentAdapter(requireActivity())

        binding.postViewPager.adapter = accountAdapter
        tabLayout = binding.tabLayoutAccount
        val tabImg = arrayOf(
            R.drawable.ic_user_post,
            R.drawable.ic_user_like,
        )
        TabLayoutMediator(tabLayout,binding.postViewPager){tab,position ->
            tab.setIcon(tabImg[position])
        }.attach()
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}