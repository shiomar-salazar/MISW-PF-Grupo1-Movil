package com.sportapp_grupo1.validator

import com.sportapp_grupo1.R
import com.sportapp_grupo1.validator.base.BaseValidator
import com.sportapp_grupo1.validator.base.ValidateResult

class DateValidator (val date: String) : BaseValidator() {
    private val length = 10
    private val day_max = 31
    private val month_max = 12
    private val min = 1

    override fun validate(): ValidateResult {
        if (date.length != length)
            return ValidateResult(false, R.string.text_validation_date_format)
        if (date.contains(" "))
            return ValidateResult(false, R.string.text_validation_date_blank_space)
        if (date.contains(Regex("[\$&+,:;=?@#|'<>./^*()%!]+")))
            return ValidateResult(false, R.string.text_validation_date_format)
        if((date.substring(5,7).toInt() > month_max) or (date.substring(5,7).toInt() < min) )
            return ValidateResult(false, R.string.text_validation_date_month)
        if((date.substring(8,10).toInt() > day_max) or (date.substring(8,10).toInt() < min) )
            return ValidateResult(false, R.string.text_validation_date_day)
        return ValidateResult(true, R.string.text_validation_success)
    }
}