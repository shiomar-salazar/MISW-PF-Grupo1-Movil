package com.sportapp_grupo1

import com.sportapp_grupo1.validator.PasswordValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PasswordValidatorUnitTest {

    @Test fun password_minLength() {
        assertFalse(PasswordValidator("1234").validate().isSuccess)
    }
    @Test fun password_maxLength() {
        assertFalse(PasswordValidator("QQZanuV8G8zJ66dPnDiYLicQ4B8Hr5NNXGpVCywJSV6UUHP7Wx4GXzj9ULAMc6g35").validate().isSuccess)
    }
    @Test fun password_noLower() {
        assertFalse(PasswordValidator("123456789A").validate().isSuccess)
    }
    @Test fun password_noUpper() {
        assertFalse(PasswordValidator("123456789a").validate().isSuccess)
    }
    @Test fun password_noSpecialChar() {
        assertTrue(PasswordValidator("123456789Aa").validate().isSuccess)
    }
    @Test fun password_correct() {
        assertTrue(PasswordValidator("123456789Aa-").validate().isSuccess)
    }

}