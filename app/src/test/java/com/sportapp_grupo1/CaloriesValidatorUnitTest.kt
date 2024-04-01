package com.sportapp_grupo1

import com.sportapp_grupo1.validator.PasswordValidator
import com.sportapp_grupo1.validator.PlanAlimentacionValidator
import org.junit.Assert
import org.junit.Test

class CaloriesValidatorUnitTest {

    @Test
    fun calories_lower_than_500() {
        Assert.assertFalse(PlanAlimentacionValidator("300").validate().isSuccess)
        System.out.println("-- Test File:CaloriesValidatorUnitTest.kt - Unit Test:calories_lower_than_500 Test ----- > Result: Passed")
    }
    @Test
    fun calories_greater_than_500() {
        Assert.assertFalse(PasswordValidator("1000").validate().isSuccess)
        System.out.println("-- Test File:CaloriesValidatorUnitTest.kt - Unit Test:calories_lower_than_500 Test ----- > Result: Passed")
    }
}