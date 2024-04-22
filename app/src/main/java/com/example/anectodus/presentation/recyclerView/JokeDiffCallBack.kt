package com.example.anectodus.presentation.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.anectodus.domain.entity.SomeJoke

class JokeDiffCallBack : DiffUtil.ItemCallback<SomeJoke>() {

    override fun areItemsTheSame(oldItem: SomeJoke, newItem: SomeJoke): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SomeJoke, newItem: SomeJoke): Boolean {
        return oldItem== newItem
    }
}