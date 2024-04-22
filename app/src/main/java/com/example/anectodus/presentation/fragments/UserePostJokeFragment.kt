package com.example.anectodus.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.anectodus.R
import com.example.anectodus.databinding.FragmentLikeJokeBinding
import com.example.anectodus.databinding.FragmentUserePostJokeBinding
import com.example.anectodus.presentation.JokeApp
import com.example.anectodus.presentation.customView.CardLayoutManager
import com.example.anectodus.presentation.recyclerView.JokeAdapter
import com.example.anectodus.presentation.recyclerView.PostsAdapter
import com.example.anectodus.presentation.viewModels.AccountViewModel
import com.example.anectodus.presentation.viewModels.viewModelFactory.ViewModelFactory
import javax.inject.Inject


class UserePostJokeFragment : Fragment() {

    private var _binding : FragmentUserePostJokeBinding?= null
    private val binding : FragmentUserePostJokeBinding
        get() = _binding ?: throw RuntimeException("FragmentUserePostJokeBinding == null")

    private val component by lazy {
        (requireActivity().application as JokeApp).component
    }

    lateinit var viewModel : AccountViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var jokeAdapter : PostsAdapter
    private lateinit var manager: RecyclerView.LayoutManager


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
        _binding = FragmentUserePostJokeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(AccountViewModel::class.java)
        launchRecyclerView()
        launchViewModel()
    }

    private fun launchRecyclerView() {
        jokeAdapter = PostsAdapter()
        manager = GridLayoutManager(requireActivity(),2, GridLayoutManager.VERTICAL,false)
        binding.postRecyclerView.layoutManager = manager
        binding.postRecyclerView.adapter = jokeAdapter
    }


    private fun launchViewModel(){
        viewModel.jokeList.observe(viewLifecycleOwner){
            jokeAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}