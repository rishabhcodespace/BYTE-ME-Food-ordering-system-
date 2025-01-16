package com.byteMe;

import com.byteMe.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoginTests {
    private static List<Customer> registeredCustomers;

    @BeforeEach
    void setUp() {
        // Setup test data
        registeredCustomers = new ArrayList<>();
        registeredCustomers.add(new Customer("test@customer.com", "password123", "Test User",
                "1234567890", "Test Address", "Male", false));
    }

    // Test for invalid customer email
    @Test
    void testInvalidCustomerEmail() {
        String invalidEmail = "invalid@customer.com";
        String password = "password123";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticateCustomer(invalidEmail, password);
        });

        assertEquals("Email not registered.", exception.getMessage());
    }

    // Test for invalid customer password
    @Test
    void testInvalidCustomerPassword() {
        String validEmail = "test@customer.com";
        String invalidPassword = "wrongpassword";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticateCustomer(validEmail, invalidPassword);
        });

        assertEquals("Invalid password.", exception.getMessage());
    }

    // Test for valid customer login
    @Test
    void testValidCustomerLogin() {
        String validEmail = "test@customer.com";
        String validPassword = "password123";

        assertDoesNotThrow(() -> {
            Customer customer = authenticateCustomer(validEmail, validPassword);
            assertNotNull(customer);
        });
    }

    // Test for invalid admin email
    @Test
    void testInvalidAdminEmail() {
        String invalidEmail = "wrong@admin.com";
        String validPassword = "admin1234";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validateAdminCredentials(invalidEmail, validPassword);
        });

        assertEquals("Invalid admin email.", exception.getMessage());
    }

    // Test for invalid admin password
    @Test
    void testInvalidAdminPassword() {
        String validEmail = "admin@byteme";
        String invalidPassword = "wrongpassword";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validateAdminCredentials(validEmail, invalidPassword);
        });

        assertEquals("Invalid admin password.", exception.getMessage());
    }

    // Test for valid admin login
    @Test
    void testValidAdminLogin() {
        String validEmail = "admin@byteme";
        String validPassword = "admin1234";

        assertDoesNotThrow(() -> validateAdminCredentials(validEmail, validPassword));
    }

    // Utility method for customer authentication
    private Customer authenticateCustomer(String email, String password) {
        for (Customer customer : registeredCustomers) {
            if (customer.getEmail().equals(email)) {
                if (customer.getPassword().equals(password)) {
                    return customer; // Login successful
                } else {
                    throw new IllegalArgumentException("Invalid password."); // Password doesn't match
                }
            }
        }
        throw new IllegalArgumentException("Email not registered."); // No matching email found
    }

    // Utility method for admin authentication
    private void validateAdminCredentials(String email, String password) {
        if (!email.equals("admin@byteme")) {
            throw new IllegalArgumentException("Invalid admin email.");
        }
        if (!password.equals("admin1234")) {
            throw new IllegalArgumentException("Invalid admin password.");
        }
    }
}
