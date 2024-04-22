package com.example.anectodus.presentation.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.example.anectodus.databinding.JokeCardBinding
import com.example.anectodus.databinding.PostJokeCardBinding

class PostViewHolder(val binding : PostJokeCardBinding) : RecyclerView.ViewHolder(binding.root) {
    val tv_text = binding.textView
}