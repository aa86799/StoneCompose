package com.stone.stonecompose.page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stone.stonecompose.util.logi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    val myFlow: Flow<Int> = flow {
        repeat(1000) {
            delay(500)
            emit(it)
        }
    }

    val sumData = MutableLiveData<Int>()


    /* 改变 countValue， 会执行 testStateFlow */
    var countValue by mutableStateOf(-100)
        private set
    val testStateFlow: StateFlow<Int> =
        snapshotFlow { countValue }
            .filter { it > 0 }
            .mapLatest { (it * 10) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = countValue
            )

    fun updateInput(count: Int) {
        countValue = count
    }

    fun testStateFlowFunc(scope: CoroutineScope) {
        viewModelScope.launch {
            withContext(scope.coroutineContext) {
                testStateFlow.collect {
                    logi("testStateFlow collect: $it")
                }
            }
        }
    }
}