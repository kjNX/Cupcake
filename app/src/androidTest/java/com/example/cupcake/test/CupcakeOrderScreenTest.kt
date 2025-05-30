package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.SelectOptionScreen
import org.junit.Rule
import org.junit.Test

class CupcakeOrderScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_verifyContent() {
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subtotal = "$100"

        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }

        flavors.forEach { flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                com.example.cupcake.R.string.subtotal_price,
                subtotal
            )
        ).assertIsDisplayed()

        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next).assertIsNotEnabled()
    }

    @Test
    fun selectOptionScreen_optionSelected_NextButtonEnabled() {
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subTotal = "$100"

        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subTotal, options = flavors)
        }

        composeTestRule.onNodeWithText("Vanilla").performClick()

        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next).assertIsEnabled()
    }

    @Test
    fun summaryScreen_verifyContentDisplay() {
        val mockOrderUiState = OrderUiState(
            quantity = 6,
            flavor = "Vanilla",
            date = "Wed Jul 21",
            price = "$100",
            pickupOptions = listOf()
        )

        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = mockOrderUiState,
                onCancelButtonClicked = {},
                onSendButtonClicked = { _, _ -> },
            )
        }

        composeTestRule.onNodeWithText(mockOrderUiState.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText(mockOrderUiState.date).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                com.example.cupcake.R.string.subtotal_price,
                mockOrderUiState.price
            )
        ).assertIsDisplayed()
    }
}