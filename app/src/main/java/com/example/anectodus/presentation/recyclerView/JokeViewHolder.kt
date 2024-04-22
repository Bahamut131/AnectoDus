package com.example.anectodus.presentation.recyclerView

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.anectodus.databinding.JokeCardBinding

class JokeViewHolder(val bining : JokeCardBinding) : ViewHolder(bining.root) {
    val tv_text = bining.textView
}