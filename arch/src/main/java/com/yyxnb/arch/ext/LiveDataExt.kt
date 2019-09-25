package com.yyxnb.arch.ext

import android.arch.lifecycle.*
import android.support.v4.app.FragmentActivity
import kotlin.LazyThreadSafetyMode.NONE


fun <X, Y> LiveData<X>.map(function: (X) -> Y): LiveData<Y> =
        Transformations.map(this, function)

fun <X, Y> LiveData<X>.switchMap(function: (X) -> LiveData<Y>): LiveData<Y> =
        Transformations.switchMap(this, function)

inline fun <T : Any> LiveData<T>.observeWith(
        lifecycleOwner: LifecycleOwner,
        crossinline onChanged: (T) -> Unit
) {
    observe(lifecycleOwner, Observer {
        it ?: return@Observer
        onChanged.invoke(it)
    })
}

inline fun <reified T : ViewModel> ViewModelProvider.Factory.get(fragmentActivity: FragmentActivity): T =
        ViewModelProviders.of(fragmentActivity, this)[T::class.java]

fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(NONE, initializer)