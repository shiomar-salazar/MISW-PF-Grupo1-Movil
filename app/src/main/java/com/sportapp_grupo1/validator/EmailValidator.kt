package com.sportapp_grupo1.validator

import android.text.TextUtils
import com.sportapp_grupo1.R
import com.sportapp_grupo1.validator.base.BaseValidator
import com.sportapp_grupo1.validator.base.ValidateResult

class EmailValidator(private val email: String) : BaseValidator() {
    override fun validate(): ValidateResult {
        val isValid =
            !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        return ValidateResult(
            isValid,
            if (isValid) R.string.text_validation_success else R.string.text_validation_error_email
        )
    }
}