package com.emedinaa.kotlinmvvm.data

import com.emedinaa.kotlinmvvm.message.OperationCallback
import com.emedinaa.kotlinmvvm.model.MuseumRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Eduardo Medina
 */
private const val TAG = "CONSOLE"

class MuseumRemoteDataSource(private val service: ApiClient.ServicesApiInterface) :
    MuseumRepository {

    private var disposable: Disposable? = null

    override fun retrieveMuseums(callback: OperationCallback) {
        disposable = service.museums().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                val data = result.data
                callback.onSuccess(data ?: emptyList())
            }, { error ->
                callback.onError(Exception(error))
            })
    }

    override fun cancel() {
        disposable?.dispose()
    }
}