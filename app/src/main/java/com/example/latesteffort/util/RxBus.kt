package com.example.latesteffort.util

import io.reactivex.rxjava3.subjects.PublishSubject

object RxBus {
    val onBookmarkRemoved = PublishSubject.create<String>()
}
