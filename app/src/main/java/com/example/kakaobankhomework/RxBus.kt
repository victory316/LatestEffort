package com.example.kakaobankhomework

import io.reactivex.rxjava3.subjects.PublishSubject

object RxBus {
    val onBookmarkRemoved = PublishSubject<String>()
}
