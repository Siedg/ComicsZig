package br.com.comicszig.core.components

enum class Status {
    INITIAL,
    SUCCESS,
    ERROR,
    LOADING
}

data class ViewState<out T>(
    val status: Status,
    val data: T?,
    val error: Error?
) {
    companion object {
        fun <T> initial(): ViewState<T> {
            return ViewState(Status.INITIAL, null, null)
        }

        fun <T> loading(): ViewState<T> {
            return ViewState(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): ViewState<T> {
            return ViewState(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Error?): ViewState<T> {
            return ViewState(Status.ERROR, null, error)
        }
    }
}
