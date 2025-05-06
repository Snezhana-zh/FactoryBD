package org.example;

import org.example.ui.MainFrame;
import javax.swing.*;
/*import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;*/

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Устанавливаем Look and Feel системы
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        /*// Создаем фабрику сессий
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        // Создаем сессию
        Session session = sessionFactory.openSession();

        // Начинаем транзакцию
        session.beginTransaction();

        // Создаем пользователя
        User user = new User();
        user.setUsername("JohnDoe");
        user.setEmail("john@example.com");

        // Сохраняем в БД
        session.save(user);

        // Фиксируем транзакцию
        session.getTransaction().commit();

        // Закрываем сессию
        session.close();
        sessionFactory.close();*/
    }
}