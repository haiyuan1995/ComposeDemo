package com.composedemo.sample
data class ApiResponse<T>(
    val resultCode: Int = RESULT_SUCCESS,
    val errorMsg: String? = null,
    val data: T
) {

    companion object {
        const val RESULT_SUCCESS = 0
    }
}

data class Page<T>(
    val curPage: Int,
    val offset: Int,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: T
)