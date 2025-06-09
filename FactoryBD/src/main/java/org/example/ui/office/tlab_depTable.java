package org.example.ui.office;

import javafx.util.Pair;
import org.example.dao.BaseDao;
import org.example.model.Department;
import org.example.model.TestLab;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class tlab_depTable extends JTable {
    private DefaultTableModel model;

    public tlab_depTable() {
        model = new DefaultTableModel(
                new Object[]{"testLabID", "departmentID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<Pair<TestLab, Department>> models) {
        model.setRowCount(0);

        for (Pair<TestLab, Department> el : models) {

            model.addRow(new Object[]{
                    el.getKey().getId(),
                    el.getValue().getId()
            });
        }
    }

    public Pair<TestLab, Department> getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long tl_id = (long) model.getValueAt(selectedRow, 0);
            long dep_id = (long) model.getValueAt(selectedRow, 1);

            BaseDao<TestLab> tl_dao = new BaseDao<>(TestLab.class);
            BaseDao<Department> dep_dao = new BaseDao<>(Department.class);

            return new Pair<TestLab, Department> (tl_dao.getById(tl_id), dep_dao.getById(dep_id));
        }
        return null;
    }
}
