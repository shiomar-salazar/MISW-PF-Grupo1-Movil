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
    fun time_nocolon() {
        Assert.assertFalse(TimeValidator("12.34").validate().isSuccess)
    }

    @Test
    fun time_text() {
        Assert.assertFalse(TimeValidator("12.34:1h").validate().isSuccess)
    }

    @Test
    fun time_extra_simbols() {
        Assert.assertFalse(TimeValidator("12.34:11-").validate().isSuccess)
    }

    @Test fun time_correct() {
        Assert.assertTrue(TimeValidator("12:34:56").validate().isSuccess)
    }
}