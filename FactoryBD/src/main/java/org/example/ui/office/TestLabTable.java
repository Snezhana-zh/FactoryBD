package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.TestLab;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TestLabTable extends BaseTable<TestLab> {
    private DefaultTableModel model;

    public TestLabTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Title"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<TestLab> models) {
        model.setRowCount(0);

        for (TestLab el : models) {

            model.addRow(new Object[]{
                    el.getId(),
                    el.getTitle()
            });
        }
    }

    public TestLab getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<TestLab>(TestLab.class).getById(id);
        }
        return null;
    }
}
