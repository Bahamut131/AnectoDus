package com.example.anectodus.presentation.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.anectodus.databinding.JokeCardBinding
import com.example.anectodus.databinding.PostJokeCardBinding
import com.example.anectodus.domain.entity.SomeJoke

class PostsAdapter : ListAdapter<SomeJoke,PostViewHolder>(PostDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        parent.alpha = 0.9f
        val binding = PostJokeCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val joke = getItem(position)
        with(joke){
            holder.tv_text.text = text
        }

    }
}