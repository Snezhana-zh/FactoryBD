package org.example.ui;

import org.example.model.Product;
import javax.swing.*;
import java.awt.*;

public class ProductForm extends JDialog {
    private final MainFrame mainFrame;
    private final Product product;

    private JTextField nameField;
    private JTextField priceField;
    private JTextArea descriptionArea;

    public ProductForm(MainFrame mainFrame, Product product) {
        super(mainFrame, "Product Form", true);
        this.mainFrame = mainFrame;
        this.product = product != null ? product : new Product();
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 300);
        setLocationRelativeTo(mainFrame);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Поля формы
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea();
        panel.add(new JScrollPane(descriptionArea));

        // Кнопки
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveProduct());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Заполняем поля, если редактируем существующий продукт
        if (product.getId() != null) {
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            descriptionArea.setText(product.getDescription());
        }

        // Добавляем компоненты на форму
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveProduct() {
        try {
            product.setName(nameField.getText());
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setDescription(descriptionArea.getText());

            mainFrame.saveProduct(product);
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}