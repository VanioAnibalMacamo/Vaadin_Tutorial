package com.example.application.it;

import com.vaadin.flow.component.login.testbench.LoginFormElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Macamo, Vanio Anibal
 * @Date 1/11/2023
 */
public class LoginIT extends AbstractTest{
    public LoginIT(String route) {
        super("");
    }
    @Test
    public void loginAsValidUserSucceeds() {
        // Find the LoginForm used on the page
        LoginFormElement form = $(LoginFormElement.class).first();
        // Enter the credentials and log in
        form.getUsernameField().setValue("user");
        form.getPasswordField().setValue("userpass");
        form.getSubmitButton().click();
        // Ensure the login form is no longer visible
        Assert.assertFalse($(LoginFormElement.class).exists());
    }
}
