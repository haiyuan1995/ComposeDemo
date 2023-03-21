package com.composedemo.mvi
/**
 * 定义意图（Intent） Intent表示用户的意图或行为，它会触发应用程序状态的变化。在这个例子中，我们只有一个意图：增加计数器的值。因此，我们可以定义一个CounterIntent类来表示这个意图
 * **/
sealed class CounterIntent{
    object Increment:CounterIntent()
    object Decrement:CounterIntent()
}
