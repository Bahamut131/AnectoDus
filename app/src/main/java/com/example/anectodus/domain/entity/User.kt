package com.example.anectodus.domain.entity

abstract class User {

    abstract val email: String
    abstract val id: String
    abstract val userName: String


    class Base(override val email: String, override val id: String, override val userName: String) : User()

    object Empty : User() {
        override val email = "Empty"
        override val id = "Empty_id"
        override val userName = "Empty_user"

    }
}