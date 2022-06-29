package com.example.utils

import java.util.*

class UsersId {

    companion object{
        fun rand(start: Int, end: Int): Int {
            return (start..end).random()
        }
    }
}
