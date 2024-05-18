package com.sportapp_grupo1.validator

import com.sportapp_grupo1.R
import com.sportapp_grupo1.validator.base.BaseValidator
import com.sportapp_grupo1.validator.base.ValidateResult

class EmptyValidator(private val input: String) : BaseValidator() {
    override fun validate(): ValidateResult {
        val isValid = input.isNotEmpty()
        return ValidateResult(
            isValid,
            if (isValid) R.string.text_validation_success else R.string.text_validation_error_empty_field
        )
    }
}