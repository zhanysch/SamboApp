package com.example.sambo.ui.bottomnavigation.courses.adapterForCourseFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sambo.R
import com.example.sambo.data.model.cards.RowsItem
import com.example.sambo.utils.diffUtils.DiffUtilsCards

import com.example.sambo.utils.ext.setCornerRadius
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_helper.view.*

class CoursesAdapter(private val listener: (item: RowsItem, image: ShapeableImageView) -> Unit) :
    PagedListAdapter<RowsItem, CoursesViewHolder>(DiffUtilsCards.diffUtilCards) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_helper, parent, false)
        return CoursesViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CoursesViewHolder(view: View, private val listener: (item: RowsItem, image: ShapeableImageView) -> Unit) :
    RecyclerView.ViewHolder(view) {
    fun bind(item: RowsItem?) {
        itemView.textCourse.text = item?.title.toString()
        itemView.Material.text = item?.categories_list.toString()
        Picasso.get().load(item?.preview).into(itemView.imageCourse)

        itemView.imageCourse.transitionName =     /// анимация
            itemView.context.resources.getString(R.string.image_transition, item?.id)

        val radius = itemView.context.resources.getDimension(R.dimen.imageCutted)

        itemView.imageCourse.setCornerRadius(  // view Extension
            topLeft = radius,
            topRight = radius,
            bottomLeft = radius,
            bottomRight = radius
        )

        itemView.setOnClickListener {
            item?.let { it1 -> listener.invoke(it1,itemView.imageCourse) }
        }
    }
}