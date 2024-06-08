package systemTest;

import UI.CardInputUI;
import dvm.service.controller.card.CardServiceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardPaymentProcessingTest {

    private CardInputUI cardInputUI;
    private boolean successCalled;
    private boolean retryCalled;
    private CardServiceController mockCardServiceController;

    @BeforeEach
    public void setUp() throws Exception {
        successCalled = false;
        retryCalled = false;

        // Mock the CardServiceController
        mockCardServiceController = mock(CardServiceController.class);

        cardInputUI = new CardInputUI(500,
                () -> successCalled = true,
                () -> retryCalled = true) {
            @Override
            protected CardServiceController createCardServiceController() {
                return mockCardServiceController;
            }
        };

        // Show the UI for testing
        cardInputUI.setVisible(true);

        // Adding sleep to allow visual inspection
        Thread.sleep(2000); // 2 seconds
    }

    @Test
    public void testValidCardPayment() throws Exception {
        // Set up the mock to return true for valid card details
        when(mockCardServiceController.requestPayment("validCardDetails", 500)).thenReturn(true);

        // Simulate entering valid card details and pressing OK
        JTextField cardInputField = (JTextField) findComponentByName(cardInputUI, "cardInputField");
        cardInputField.setText("validCardDetails");

        JButton okButton = (JButton) findComponentByName(cardInputUI, "okButton");
        Thread.sleep(2000); // Adding sleep to allow visual inspection
        okButton.doClick();

        // Adding sleep to allow visual inspection after click
        Thread.sleep(2000);

        // Verify the results
        assertTrue(successCalled, "Success callback should be called");
        assertFalse(retryCalled, "Retry callback should not be called");

        // Verify that the mock was called as expected
        verify(mockCardServiceController, times(1)).requestPayment("validCardDetails", 500);
    }

    @Test
    public void testInvalidCardPayment() throws Exception {
        // Set up the mock to return false for invalid card details
        when(mockCardServiceController.requestPayment("invalidCardDetails", 500)).thenReturn(false);

        // Simulate entering invalid card details and pressing OK
        JTextField cardInputField = (JTextField) findComponentByName(cardInputUI, "cardInputField");
        cardInputField.setText("invalidCardDetails");

        JButton okButton = (JButton) findComponentByName(cardInputUI, "okButton");
        Thread.sleep(2000); // Adding sleep to allow visual inspection
        okButton.doClick();

        // Adding sleep to allow visual inspection after click
        Thread.sleep(2000);

        // Verify the results
        assertFalse(successCalled, "Success callback should not be called");
        assertTrue(retryCalled, "Retry callback should be called");

        // Verify that the mock was called as expected
        verify(mockCardServiceController, times(1)).requestPayment("invalidCardDetails", 500);
    }

    private Component findComponentByName(Container container, String componentName) {
        for (Component component : container.getComponents()) {
            if (componentName.equals(component.getName())) {
                return component;
            } else if (component instanceof Container) {
                Component childComponent = findComponentByName((Container) component, componentName);
                if (childComponent != null) {
                    return childComponent;
                }
            }
        }
        return null;
    }
}
