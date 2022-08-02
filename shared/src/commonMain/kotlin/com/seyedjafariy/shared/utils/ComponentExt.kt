package com.seyedjafariy.shared.utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.rx.Disposable

fun ComponentContext.disposeOnDestroy(disposable: Disposable) {
    lifecycle.doOnDestroy {
        disposable.dispose()
    }
}