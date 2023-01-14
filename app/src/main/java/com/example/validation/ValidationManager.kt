package com.example.validation

import android.content.Context
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class ValidationManager private constructor() {
    private var textInputLayout: TextInputLayout? = null
    private var editText: EditText? = null
    private var errorSetter: ErrorSetter? = null
    private val phoneNumberDigits = 10
    private val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    private var isEmpty = true
    private val ERR_MSG_CHECK_EMPTY = "Field can not be empty."
    private val ERR_MSG_CHECK_PHONE_NUMBER = "Please enter 10 digit phone number."
    private val ERR_MSG_CHECK_EMAIL = "Invalid email."
    private val ERR_MSG_MATCH_PASSWORD = "Password does not match."
    var isAllValid = false
        get() {
            field = isEmptyValid && isEmailValid && isPhoneNumberValid && isPasswordValid
            return field
        }
        private set
    private var isEmptyValid = false
    private var isEmailValid = false
    private var isPhoneNumberValid = false
    private var isPasswordValid = false

    internal interface ErrorSetter {
        fun setError(textInputLayout: TextInputLayout?, errorMsg: String?)
    }

    fun doValidation(context: Context?, textInputLayout: TextInputLayout): ValidationManager? {
        errorSetter = context as ErrorSetter?
        this.textInputLayout = textInputLayout
        editText = textInputLayout.editText
        return instance
    }

    fun checkEmpty(): ValidationManager? {
        if (editText!!.text.toString().isEmpty()) {
            errorSetter!!.setError(textInputLayout, ERR_MSG_CHECK_EMPTY)
            isEmpty = true
            isEmptyValid = false
        } else {
            isEmpty = false
            isEmptyValid = true
        }
        return instance
    }

    fun checkPhoneNumber(): ValidationManager? {
        isPhoneNumberValid =
            if (!isEmpty && editText!!.text.toString().length != phoneNumberDigits) {
                errorSetter!!.setError(textInputLayout, ERR_MSG_CHECK_PHONE_NUMBER)
                false
            } else {
                true
            }
        return instance
    }

    fun checkEmail(): ValidationManager? {
        isEmailValid = if (!isEmpty && !editText!!.text.toString().matches(emailPattern)) {
            errorSetter!!.setError(textInputLayout, ERR_MSG_CHECK_EMAIL)
            false
        } else {
            true
        }
        return instance
    }

    fun matchPassword(
        firstPasswordLayout: TextInputLayout,
        secondPasswordLayout: TextInputLayout
    ): ValidationManager? {
        isPasswordValid = if (!isEmpty && firstPasswordLayout.editText!!
                .text.toString() != secondPasswordLayout.editText!!.text.toString()
        ) {
            errorSetter!!.setError(textInputLayout, ERR_MSG_MATCH_PASSWORD)
            false
        } else {
            true
        }
        return instance
    }

    companion object {
        private var instance: ValidationManager? = null
        fun getInstance(): ValidationManager? {
            if (instance == null) {
                instance = ValidationManager()
            }
            return instance
        }
    }
}