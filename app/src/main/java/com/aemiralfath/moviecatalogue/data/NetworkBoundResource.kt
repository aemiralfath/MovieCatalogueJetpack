package com.aemiralfath.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.aemiralfath.moviecatalogue.data.source.remote.ApiResponse
import com.aemiralfath.moviecatalogue.data.source.remote.StatusResponse
import com.aemiralfath.moviecatalogue.utils.AppExecutors
import com.aemiralfath.moviecatalogue.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(private val executors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis") val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) {
                    result.value = Resource.success(it)
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun onFetchFailed() {}

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) {
            result.value = Resource.loading(it)
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                StatusResponse.SUCCESS ->
                    executors.diskIO().execute {
                        response.body?.let { saveCallResult(it) }
                        executors.mainThread().execute {
                            result.addSource(loadFromDB()) {
                                result.value = Resource.success(it)
                            }
                        }
                    }
                StatusResponse.EMPTY -> executors.mainThread().execute {
                    result.addSource(loadFromDB()) {
                        result.value = Resource.success(it)
                    }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) {
                        result.value = response.message?.let { text -> Resource.error(text, it) }
                    }
                }
            }
        }
    }

}