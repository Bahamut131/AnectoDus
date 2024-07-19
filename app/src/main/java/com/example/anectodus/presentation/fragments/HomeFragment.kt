package com.example.anectodus.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.anectodus.databinding.FragmentHomeBinding
import com.example.anectodus.presentation.JokeApp
import com.example.anectodus.presentation.recyclerView.JokeAdapter
import com.example.anectodus.presentation.viewModels.HomeFragmentViewModel
import com.example.anectodus.presentation.viewModels.states.Content
import com.example.anectodus.presentation.viewModels.states.Loading
import com.example.anectodus.presentation.viewModels.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory).get(HomeFragmentViewModel::class.java)
    }

    private val component by lazy {
        (requireActivity().application as JokeApp).component
    }

    private var _binding : FragmentHomeBinding?= null
    private val binding : FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")


    private lateinit var jokeAdapter : JokeAdapter




    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        launchFlow()
    }

    fun launchFlow(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.loadJoke(null)
                viewModel.jokeList.collect{
                    when(it){
                        is Loading -> {binding.progressBar.isVisible = true}
                        is Content -> {
                            binding.progressBar.isVisible = false
                            jokeAdapter.submitList(it.listJoke)
                        }
                    }
                }
            }
        }
    }


    fun setupRecyclerView(){
        jokeAdapter = JokeAdapter()
        val manager = LinearLayoutManager(requireActivity())
        binding.rvJokeCardList.layoutManager = manager
        binding.rvJokeCardList.adapter = jokeAdapter

        binding.rvJokeCardList.addOnScrollListener(object : OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = manager.childCount
                val totalItemCount = manager.itemCount
                val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                val postsCount = visibleItemCount + firstVisibleItemPosition

                if (postsCount >= totalItemCount && totalItemCount > 0) {
                    val lastItem = jokeAdapter.currentList.lastOrNull()
                    viewModel.loadJoke(lastItem)
                }


            }
        })
        setupSwipe()
    }

    private fun setupSwipe() = with(binding) {
        var onShopItemDelete = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {

            // More code here
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = jokeAdapter.currentList[viewHolder.adapterPosition]
                lifecycleScope.launch {
                    viewModel.likeJoke(item)
                }

            }

        }

        val itemTouchHelper = ItemTouchHelper(onShopItemDelete)
        itemTouchHelper.attachToRecyclerView(rvJokeCardList)
    }
}