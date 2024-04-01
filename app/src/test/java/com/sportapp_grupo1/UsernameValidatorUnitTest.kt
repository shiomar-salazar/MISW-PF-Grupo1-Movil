package com.sportapp_grupo1

import com.sportapp_grupo1.validator.EmailValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class UsernameValidatorUnitTest {
  @Test fun username_noAt() {
    assertFalse(EmailValidator("s.salazarcuniandes.edu.co").validate().isSuccess)
    System.out.println("-- Test File:UsernameValidatorUnitTest.kt - Unit Test:username_noAt ----- > Result: Passed")
  }
  @Test fun username_noDomain() {
    assertFalse(EmailValidator("s.salazarc@uniandeseduco").validate().isSuccess)
    System.out.println("-- Test File:UsernameValidatorUnitTest.kt - Unit Test:username_noDomain ----- > Result: Passed")
  }
  @Test fun username_empty() {
    assertFalse(EmailValidator("").validate().isSuccess)
    System.out.println("-- Test File:UsernameValidatorUnitTest.kt - Unit Test:username_empty ----- > Result: Passed")
  }
  @Test fun username_emptySpace() {
    assertFalse(EmailValidator("s.salazarc@ uniandes.edu.co").validate().isSuccess)
    System.out.println("-- Test File:UsernameValidatorUnitTest.kt - Unit Test:username_emptySpace ----- > Result: Passed")
  }
  @Test fun username_noName() {
    assertFalse(EmailValidator("@uniandes.edu.co").validate().isSuccess)
    System.out.println("-- Test File:UsernameValidatorUnitTest.kt - Unit Test:username_noName ----- > Result: Passed")
  }
  @Test fun username_correct() {
    assertTrue(EmailValidator("s.salazarc@uniandes.edu.co").validate().isSuccess)
    System.out.println("-- Test File:UsernameValidatorUnitTest.kt - Unit Test:username_correct ----- > Result: Passed")
  }
}