package org.example.ui.employee;

import org.example.dao.BaseDao;
import org.example.model.BaseModel;
import org.example.model.Employee;
import org.example.ui.BasePanel;

import javax.swing.*;

public class EmployeePanel extends BasePanel {


    public EmployeePanel(BaseDao<Employee> dao, JFrame mainFrame) {

        super(dao, mainFrame, new EmployeeTable());

    }
    @Override
    protected void showForm(BaseModel el) {
        EmployeeForm form = new EmployeeForm(mainFrame, (Employee) el);
        form.setVisible(true);
    }
}