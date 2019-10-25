package com.weiwei.ww.base

import androidx.lifecycle.*
import kotlinx.coroutines.*

/**
1.MutableLiveData的父类是LiveData

2.LiveData在实体类里可以通知指定某个字段的数据更新.

3.MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    private val _state = MutableLiveData<StatusModel>()
    val state: LiveData<StatusModel>
        get() = _state

    val mException: MutableLiveData<Throwable> = MutableLiveData()


    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {

        viewModelScope.launch { block() }

    }

    suspend fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }


    fun launchOnUITryCatch(tryBlock: suspend CoroutineScope.() -> Unit,
                           catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
                           finallyBlock: suspend CoroutineScope.() -> Unit,
                           handleCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }

    fun launchOnUITryCatch(tryBlock: suspend CoroutineScope.() -> Unit,
                           handleCancellationExceptionManually: Boolean = false
    ) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, handleCancellationExceptionManually)
        }
    }


    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Throwable) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mException.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }


    fun emitState(
        showLoading: Boolean = false,
        showError: String? = null,
        needLogin: Boolean = false
    ) {
        val statusModel = StatusModel(showLoading, showError,needLogin)
        _state.value = statusModel
    }

    data class StatusModel(
        val showLoading: Boolean,
        val showError: String?,
        val needLogin:Boolean
    )
}