package org.example.ui.products;

import org.example.dao.BaseDao;
import org.example.model.*;
import org.example.dao.ProductModelDao;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductForm extends BaseForm {
    private final BaseDao<Product> productDao;
    private final ProductModelDao productModelDao;
    private Product product;

    private JTextField nameField;
    private JTextField priceField;
    private JComboBox<ProductModel> modelComboBox;

    public ProductForm(JFrame parent, Product product) {
        super(parent, "Product Form", true);
        this.productDao = new BaseDao<Product>(Product.class);
        this.productModelDao = new ProductModelDao();
        this.product = product != null ? product : new Product();

        initializeUI();
        loadModels();
    }

    private void initializeUI() {
        setLayout(new GridLayout(0, 2, 10, 10));
        setSize(400, 300);

        // Поля формы
        /*add(new JLabel("Name:"));
        nameField = new JTextField(product.getName());
        add(nameField);

        add(new JLabel("Price:"));
        priceField = new JTextField(product.getPrice() != null ? product.getPrice().toString() : "");
        add(priceField);*/

//        add(new JLabel("ID:"));
//        nameField = new JTextField(product.getId().toString());
//        add(nameField);

        add(new JLabel("Model:"));
        modelComboBox = new JComboBox<>();
        modelComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ProductModel) {
                    ProductModel model = (ProductModel) value;
                    setText(model.getTitle());
                }
                return this;
            }
        });
        add(modelComboBox);

        // Кнопки
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveProduct());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void loadModels() {
        List<ProductModel> models = productModelDao.getAllModels();
        models.forEach(model -> modelComboBox.addItem(model));

        // Устанавливаем текущую модель продукта, если она есть
        if (product.getModel() != null) {
            modelComboBox.setSelectedItem(product.getModel());
        }
    }

    private void saveProduct() {
        try {
            /*product.setName(nameField.getText());
            product.setPrice(Double.parseDouble(priceField.getText()));*/

            // Устанавливаем выбранную модель
            ProductModel selectedModel = (ProductModel) modelComboBox.getSelectedItem();
            product.setModel(selectedModel);

            if (product.getId() == null) {
                productDao.save(product);
            } else {
                productDao.update(product);
            }

            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}