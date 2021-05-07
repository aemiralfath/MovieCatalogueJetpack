package com.aemiralfath.moviecatalogue.ui.utils

import androidx.paging.PagedList
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

@Suppress("UNCHECKED_CAST")
object PagedListUtil {

    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList[ArgumentMatchers.anyInt()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)

        return pagedList
    }
}