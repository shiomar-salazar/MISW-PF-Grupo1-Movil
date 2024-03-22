package com.sportapp_grupo1

import com.sportapp_grupo1.validator.EmailValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class UsernameValidatorUnitTest {
  @Test fun username_noAt() {
    assertFalse(EmailValidator("s.salazarcuniandes.edu.co").validate().isSuccess)
  }
  @Test fun username_noDomain() {
    assertFalse(EmailValidator("s.salazarc@uniandeseduco").validate().isSuccess)
  }
  @Test fun username_empty() {
    assertFalse(EmailValidator("").validate().isSuccess)
  }
  @Test fun username_emptySpace() {
    assertFalse(EmailValidator("s.salazarc@ uniandes.edu.co").validate().isSuccess)
  }
  @Test fun username_noName() {
    assertFalse(EmailValidator("@uniandes.edu.co").validate().isSuccess)
  }
  @Test fun username_correct() {
    assertTrue(EmailValidator("s.salazarc@uniandes.edu.co").validate().isSuccess)
  }
}