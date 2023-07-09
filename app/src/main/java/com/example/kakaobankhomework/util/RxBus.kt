package com.example.kakaobankhomework.util

import io.reactivex.rxjava3.subjects.PublishSubject

object RxBus {
    val onBookmarkRemoved = PublishSubject.create<String>()
}
