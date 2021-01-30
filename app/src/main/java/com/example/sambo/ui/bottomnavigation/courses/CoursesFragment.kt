package com.example.sambo.ui.bottomnavigation.courses

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.sambo.R
import com.example.sambo.data.model.cards.RowsItem
import com.example.sambo.data.modelBottomSheet.BottomSheetRows
import com.example.sambo.ui.bottomnavigation.BaseFragment
import com.example.sambo.ui.bottomnavigation.courses.adapterForCourseFragment.CoursesAdapter
import com.example.sambo.ui.bottomnavigation.courses.bottomsheet.BottomSheet
import com.example.sambo.ui.bottomnavigation.courses.bottomsheet.ItemListener
import com.example.sambo.utils.ext.toTransitionGroup
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.fragment_courses.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CoursesFragment : BaseFragment(), ItemListener {
    override fun resID() = R.layout.fragment_courses
    private val vm by sharedViewModel<CoursesViewModel>()
    private val adapter by lazy {
        CoursesAdapter() { item: RowsItem, image: ShapeableImageView ->
            navigateToDetails(item, image)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.loadList()
        vm.text.observe(viewLifecycleOwner, Observer {
            // изминен текста при клике на recyclerview
            textChange.text = it.title
            vm.choosedCategory(it)  // подгрузка картины при клике на категорию
        })

        recycler_courses.adapter = adapter
        vm.data.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

       /* recycler_courses.apply {
            adapter = adapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }*/


        textChange.setOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(childFragmentManager, "tdsg")
        }
    }

    private fun navigateToDetails(data: RowsItem, image: ShapeableImageView) {
        val extras = FragmentNavigatorExtras(  // анимац
            image.toTransitionGroup()
        )
        val destination = CoursesFragmentDirections.actionCoursesFragmentToNewsDetailsFragment(data)
        findNavController().navigate(destination,extras)
    }

    override fun itemsClick(item: BottomSheetRows) {   // для подгрузки  оперделенных данных при выборе категории
        vm.choosedCategory(item)
    }
}