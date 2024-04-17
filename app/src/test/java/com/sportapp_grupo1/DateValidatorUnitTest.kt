package com.sportapp_grupo1

import com.sportapp_grupo1.validator.DateValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DateValidatorUnitTest {

    @Test fun date_length_min(){
        assertFalse(DateValidator("2024-12").validate().isSuccess)
    }
    @Test fun date_length_max(){
        assertFalse(DateValidator("2024-12-10-5").validate().isSuccess)
    }
    @Test fun date_length_symbol(){
        assertFalse(DateValidator("2024/12/10").validate().isSuccess)
    }
    @Test fun date_length_blankSpace(){
        assertFalse(DateValidator("2024/12 10").validate().isSuccess)
    }
    @Test fun date_length_month(){
        assertFalse(DateValidator("2024-20-10").validate().isSuccess)
    }
    @Test fun date_length_day(){
        assertFalse(DateValidator("2024-18-50").validate().isSuccess)
    }
    @Test fun date_correct() {
        assertTrue(DateValidator("2024-04-15").validate().isSuccess)
    }
}