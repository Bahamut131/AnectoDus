package com.example.anectodus.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anectodus.R
import com.example.anectodus.databinding.FragmentHomeBinding
import com.example.anectodus.databinding.FragmentLikeJokeBinding
import com.example.anectodus.presentation.recyclerView.JokeAdapter


class LikeJokeFragment : Fragment() {

    private var _binding : FragmentLikeJokeBinding?= null
    private val binding : FragmentLikeJokeBinding
        get() = _binding ?: throw RuntimeException("FragmentLikeJokeBinding == null")

    private lateinit var jokeAdapter : JokeAdapter
    private lateinit var manager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikeJokeBinding.inflate(inflater,container,false)
        return binding.root
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