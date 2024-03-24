package com.sportapp_grupo1.validator

import com.sportapp_grupo1.R
import com.sportapp_grupo1.validator.base.BaseValidator
import com.sportapp_grupo1.validator.base.ValidateResult

class PlanAlimentacionValidator(val calories: String) : BaseValidator() {

    private val minCalories = 500

    override fun validate(): ValidateResult {
        if (calories.toInt() < minCalories)
            return ValidateResult(false, R.string.text_validation_error_min_calories)
        return ValidateResult(true, R.string.text_validation_success)
    }

}