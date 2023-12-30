package com.stone.stonecompose.page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeViewModel : ViewModel() {

    val myFlow: Flow<Int> = flow {
        repeat(1000) {
            delay(500)
            emit(it)
        }
    }

    val sumData = MutableLiveData<Int>()
}