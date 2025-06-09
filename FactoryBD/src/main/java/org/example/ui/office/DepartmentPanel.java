package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.Department;
import org.example.ui.BasePanel;

import javax.swing.*;

public class DepartmentPanel extends BasePanel {


    public DepartmentPanel(BaseDao<Department> dao, JFrame mainFrame) {

        super(dao, mainFrame, new DepartmentTable());

    }
    @Override
    protected void showForm(BaseModel el) {
        DepartmentForm form = new DepartmentForm(mainFrame, (Department) el);
        form.setVisible(true);
    }
}