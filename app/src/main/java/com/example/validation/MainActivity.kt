package com.example.validation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextWatcher,
ValidationManager.ErrorSetter {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSubmit.setOnClickListener {
            ValidationManager.getInstance()!!
                .doValidation(this@MainActivity, tilEmpty)!!.checkEmpty()
            ValidationManager.getInstance()!!.doValidation(
                this@MainActivity,
                tilNumberofDigits
            )!!.checkEmpty()
                ?.checkPhoneNumber()
            ValidationManager.getInstance()!!
                .doValidation(this@MainActivity, tilEmail)!!.checkEmpty()
                ?.checkEmail()
            ValidationManager.getInstance()!!
                .doValidation(this@MainActivity, tilPassword)!!.checkEmpty()
            ValidationManager.getInstance()!!
                .doValidation(this@MainActivity, tilRePassword)!!.checkEmpty()
                ?.matchPassword(tilPassword, tilRePassword)
            if (ValidationManager.getInstance()!!.isAllValid) {
                Toast.makeText(this@MainActivity, "All valid.", Toast.LENGTH_SHORT).show()
            }
        }

        setTextWatcher()
    }

    private fun setTextWatcher() {
        tilEmpty.editText!!.addTextChangedListener(this)
        tilEmail.editText!!.addTextChangedListener(this)
        tilNumberofDigits.editText!!.addTextChangedListener(this)
        tilPassword.editText!!.addTextChangedListener(this)
        tilRePassword.editText!!.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(editable: Editable?) {
        if (editable.hashCode() == tilEmpty.editText?.text.hashCode()) {
            tilEmpty.isErrorEnabled = false
        } else if (editable.hashCode() == tilNumberofDigits.editText?.text.hashCode()) {
            tilNumberofDigits.isErrorEnabled = false
        } else if (editable.hashCode() == tilEmail.editText?.text.hashCode()) {
            tilEmail.isErrorEnabled = false
        } else if (editable.hashCode() == tilPassword.editText?.text.hashCode()) {
            tilPassword.isErrorEnabled = false
        } else if (editable.hashCode() == tilRePassword.editText?.text.hashCode()) {
            tilRePassword.isErrorEnabled = false
        }
    }

    override fun setError(textInputLayout: TextInputLayout?, errorMsg: String?) {
        textInputLayout?.error = errorMsg
    }
}