package com.yyxnb.arch.bean

import com.yyxnb.arch.interfaces.LceeStatus

class Lcee<T>(@field:LceeStatus
              val status: Int?, val data: T?, val error: String?) {
    companion object {

        fun <T> content(data: T?): Lcee<T> {
            return Lcee(LceeStatus.Content, data, null)
        }

        fun <T> error(data: T?, error: String?): Lcee<T> {
            return Lcee<T>(LceeStatus.Error, data, error)
        }

        fun <T> error(error: String?): Lcee<T> {
            return error(null, error)
        }

        fun <T> empty(data: T?): Lcee<T> {
            return Lcee<T>(LceeStatus.Empty, data, null)
        }

        fun <T> empty(): Lcee<T> {
            return empty(null)
        }

        fun <T> loading(data: T?): Lcee<T> {
            return Lcee<T>(LceeStatus.Loading, data, null)
        }

        fun <T> loading(): Lcee<T> {
            return loading(null)
        }
    }

}
