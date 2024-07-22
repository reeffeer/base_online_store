package com.reef.base_online_store;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("login")
public class LoginFormView extends FormLayout {
    private LoginForm loginForm;
    private UserService userService;

    public LoginFormView(UserService userService) {
        this.userService = userService;
        loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.setAction("login");

        LoginI18n i18n = createLoginI18n();
        loginForm.setI18n(i18n);

        // Добавление обработчика события для кнопки входа
        loginForm.addLoginListener(this::loginUser);

        add(loginForm);
    }

    private LoginI18n createLoginI18n() {
        LoginI18n i18n = new LoginI18n();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Мой Интернет Магазин");
        i18n.getHeader().setDescription("Войдите, чтобы продолжить");

        // Задайте другие локализационные настройки, такие как сообщения об ошибках и метки полей, по необходимости.

        return i18n;
    }

    private void loginUser(AbstractLogin.LoginEvent event) {
        String username = event.getUsername();
        String password = event.getPassword();

        boolean isAuthenticated = userService.authenticate(username, password);

        if (isAuthenticated) {
            getUI().ifPresent(ui -> ui.navigate("catalog"));
        } else {
            // Отобразить сообщение об ошибке в форме входа
            loginForm.setError(true);
        }
    }
}