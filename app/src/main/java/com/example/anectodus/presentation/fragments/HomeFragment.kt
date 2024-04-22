package com.example.anectodus.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.anectodus.presentation.customView.CardLayoutManager
import com.example.anectodus.databinding.FragmentHomeBinding
import com.example.anectodus.presentation.JokeApp
import com.example.anectodus.presentation.viewModels.viewModelFactory.ViewModelFactory
import com.example.anectodus.presentation.recyclerView.JokeAdapter
import com.example.anectodus.presentation.viewModels.HomeFragmentViewModel
import javax.inject.Inject


class HomeFragment : Fragment() {

    lateinit var viewModel: HomeFragmentViewModel

    private val component by lazy {
        (requireActivity().application as JokeApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    private var _binding : FragmentHomeBinding?= null
    private val binding : FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")


    private lateinit var jokeAdapter : JokeAdapter
    private lateinit var manager: RecyclerView.LayoutManager

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
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeFragmentViewModel::class.java)
        launchViewModel()
        setupRecyclerView()
    }

    fun launchViewModel(){
        viewModel.jokeList.observe(viewLifecycleOwner){
            jokeAdapter.submitList(it)
        }
    }


    fun setupRecyclerView(){
        jokeAdapter = JokeAdapter()
        manager = CardLayoutManager()
        binding.rvJokeCardList.layoutManager = manager

        binding.rvJokeCardList.adapter = jokeAdapter
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
                viewModel.deleteJoke(item)


            }

        }

        val itemTouchHelper = ItemTouchHelper(onShopItemDelete)
        itemTouchHelper.attachToRecyclerView(rvJokeCardList)
    }
}