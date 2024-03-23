package com.sportapp_grupo1.test.utils

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.core.AllOf

//Constante que define el tiempo de espera para que se carguen los datos retornados por el adapter
val delayService = Integer.toUnsignedLong(5000)
val delayService2 = Integer.toUnsignedLong(1000)

fun clickIntoButtonById(idView: Int) {
    //Damos click en el boton idView
    Espresso.onView(ViewMatchers.withId(idView)).perform(ViewActions.click())
}

fun clickIntoButtonByText(idView: Int, valueToSearch: String) {
    //Damos click en el boton idView
    getTextViewByValue(idView, valueToSearch)?.perform(ViewActions.click())
}

fun getTextViewByValue(idView: Int, valueToSearch: String): ViewInteraction? {
    //Validamos si existe un TextView de tipo idView con el texto valueToSearch
    return Espresso.onView(
        AllOf.allOf(
            ViewMatchers.withId(idView),
            ViewMatchers.withText(valueToSearch)
        )
    )
}

fun setTextLayoutViewByValue(idView: Int, valueToType:String) {
    //Validamos si existe un TextView de tipo idView con el texto valueToSearch
    Espresso.onView(
        AllOf.allOf(
            ViewMatchers.isDescendantOfA(ViewMatchers.withId(idView)),
            ViewMatchers.withClassName(CoreMatchers.endsWith("EditText"))
        )
    ).perform(
        ViewActions.typeText(valueToType)
    )
}

fun setTextViewByValue(idView: Int, valueToType:String) {
    //Validamos si existe un TextView de tipo idView con el texto valueToSearch
    Espresso.onView(
        AllOf.allOf(
            ViewMatchers.withId(idView)
        )
    ).perform(
        ViewActions.click(),
        ViewActions.clearText(),
        ViewActions.typeText(valueToType),
        ViewActions.closeSoftKeyboard()
    )
}