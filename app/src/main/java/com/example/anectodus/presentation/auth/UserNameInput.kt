package com.example.anectodus.presentation.auth

import android.content.Context
import android.util.AttributeSet
import com.example.anectodus.R
import com.example.anectodus.presentation.customView.CustomInputLayout

class UserNameInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : CustomInputLayout(context, attrs, defStyleAttr) {


    override val errorMessageId: Int
        get() = R.string.user_name_error

    override fun innerIsValid(): Boolean {
        return text().matches(Regex(USERNAME_PATTERN))
    }

    companion object{
        const val USERNAME_PATTERN =  "^(?=.*?[A-Z])(?=.*?[a-z]).{6,}\$"
    }
}