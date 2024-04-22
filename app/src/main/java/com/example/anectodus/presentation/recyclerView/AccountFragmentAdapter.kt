package com.example.anectodus.presentation.recyclerView

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.anectodus.presentation.fragments.LikeJokeFragment
import com.example.anectodus.presentation.fragments.UserePostJokeFragment

class AccountFragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> LikeJokeFragment()
            1 -> UserePostJokeFragment()
            else ->LikeJokeFragment()
        }
    }
}