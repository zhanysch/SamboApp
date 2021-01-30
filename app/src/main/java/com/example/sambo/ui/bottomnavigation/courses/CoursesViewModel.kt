package com.example.sambo.ui.bottomnavigation.courses

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sambo.data.modelcourses.MainCourseModel
import androidx.lifecycle.viewModelScope
import com.example.sambo.data.commonpagination.BaseDataSource
import com.example.sambo.data.commonpagination.BasePagedViewModel
import com.example.sambo.data.model.cards.RowsItem
import com.example.sambo.data.modelBottomSheet.BottomSheetRows
import com.example.sambo.data.repository.SamboRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoursesViewModel(private val service: SamboRepository) :
    BasePagedViewModel<RowsItem, CoursesViewModel.CourseDataSource>() {


    override val sourceFactory by lazy {
        BaseDataSource.Factory {
            CourseDataSource(viewModelScope)
        }
    }

    val text = MutableLiveData<BottomSheetRows>()
    val data = getPagedList()
    val dataCategory = MutableLiveData<List<BottomSheetRows>>()
    var categoryId: Int =
        -1   // -1 значен по умолчанию для подгрузки  оперделенных данных при выборе категории

    fun loadList() {
        viewModelScope.launch {
            val result = service.loadCategory(limit = 20, page = 1)
            if (result.isSuccessful) dataCategory.postValue(result.body()?.rows)
        }
    }

    fun choosedCategory(item: BottomSheetRows) {   // функц выбирает категорию //  dataSourceFactoryLiveData из класса  BaseDataSource
        categoryId = item.id
        sourceFactory.dataSourceFactoryLiveData.value?.invalidate()
    }

    inner class CourseDataSource(  // это внутренний класс, а не метод
        scope: CoroutineScope
    ) : BaseDataSource<RowsItem>(scope) {
        //<Data> это class  тут укзываетс, чтo тип T это -> class Data (data class)
        override fun getListByPageNumber(limit: Int, page: Int): MainCourseModel<RowsItem>? {
            return runBlocking {
                val data = service.loadData(limit = limit, page = page, categoryId = categoryId)
                return@runBlocking data
            }
        }
    }
}


