package com.hanifabdullah.data.model

data class StatusResponseModel<out T>(

    val statusRequest: StatusResponse,

    val data: T? = null,

    val failureModel: FailureModel?

){
    companion object{
        fun <T> loading(): StatusResponseModel<T> {
            return StatusResponseModel(
                StatusResponse.LOADING,
                null,
                null
            )
        }

        fun <T> error(failureModel: FailureModel?): StatusResponseModel<T> {
            return StatusResponseModel(
                StatusResponse.ERROR,
                null,
                failureModel
            )
        }

        fun <T> success(data: T): StatusResponseModel<T> {
            return StatusResponseModel(
                StatusResponse.SUCCESS,
                data,
                null
            )
        }

        fun <T> empty(): StatusResponseModel<T> {
            return StatusResponseModel(
                StatusResponse.EMPTY,
                null,
                null
            )
        }
    }
}