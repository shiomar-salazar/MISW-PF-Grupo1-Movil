package com.sportapp_grupo1.validator

import com.sportapp_grupo1.R
import com.sportapp_grupo1.validator.base.BaseValidator
import com.sportapp_grupo1.validator.base.ValidateResult

class TimeValidator(private val time: String) : BaseValidator() {

    override fun validate(): ValidateResult {
        if (time.contains(":").not())
            return ValidateResult(false, R.string.text_validation_error_time_format)
        if (time.contains(Regex("[A-Z]+")))
            return ValidateResult(false, R.string.text_validation_error_time_letters)
        if (time.contains(Regex("[a-z]+")))
            return ValidateResult(false, R.string.text_validation_error_time_letters)
        if (time.contains(Regex("[\$&+,;=?@#|'.<>^*()%!-]+")))
            return ValidateResult(false, R.string.text_validation_error_time_format)
        return ValidateResult(true, R.string.text_validation_success)
    }
}