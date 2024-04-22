package com.example.anectodus.presentation.customView

import android.app.Application
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.anectodus.data.AppDataBase
import com.example.anectodus.data.JokeListDao
import javax.inject.Inject

class CardLayoutManager  : RecyclerView.LayoutManager() {



    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return  RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
       fillDown(recycler)
    }

    private fun fillDown(recycler: RecyclerView.Recycler?) {
        var pos = 0;
        var fillDown = true;
        val height = getHeight();
        var viewTop = 0;
        val itemCount = getItemCount();

        while (fillDown && pos < itemCount) {
            val view: View? = recycler?.getViewForPosition(0)
            addView(view)
            measureChildWithMargins(view!!, 0, 0)
            layoutDecorated(view, 0, 0, width, height)
            viewTop = getDecoratedBottom(view)
            fillDown = viewTop <= height
            pos++
        }
    }
}