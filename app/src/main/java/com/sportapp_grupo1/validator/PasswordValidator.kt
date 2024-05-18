package com.sportapp_grupo1.validator

import com.sportapp_grupo1.R
import com.sportapp_grupo1.validator.base.BaseValidator
import com.sportapp_grupo1.validator.base.ValidateResult

class PasswordValidator(private val password: String) : BaseValidator() {
    private val minPasswordLength = 8
    private val maxPasswordLength = 64

    override fun validate(): ValidateResult {
        if (password.length < minPasswordLength)
            return ValidateResult(false, R.string.text_validation_error_min_pass_length)
        if (password.length > maxPasswordLength)
            return ValidateResult(false, R.string.text_validation_error_max_pass_length)
        if (password.contains(" "))
            return ValidateResult(false, R.string.text_validation_error_blank_space)
        if (password.contains(Regex("[A-Z]+")).not())
            return ValidateResult(false, R.string.text_validation_error_uppercase)
        if (password.contains(Regex("[a-z]+")).not())
            return ValidateResult(false, R.string.text_validation_error_lowercase)
        if (password.contains(Regex("[\$&+,:;=?@#|'<>.^*()%!-]+")).not())
            return ValidateResult(false, R.string.text_validation_error_special_char)
        return ValidateResult(true, R.string.text_validation_success)
    }
}