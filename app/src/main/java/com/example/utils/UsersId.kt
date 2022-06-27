package com.example.utils

import java.util.*

class UsersId {

    companion object{
        val givenList  = (1..10).toList()
        var rand: Random? = Random()
        var randomUserId = givenList[rand!!.nextInt(givenList.size)]
    }
}