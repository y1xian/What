package com.yyxnb.common

fun CharSequence.toast() {
    CommonManager.toast(this.toString())
}

fun CharSequence.log(tag: String) {
    CommonManager.log(tag, this.toString())
}

fun CharSequence.log() {
    CommonManager.log(this.toString())
}