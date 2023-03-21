package com.composedemo.mvi
/**
*定义状态（State） 在MVI中，State是应用程序的当前状态的表示。在这个例子中，我们的状态只是一个简单的整数，表示当前计数器的值。因此，我们可以定义一个CounterState类来表示当前状态。
* **/
data class CounterState(val count: Int=0,val clickNum:Int=0)

