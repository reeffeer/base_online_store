package com.reef.base_online_store;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Route("registration")
public class RegistrationView extends VerticalLayout {
    private TextField usernameField;
    private PasswordField passwordField;
    private Button registerButton;

    private UserRepository userRepository;

    @Autowired
    public RegistrationView(UserRepository userRepository) {
        this.userRepository = userRepository;

        FormLayout formLayout = new FormLayout();
        usernameField = new TextField("Имя пользователя");
        passwordField = new PasswordField("Пароль");
        registerButton = new Button("Зарегистрироваться");

        registerButton.addClickListener(event -> registerUser());

        formLayout.add(usernameField, passwordField, registerButton);
        add(formLayout);
    }

    private void registerUser() {
        String username = usernameField.getValue();
        String password = passwordField.getValue();

        // Хэшируем пароль перед сохранением в базе данных
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        // Создаем новый объект пользователя
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);

        // Сохраняем пользователя в базе данных
        userRepository.save(newUser);

        // Перенаправляем пользователя на другую страницу, например, на страницу каталога товаров
        getUI().ifPresent(ui -> ui.navigate("catalog"));
    }
}