package com.sportapp_grupo1

import com.sportapp_grupo1.validator.PasswordValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PasswordValidatorUnitTest {

    @Test fun password_minLength() {
        assertFalse(PasswordValidator("1234").validate().isSuccess)
        System.out.println("-- Test File:PasswordValidatorUnitTest.kt - Unit Test:password_minLength ----- > Result: Passed")
    }
    @Test fun password_maxLength() {
        assertFalse(PasswordValidator("QQZanuV8G8zJ66dPnDiYLicQ4B8Hr5NNXGpVCywJSV6UUHP7Wx4GXzj9ULAMc6g35").validate().isSuccess)
        System.out.println("-- Test File:PasswordValidatorUnitTest.kt - Unit Test:password_maxLength ----- > Result: Passed")
    }
    @Test fun password_noLower() {
        assertFalse(PasswordValidator("123456789A").validate().isSuccess)
        System.out.println("-- Test File:PasswordValidatorUnitTest.kt - Unit Test:password_noLower ----- > Result: Passed")
    }
    @Test fun password_noUpper() {
        assertFalse(PasswordValidator("123456789a").validate().isSuccess)
        System.out.println("-- Test File:PasswordValidatorUnitTest.kt - Unit Test:password_noUpper ----- > Result: Passed")
    }
    @Test fun password_noSpecialChar() {
        assertFalse(PasswordValidator("123456789Aa").validate().isSuccess)
        System.out.println("-- Test File:PasswordValidatorUnitTest.kt - Unit Test:password_noSpecialChar ----- > Result: Passed")
    }
    @Test fun password_correct() {
        assertTrue(PasswordValidator("123456789Aa-").validate().isSuccess)
        System.out.println("-- Test File:PasswordValidatorUnitTest.kt - Unit Test:password_correct ----- > Result: Passed")
    }

}