package org.example.ui.office;

import org.example.dao.BaseDao;
import org.example.model.TestLabEquipment;
import org.example.ui.BaseTable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TestLabEquipmentTable extends BaseTable<TestLabEquipment> {
    private DefaultTableModel model;

    public TestLabEquipmentTable() {
        model = new DefaultTableModel(
                new Object[]{"ID", "Title", "TestLab_ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
    }

    public void set(List<TestLabEquipment> models) {
        model.setRowCount(0);

        for (TestLabEquipment el : models) {

            model.addRow(new Object[]{
                    el.getId(),
                    el.getTitle(),
                    el.getTestLab() != null ? el.getTestLab().getTitle() : "N/A",
            });
        }
    }

    public TestLabEquipment getSelected() {
        int selectedRow = getSelectedRow();
        if (selectedRow >= 0) {
            long id = (long) model.getValueAt(selectedRow, 0);

            return new BaseDao<TestLabEquipment>(TestLabEquipment.class).getById(id);
        }
        return null;
    }
}
