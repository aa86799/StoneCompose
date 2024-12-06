package com.stone.stonecompose.base.mvi

interface IUiState

/**
 * 正在加载
 */
class LoadingState(val isShow: Boolean, val msg: String = "") : IUiState

/**
 * 加载失败
 */
class LoadErrorState(var error: String, var flag: Int = 0) : IUiState

/**
 * 加载失败
 * @param errorType 区分错误类型
 */
class LoadErrorTypeState(var error: String, var errorType: Int) : IUiState

//class LoadResultState<T>(val subState: IUiState, val data: T?, val error: String? = null) : IUiState

//class LoadPageDataResultState<T>(val data: List<T>?, val page: Int, val error: String? = null) : IUiState