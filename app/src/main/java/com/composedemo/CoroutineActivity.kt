package com.composedemo

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.*

const val TAG = "test"
suspend fun main() {
    val continuation = suspend {
        println("in Coroutine.")
        5
    }.createCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("Coroutine end: $result")
        }
    })
    continuation.resume(Unit)

//     flowTest()

    interceptorTest()
}

fun  flowTest(){
    GlobalScope.launch {
        val f = flow {
            for (i in 1..3) {
                delay(500)
                println("emit $i")
                emit(i)
            }
        }
        withTimeoutOrNull(1600) {
            f.collect {
                delay(500)
                println("consume $it")
            }
        }
        println("cancel")
    }
}

//协程拦截器
class MyContinuationInterceptor:ContinuationInterceptor{
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): MyContinuation<T> {
        return MyContinuation(continuation)
    }
}

class MyContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> {
    override fun resumeWith(result: Result<T>) {
        println("resumeWith: <MyContinuation> $result") //所有协程启动的时候，都会有一次 Continuation.resumeWith 的操作,这里可以做一些拦截操作
        continuation.resumeWith(result)
    }

    override val context: CoroutineContext=continuation.context
}
suspend fun interceptorTest(){
    GlobalScope.launch(MyContinuationInterceptor()){
        println("interceptorTest: 1")
        val job=async {
            println("interceptorTest: 2", )
            delay(1000)
            println("interceptorTest: 3")
            "Hello"
        }
        println("interceptorTest: 4")
        val result=job.await()
        println("interceptorTest: 5  result:$result" )
    }.join()
    println("interceptorTest: 6")

}
