package com.yyxnb.arch.ext

import android.arch.lifecycle.*
import android.support.v4.app.FragmentActivity
import com.yyxnb.arch.jetpack.NonNullLiveData
import com.yyxnb.arch.jetpack.SingleLiveData
import com.yyxnb.arch.jetpack.SingleLiveDataConcat
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Creates a LiveData that emits the initialValue immediately.
 * 从一个值创建一个LiveData对象（类似于justRxJava，尽管它立即发出该值）
 */
fun <T> liveDataOf(initialValue: T): MutableLiveData<T> {
    return emptyLiveData<T>().apply { value = initialValue }
}


/**
 * Creates a LiveData that emits the value that the `callable` function produces, immediately.
 * 创建livedata，该livedata立即发出“callable”函数生成的值。
 */
fun <T> liveDataOf(callable: () -> T): LiveData<T> {
    val returnedLiveData = MutableLiveData<T>()
    returnedLiveData.value = callable.invoke()
    return returnedLiveData
}

/**
 * Creates an empty LiveData.
 * 创建空的Livedata。
 */
fun <T> emptyLiveData(): MutableLiveData<T> {
    return MutableLiveData()
}

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

/**
 * Emits the items that are different from all the values that have been emitted so far
 * 发出与迄今为止发出的所有值不同的项
 */
fun <T> LiveData<T>.distinct(): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    val dispatchedValues = mutableSetOf<T?>()
    mutableLiveData.addSource(this) {
        if (!dispatchedValues.contains(it)) {
            mutableLiveData.value = it
            dispatchedValues.add(it)
        }
    }
    return mutableLiveData
}

/**
 * Emits the items that are different from the last item
 * 发出与上一项不同的项
 */
fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    var latestValue: T? = null
    mutableLiveData.addSource(this) {
        if (latestValue != it) {
            mutableLiveData.value = it
            latestValue = it
        }
    }
    return mutableLiveData
}

/**
 * Emits the items that pass through the predicate
 * 发出通过谓词的项
 */
inline fun <T> LiveData<T>.filter(crossinline predicate: (T?) -> Boolean): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    mutableLiveData.addSource(this) {
        if (predicate(it))
            mutableLiveData.value = it
    }
    return mutableLiveData
}

/**
 * Emits at most 1 item and returns a SingleLiveData
 * 产生仅产生一个项目的SingleLiveData。
 */
fun <T> LiveData<T>.first(): SingleLiveData<T> {
    return SingleLiveData(this)
}

/**
 * Emits the first n valueus
 * 产生仅产生前n个项目的LiveData。
 */
fun <T> LiveData<T>.take(count: Int): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    var takenCount = 0
    mutableLiveData.addSource(this) {
        if (takenCount < count) {
            mutableLiveData.value = it
            takenCount++
        } else {
            mutableLiveData.removeSource(this)
        }
    }
    return mutableLiveData
}

/**
 * Takes until a certain predicate is met, and does not emit anything after that, whatever the value.
 * 直到满足某个谓词为止，此后无论值如何都不会发出任何东西。
 */
inline fun <T> LiveData<T>.takeUntil(crossinline predicate: (T?) -> Boolean): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    var metPredicate = predicate(value)
    mutableLiveData.addSource(this) {
        if (predicate(it)) metPredicate = true
        if (!metPredicate) {
            mutableLiveData.value = it
        } else {
            mutableLiveData.removeSource(this)
        }
    }
    return mutableLiveData
}

/**
 * Skips the first n values
 * 跳过前n个值
 */
fun <T> LiveData<T>.skip(count: Int): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    var skippedCount = 0
    mutableLiveData.addSource(this) {
        if (skippedCount >= count) {
            mutableLiveData.value = it
        }
        skippedCount++
    }
    return mutableLiveData
}

/**
 * Skips all values until a certain predicate is met (the item that actives the predicate is also emitted)
 * 跳过所有值，直到满足某个谓词（激活谓词的项也被发出）
 */
inline fun <T> LiveData<T>.skipUntil(crossinline predicate: (T?) -> Boolean): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    var metPredicate = false
    mutableLiveData.addSource(this) {
        if (metPredicate || predicate(it)) {
            metPredicate = true
            mutableLiveData.value = it
        }
    }
    return mutableLiveData
}

/**
 * emits the item that was emitted at `index` position
 * Note: This only works for elements that were emitted `after` the `elementAt` is applied.
 * 发出在“index”位置发出的项
 * 注意：这只适用于应用“elementat”之后发出的元素。
 */
fun <T> LiveData<T>.elementAt(index: Int): SingleLiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    var currentIndex = 0
    if (this.value != null)
        currentIndex = -1
    mutableLiveData.addSource(this) {
        if (currentIndex == index) {
            mutableLiveData.value = it
            mutableLiveData.removeSource(this)
        }
        currentIndex++
    }
    return SingleLiveData(mutableLiveData)
}

/**
 * Emits only the values that are not null
 * 永远不会向观察者发出空值（输出a NonNullLiveData）。
 */
fun <T> LiveData<T>.nonNull(): NonNullLiveData<T> {
    return NonNullLiveData(this)
}

/**
 * Emits the default value when a null value is emitted
 * 将产生value何时null收到。
 */
fun <T> LiveData<T>.defaultIfNull(default: T): LiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    mutableLiveData.addSource(this) {
        mutableLiveData.value = it ?: default
    }
    return mutableLiveData
}

/**
 * Maps any values that were emitted by the LiveData to the given function
 * 使用给定函数将每个发出的值映射到另一个值（和类型）
 */
fun <T, O> LiveData<T>.map(function: MapperFunction<T, O>): LiveData<O> {
    return Transformations.map(this, function)
}

/**
 * Maps any values that were emitted by the LiveData to the given function that produces another LiveData
 * 将LiveData发出的任何值映射到产生另一个LiveData的给定函数
 */
fun <T, O> LiveData<T>.switchMap(function: MapperFunction<T, LiveData<O>>): LiveData<O> {
    return Transformations.switchMap(this, function)
}

/**
 * Does the `onNext` function before everything actually emitting the item to the observers
 * onNext在将所有内容实际发送给观察者之前执行功能
 */
fun <T> LiveData<T>.doBeforeNext(onNext: OnNextAction<T>): MutableLiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    mutableLiveData.addSource(this) {
        onNext(it)
        mutableLiveData.value = it
    }
    return mutableLiveData
}

/**
 * Does the `onNext` function after emitting the item to the observers
 * onNext将项目发送给观察者后执行功能（函数）：onNext在将项目实际发送给观察者之前执行功能
 */
fun <T> LiveData<T>.doAfterNext(onNext: OnNextAction<T>): MutableLiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()
    mutableLiveData.addSource(this) {
        mutableLiveData.value = it
        onNext(it)
    }
    return mutableLiveData
}

/**
 * Buffers the items emitted by the LiveData, and emits them when they reach the `count` as a List.
 * 缓冲LiveData发出的项目，并在到达count列表时将其发出。
 */
fun <T> LiveData<T>.buffer(count: Int): MutableLiveData<List<T?>> {
    val mutableLiveData: MediatorLiveData<List<T?>> = MediatorLiveData()
    val latestBuffer = mutableListOf<T?>()
    mutableLiveData.addSource(this) { value ->
        latestBuffer.add(value)
        if (latestBuffer.size == count) {
            mutableLiveData.value = latestBuffer.toList()
            latestBuffer.clear()
        }

    }
    return mutableLiveData
}

/**
 * Returns a LiveData that applies a specified accumulator function to each item that is emitted
 * after the first item has been emitted.
 * Note: The LiveData should not emit nulls. Add .nonNull() to your LiveData if you want to ensure this.
 * 返回一个livedata，该livedata将指定的累加器函数应用于发出的每个项
 * 在发出第一个项目之后。
 * 注意：livedata不应该发出空值。如果您想确保这一点，请将.nonnull（）添加到LiveData中。
 *
 * @param accumulator the function that is applied to each item
 */
fun <T> LiveData<T>.scan(accumulator: (accumulatedValue: T, currentValue: T) -> T): MutableLiveData<T> {
    var accumulatedValue: T? = null
    val hasEmittedFirst = AtomicBoolean(false)
    return MediatorLiveData<T>().apply {
        addSource(this@scan) { emittedValue ->
            if (hasEmittedFirst.compareAndSet(false, true)) {
                accumulatedValue = emittedValue
            } else {
                accumulatedValue = accumulator(accumulatedValue!!, emittedValue!!)
                value = accumulatedValue
            }
        }
    }
}

/**
 * Returns a LiveData that applies a specified accumulator function to the first item emitted by a source LiveData,
 * then feeds the result of that function along with the second item emitted by the source LiveData into the same function,
 * and so on, emitting the result of each of these iterations.
 * Note: Your LiveData should not emit nulls. Add .nonNull() to your LiveData if you want to ensure this.
 * 返回一个livedata，该livedata将指定的累加器函数应用于源livedata发出的第一个项，
 * 然后将该函数的结果与源livedata发出的第二个项一起馈送到同一个函数中，
 * 等等，发出每个迭代的结果。
 * 注意：livedata不应该发出空值。如果您想确保这一点，请将.nonnull（）添加到LiveData中。
 *
 * @param initialSeed the initial value of the accumulator
 * @param accumulator the function that is applied to each item
 */
fun <T, R> LiveData<T>.scan(initialSeed: R, accumulator: (accumulated: R, currentValue: T) -> R): MutableLiveData<R> {
    var accumulatedValue = initialSeed
    return MediatorLiveData<R>().apply {
        value = initialSeed
        addSource(this@scan) { emittedValue ->
            accumulatedValue = accumulator(accumulatedValue, emittedValue!!)
            value = accumulatedValue
        }
    }
}

/**
 * Emits the items of the first LiveData that emits the item. Items of other LiveDatas will never be emitted and are not considered.
 * 发出发出该项目的第一个LiveData的项目。其他LiveData的项目将永远不会发出，也不会被考虑。
 */
fun <T> amb(vararg inputLiveData: LiveData<T>, considerNulls: Boolean = true): MutableLiveData<T> {
    val mutableLiveData: MediatorLiveData<T> = MediatorLiveData()

    var activeLiveDataIndex = inputLiveData.indexOfFirst { it.value != null }
    if (activeLiveDataIndex >= 0) {
        mutableLiveData.value = inputLiveData[activeLiveDataIndex].value
    }
    inputLiveData.forEachIndexed { index, liveData ->
        mutableLiveData.addSource(liveData) { value ->
            if (considerNulls || value != null) {
                activeLiveDataIndex = index
                inputLiveData.forEachIndexed { index, liveData ->
                    if (index != activeLiveDataIndex) {
                        mutableLiveData.removeSource(liveData)
                    }
                }

                if (index == activeLiveDataIndex) {
                    mutableLiveData.value = value
                }
            }


        }
    }
    return mutableLiveData
}

/**
 * Converts a LiveData to a SingleLiveData (exactly similar to LiveData.first()
 * 将LiveData转换为SingleLiveData（与LiveData.First（）完全相似）
 */
fun <T> LiveData<T>.toSingleLiveData(): SingleLiveData<T> = first()

/**
 * Converts a LiveData to a MutableLiveData with the initial value set by this LiveData's value
 * 将Livedata转换为MutableLiveData，初始值由livedata的值设置
 */
fun <T> LiveData<T>.toMutableLiveData(): MutableLiveData<T> {
    val liveData = MutableLiveData<T>()
    liveData.value = this.value
    return liveData
}

/**
 * Mapper function used in the operators that need mapping
 * 需要映射的运算符中使用的映射器函数
 */
typealias MapperFunction<T, O> = (T) -> O

/**
 * Mapper function used in the operators that need mapping
 * 需要映射的运算符中使用的映射器函数
 */
typealias OnNextAction<T> = (T?) -> Unit

/**
 * Merges this LiveData with another one, and emits any item that was emitted by any of them
 * 将此livedata与另一个livedata合并，并发出其中任何一个发出的任何项
 */
fun <T> LiveData<T>.mergeWith(vararg liveDatas: LiveData<T>): LiveData<T> {
    val mergeWithArray = mutableListOf<LiveData<T>>()
    mergeWithArray.add(this)
    mergeWithArray.addAll(liveDatas)
    return merge(mergeWithArray)
}


/**
 * Merges multiple LiveData, and emits any item that was emitted by any of them
 * 合并多个livedata，并发出其中任何一个发出的任何项
 */
fun <T> merge(liveDataList: List<LiveData<T>>): LiveData<T> {
    val finalLiveData: MediatorLiveData<T> = MediatorLiveData()
    liveDataList.forEach { liveData ->

        liveData.value?.let {
            finalLiveData.value = it
        }

        finalLiveData.addSource(liveData) { source ->
            finalLiveData.value = source
        }
    }
    return finalLiveData
}

/**
 * Emits the `startingValue` before any other value.
 * 发出startingValue其他任何值之前的值。
 */
fun <T> LiveData<T>.startWith(startingValue: T?): LiveData<T> {
    val finalLiveData: MediatorLiveData<T> = MediatorLiveData()
    finalLiveData.value = startingValue
    finalLiveData.addSource(this) { source ->
        finalLiveData.value = source
    }
    return finalLiveData
}

/**
 * zips both of the LiveData and emits a value after both of them have emitted their values,
 * after that, emits values whenever any of them emits a value.
 *
 * The difference between combineLatest and zip is that the zip only emits after all LiveData
 * objects have a new value, but combineLatest will emit after any of them has a new value.
 * 在livedata和emitter都发出值之后，对它们进行zips和emitter操作，
 * 之后，只要其中任何一个发出值，就会发出值。
 *
 * combinelast和zip的区别在于，zip毕竟只发出livedata
 * 对象有一个新值，但combineTest将在其中任何对象有一个新值后发出。
 */
fun <T, Y> zip(first: LiveData<T>, second: LiveData<Y>): LiveData<Pair<T, Y>> {
    return zip(first, second) { t, y -> Pair(t, y) }
}

fun <T, Y, Z> zip(first: LiveData<T>, second: LiveData<Y>, zipFunction: (T, Y) -> Z): LiveData<Z> {
    val finalLiveData: MediatorLiveData<Z> = MediatorLiveData()

    var firstEmitted = false
    var firstValue: T? = null

    var secondEmitted = false
    var secondValue: Y? = null
    finalLiveData.addSource(first) { value ->
        firstEmitted = true
        firstValue = value
        if (firstEmitted && secondEmitted) {
            finalLiveData.value = zipFunction(firstValue!!, secondValue!!)
            firstEmitted = false
            secondEmitted = false
        }
    }
    finalLiveData.addSource(second) { value ->
        secondEmitted = true
        secondValue = value
        if (firstEmitted && secondEmitted) {
            finalLiveData.value = zipFunction(firstValue!!, secondValue!!)
            firstEmitted = false
            secondEmitted = false
        }
    }
    return finalLiveData
}


/**
 * zips three LiveData and emits a value after all of them have emitted their values,
 * after that, emits values whenever any of them emits a value.
 *
 * The difference between combineLatest and zip is that the zip only emits after all LiveData
 * objects have a new value, but combineLatest will emit after any of them has a new value.
 * 在三个livedata都发出值后，zips三个livedata并发出一个值，
 * 之后，只要其中任何一个发出值，就会发出值。
 *
 * combinelast和zip的区别在于，zip毕竟只发出livedata
 * 对象有一个新值，但combineTest将在其中任何对象有一个新值后发出。
 */
fun <T, Y, X, Z> zip(first: LiveData<T>, second: LiveData<Y>, third: LiveData<X>, zipFunction: (T, Y, X) -> Z): LiveData<Z> {
    val finalLiveData: MediatorLiveData<Z> = MediatorLiveData()

    var firstEmitted = false
    var firstValue: T? = null

    var secondEmitted = false
    var secondValue: Y? = null

    var thirdEmitted = false
    var thirdValue: X? = null
    finalLiveData.addSource(first) { value ->
        firstEmitted = true
        firstValue = value
        if (firstEmitted && secondEmitted && thirdEmitted) {
            finalLiveData.value = zipFunction(firstValue!!, secondValue!!, thirdValue!!)
            firstEmitted = false
            secondEmitted = false
            thirdEmitted = false
        }
    }

    finalLiveData.addSource(second) { value ->
        secondEmitted = true
        secondValue = value
        if (firstEmitted && secondEmitted && thirdEmitted) {
            firstEmitted = false
            secondEmitted = false
            thirdEmitted = false
            finalLiveData.value = zipFunction(firstValue!!, secondValue!!, thirdValue!!)
        }
    }

    finalLiveData.addSource(third) { value ->
        thirdEmitted = true
        thirdValue = value
        if (firstEmitted && secondEmitted && thirdEmitted) {
            firstEmitted = false
            secondEmitted = false
            thirdEmitted = false
            finalLiveData.value = zipFunction(firstValue!!, secondValue!!, thirdValue!!)
        }
    }

    return finalLiveData
}

fun <T, Y, X> zip(first: LiveData<T>, second: LiveData<Y>, third: LiveData<X>): LiveData<Triple<T, Y, X>> {
    return zip(first, second, third) { t, y, x -> Triple(t, y, x) }
}

/**
 * Combines the latest values from two LiveData objects.
 * First emits after both LiveData objects have emitted a value, and will emit afterwards after any
 * of them emits a new value.
 *
 * The difference between combineLatest and zip is that the zip only emits after all LiveData
 * objects have a new value, but combineLatest will emit after any of them has a new value.
 * 组合来自两个livedata对象的最新值。
 * 首先在两个livedata对象都发出一个值之后发出，然后在任何
 * 它们中有一种新的价值。
 *
 * combinelast和zip的区别在于，zip毕竟只发出livedata
 * 对象有一个新值，但combineTest将在其中任何对象有一个新值后发出。
 */
fun <X, T, Z> combineLatest(first: LiveData<X>, second: LiveData<T>, combineFunction: (X, T) -> Z): LiveData<Z> {
    val finalLiveData: MediatorLiveData<Z> = MediatorLiveData()

    var firstEmitted = false
    var firstValue: X? = null

    var secondEmitted = false
    var secondValue: T? = null
    finalLiveData.addSource(first) { value ->
        firstEmitted = true
        firstValue = value
        if (firstEmitted && secondEmitted) {
            finalLiveData.value = combineFunction(firstValue!!, secondValue!!)
        }
    }
    finalLiveData.addSource(second) { value ->
        secondEmitted = true
        secondValue = value
        if (firstEmitted && secondEmitted) {
            finalLiveData.value = combineFunction(firstValue!!, secondValue!!)
        }
    }
    return finalLiveData
}

/**
 * Combines the latest values from two LiveData objects.
 * First emits after both LiveData objects have emitted a value, and will emit afterwards after any
 * of them emits a new value.
 *
 * The difference between combineLatest and zip is that the zip only emits after all LiveData
 * objects have a new value, but combineLatest will emit after any of them has a new value.
 * 组合来自两个livedata对象的最新值。
 * 首先在两个livedata对象都发出一个值之后发出，然后在任何
 * 它们中有一种新的价值。
 *
 * combinelast和zip的区别在于，zip毕竟只发出livedata
 * 对象有一个新值，但combineTest将在其中任何对象有一个新值后发出。
 */
fun <X, Y, T, Z> combineLatest(first: LiveData<X>, second: LiveData<Y>, third: LiveData<T>, combineFunction: (X, Y, T) -> Z): LiveData<Z> {
    val finalLiveData: MediatorLiveData<Z> = MediatorLiveData()

    var firstEmitted = false
    var firstValue: X? = null

    var secondEmitted = false
    var secondValue: Y? = null

    var thirdEmitted = false
    var thirdValue: T? = null
    finalLiveData.addSource(first) { value ->
        firstEmitted = true
        firstValue = value
        if (firstEmitted && secondEmitted && thirdEmitted) {
            finalLiveData.value = combineFunction(firstValue!!, secondValue!!, thirdValue!!)
        }
    }
    finalLiveData.addSource(second) { value ->
        secondEmitted = true
        secondValue = value
        if (firstEmitted && secondEmitted && thirdEmitted) {
            finalLiveData.value = combineFunction(firstValue!!, secondValue!!, thirdValue!!)
        }
    }
    finalLiveData.addSource(third) { value ->
        thirdEmitted = true
        thirdValue = value
        if (firstEmitted && secondEmitted && thirdEmitted) {
            finalLiveData.value = combineFunction(firstValue!!, secondValue!!, thirdValue!!)
        }
    }
    return finalLiveData
}

/**
 * Converts the LiveData to `SingleLiveData` and concats it with the `otherLiveData` and emits their
 * values one by one
 * 将livedata转换为“singlelivedata”，并将其与“otherlivedata”连接，并发出
 * 价值观一个接一个
 */
fun <T> LiveData<T>.then(otherLiveData: LiveData<T>): LiveData<T> {
    return if (this is SingleLiveData) {
        when (otherLiveData) {
            is SingleLiveData -> SingleLiveDataConcat(this, otherLiveData)
            else -> SingleLiveDataConcat(this, otherLiveData.toSingleLiveData())
        }
    } else {
        when (otherLiveData) {
            is SingleLiveData -> SingleLiveDataConcat(this.toSingleLiveData(), otherLiveData)
            else -> SingleLiveDataConcat(this.toSingleLiveData(), otherLiveData.toSingleLiveData())
        }
    }
}

fun <T> LiveData<T>.concatWith(otherLiveData: LiveData<T>) = then(otherLiveData)

/**
 * Concats the given LiveData together and emits their values one by one in order
 * 将给定的livedata连接在一起，并按顺序逐个发出它们的值
 */
fun <T> concat(vararg liveData: LiveData<T>): LiveData<T> {
    val liveDataList = mutableListOf<SingleLiveData<T>>()
    liveData.forEach {
        if (it is SingleLiveData<T>)
            liveDataList.add(it)
        else
            liveDataList.add(it.toSingleLiveData())
    }
    return SingleLiveDataConcat(liveDataList)
}

/**
 * Samples the current live data with other live data, resulting in a live data that emits the last
 * value emitted by the original live data (if there were any values emitted) whenever the other live
 * data emits
 * 使用其他实时数据对当前实时数据进行采样，从而生成一个实时数据，该数据发出最后一个
 * 原始实时数据发出的值（如果有任何值发出），只要另一个实时数据
 * 数据发射
 */
fun <T> LiveData<T>.sampleWith(other: LiveData<*>): LiveData<T> {
    val finalLiveData: MediatorLiveData<T> = MediatorLiveData()
    val hasValueToConsume = AtomicBoolean(false)
    var latestValue: T? = null
    finalLiveData.addSource(this) {
        hasValueToConsume.set(true)
        latestValue = it
    }
    finalLiveData.addSource(other) {
        if (hasValueToConsume.compareAndSet(true, false)) {
            finalLiveData.value = latestValue
        }
    }
    return finalLiveData
}