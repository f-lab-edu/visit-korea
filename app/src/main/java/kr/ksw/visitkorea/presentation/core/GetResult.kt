package kr.ksw.visitkorea.presentation.core

fun <T> Result<T>.getResult(block: (result: T) -> Unit) {
    getOrNull()?.run {
        block(this)
    }
}