package org.example.ui;
import org.example.model.BaseModel;
import org.example.model.Product;

import javax.swing.*;
import java.util.List;

public abstract class BaseTable<T extends BaseModel> extends JTable {
    public abstract void set(List<T> products);
    public abstract T getSelected();
}