package com.seyedjafariy.shared.utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.mvikotlin.rx.Disposable

infix fun Disposable.addTo(disposables: MutableSet<Disposable>) {
    disposables.add(this)
}

val ComponentContext.createdDisposables: MutableSet<Disposable>
    get() = instanceKeeper.getOrCreate(KEY_CREATED_DISPOSABLE) {
        DisposableInstance(mutableSetOf())
    }.disposables

private class DisposableInstance(
    val disposables: MutableSet<Disposable>
) : InstanceKeeper.Instance {
    override fun onDestroy() {
        disposables.removeAll {
            it.dispose()
            true
        }
    }
}

private const val KEY_CREATED_DISPOSABLE = "KEY_CREATED_DISPOSABLE"
