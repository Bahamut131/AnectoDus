package com.example.anectodus.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anectodus.databinding.FragmentLikeJokeBinding
import com.example.anectodus.presentation.JokeApp
import com.example.anectodus.presentation.recyclerView.PostsAdapter
import com.example.anectodus.presentation.viewModels.AccountViewModel
import com.example.anectodus.presentation.viewModels.states.Content
import com.example.anectodus.presentation.viewModels.states.Loading
import com.example.anectodus.presentation.viewModels.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class LikeJokeFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as JokeApp).component
    }

    private var _binding : FragmentLikeJokeBinding?= null
    private val binding : FragmentLikeJokeBinding
        get() = _binding ?: throw RuntimeException("FragmentLikeJokeBinding == null")

    private lateinit var jokeAdapter : PostsAdapter
    private lateinit var manager: RecyclerView.LayoutManager



    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory).get(AccountViewModel::class.java)
    }

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikeJokeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchRecyclerView()
        launchFlow()
    }
    private fun launchRecyclerView() {
        jokeAdapter = PostsAdapter()
        manager = GridLayoutManager(requireActivity(),2, GridLayoutManager.VERTICAL,false)
        binding.recyclerLikeJoke.layoutManager = manager
        binding.recyclerLikeJoke.adapter = jokeAdapter
    }


    private fun launchFlow(){
        scope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.jokeLikeList.collect{
                    when(it){
                        is Loading -> {binding.progressBar3.isVisible = true}
                        is Content -> {
                            binding.progressBar3.isVisible = false
                            jokeAdapter.submitList(it.listJoke)
                        }
                    }
                }
            }
        }
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LikeJokeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}