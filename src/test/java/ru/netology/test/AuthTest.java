package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.Registration.getUser;
import static ru.netology.data.DataGenerator.getRandomLogin;
import static ru.netology.data.DataGenerator.getRandomPassword;


class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");}

    @Test
    void shouldBeRegisteredActiveUser() throws InterruptedException {
        var registeredUser = getRegisteredUser("active");
        $("[name='login'].input__control").clear();
        $("[name='login'].input__control").setValue(registeredUser.getLogin());
        $("[name='password'].input__control").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'].button").click();
        $("[id='root']").shouldHave(Condition.exactText("  Личный кабинет"));
    }

    @Test
    void shouldBeNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[name='login'].input__control").clear();
        $("[name='login'].input__control").setValue(notRegisteredUser.getLogin());
        $("[name='password'].input__control").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login'].button").click();
        $("[data-test-id='error-notification'].notification").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldBeBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[name='login'].input__control").clear();
        $("[name='login'].input__control").setValue(blockedUser.getLogin());
        $("[name='password'].input__control").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login'].button").click();
        $("[data-test-id='error-notification'].notification").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldBeWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[name='login'].input__control").clear();
        $("[name='login'].input__control").setValue(wrongLogin);
        $("[name='password'].input__control").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'].button").click();
        $("[data-test-id='error-notification'].notification").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldBeWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[name='login'].input__control").clear();
        $("[name='login'].input__control").setValue(registeredUser.getLogin());
        $("[name='password'].input__control").setValue(wrongPassword);
        $("[data-test-id='action-login'].button").click();
        $("[data-test-id='error-notification'].notification").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}

