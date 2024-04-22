package com.example.anectodus.presentation.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.anectodus.databinding.JokeCardBinding
import com.example.anectodus.domain.entity.SomeJoke

class JokeAdapter : ListAdapter<SomeJoke, JokeViewHolder>(JokeDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        parent.alpha = 0.9f
        val binding = JokeCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return JokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        val joke = getItem(position)

        with(joke){
            holder.tv_text.text = text
        }

    }



}