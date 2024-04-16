package com.sportapp_grupo1

import com.sportapp_grupo1.validator.TimeValidator
import org.junit.Assert
import org.junit.Test

class TimeValidatorUnitTest {
    @Test
    fun time_nopunctuation() {
        Assert.assertFalse(TimeValidator("1234").validate().isSuccess)
    }

    @Test
    fun password_nodot() {
        Assert.assertFalse(TimeValidator("12:34").validate().isSuccess)
    }

    @Test
    fun password_nocolon() {
        Assert.assertFalse(TimeValidator("12.34").validate().isSuccess)
    }

    @Test
    fun password_notext() {
        Assert.assertFalse(TimeValidator("12.34.1h").validate().isSuccess)
    }

    @Test
    fun password_nosimbols() {
        Assert.assertFalse(TimeValidator("12.34.11-").validate().isSuccess)
    }

    @Test fun time_correct() {
        Assert.assertTrue(TimeValidator("12:34.56").validate().isSuccess)
    }
}