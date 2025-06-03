package org.example.ui.products;

import org.example.dao.BaseDao;
import org.example.model.*;
import org.example.ui.BaseForm;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductModelForm extends BaseForm {
    private final JComboBox<Department> departmentComboBox;
    private final JComboBox<ProductCategory> categoryComboBox;
    private final ProductModel model;

    public ProductModelForm(JFrame parent, ProductModel model_) {
        super(parent, "Product Model Form", true);
        this.model = model_ != null ? model_ : new ProductModel();

        BaseDao<Department> departmentDao = new BaseDao<>(Department.class);
        BaseDao<ProductCategory> categoryDao = new BaseDao<>(ProductCategory.class);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        setSize(400, 300);

        // Поле названия
        JTextField titleField = new JTextField(model.getTitle());
        panel.add(new JLabel("Title:"));
        panel.add(titleField);

        // Выбор департамента
        List<Department> departments = departmentDao.getAll();
        departmentComboBox = new JComboBox<>(departments.toArray(new Department[0]));
        departmentComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Department) {
                    setText(((Department) value).getName());
                }
                return this;
            }
        });
        panel.add(new JLabel("Department:"));
        panel.add(departmentComboBox);

        // Выбор категории
        List<ProductCategory> categories = categoryDao.getAll();
        categoryComboBox = new JComboBox<>(categories.toArray(new ProductCategory[0]));
        categoryComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ProductCategory) {
                    setText(((ProductCategory) value).getTitle());
                }
                return this;
            }
        });
        panel.add(new JLabel("Category:"));
        panel.add(categoryComboBox);

        BaseDao<ProductModel> dao = new BaseDao<>(ProductModel.class);

        // Кнопки
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            model.setTitle(titleField.getText());
            model.setDepartment((Department) departmentComboBox.getSelectedItem());
            model.setCategory((ProductCategory) categoryComboBox.getSelectedItem());

            if (model.getId() == null) {
                dao.save(model);
            } else {
                dao.update(model);
            }

            dispose();
        });


        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        panel.add(saveButton);
        panel.add(cancelButton);
        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }
}