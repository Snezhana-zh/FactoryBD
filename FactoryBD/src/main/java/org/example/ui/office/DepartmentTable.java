package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.Department;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DepartmentTable extends BaseTable<Department> {
    private DefaultTableModel model;

    public DepartmentTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Head", "Name", "Company_ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<Department> models) {
        model.setRowCount(0);

        for (Department el : models) {

            model.addRow(new Object[]{
                    el.getId(),
                    el.getHead().getName(),
                    el.getName(),
                    el.getCompany() != null ? el.getCompany().getName() : "N/A",
            });
        }
    }

    public Department getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<Department>(Department.class).getById(id);
        }
        return null;
    }
}
