// File: StudentRegistrationController.java
package Tugas3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentRegistrationController {
    private StudentRegistrationApp app;
    private DefaultTableModel tableModel;

    public StudentRegistrationController(StudentRegistrationApp app) {
        this.app = app;
        this.tableModel = (DefaultTableModel) app.getStudentTable().getModel();
        initialize();
    }

    private void initialize() {
        // Menambahkan action listener untuk tombol "Tambah"
        app.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) { // Cek validasi sebelum menambahkan data
                    addStudentData();
                }
            }
        });

        // Menambahkan action listener untuk tombol "Ubah"
        app.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) { // Cek validasi sebelum memperbarui data
                    updateStudentData();
                }
            }
        });

        // Menambahkan action listener untuk tombol "Hapus"
        app.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudentData();
            }
        });

        // Menambahkan mouse listener untuk memilih baris di tabel
        app.getStudentTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadSelectedRowData();
            }
        });
    }

    // Method untuk menambahkan data mahasiswa ke dalam tabel
    private void addStudentData() {
        String name = app.getNameField().getText();
        String address = app.getAddressField().getText();
        String gender = app.getMaleRadio().isSelected() ? "Laki-laki" : "Perempuan";
        int age = (int) app.getAgeSpinner().getValue();

        StringBuilder hobbies = new StringBuilder();
        if (app.getReadingCheckBox().isSelected()) hobbies.append("Membaca ");
        if (app.getSportsCheckBox().isSelected()) hobbies.append("Olahraga ");
        if (app.getMusicCheckBox().isSelected()) hobbies.append("Musik ");

        String program = (String) app.getProgramComboBox().getSelectedItem();

        StringBuilder courses = new StringBuilder();
        for (String course : app.getCourseList().getSelectedValuesList()) {
            courses.append(course).append(" ");
        }

        // Tambahkan data ke model tabel
        tableModel.addRow(new Object[]{name, address, gender, age, hobbies.toString().trim(), program, courses.toString().trim()});
        
        // Bersihkan field input setelah menambahkan data
        clearInputFields();
    }

    // Method untuk memperbarui data yang sudah ada di dalam tabel
    private void updateStudentData() {
        int selectedRow = app.getStudentTable().getSelectedRow();
        if (selectedRow != -1) { // Pastikan ada baris yang dipilih
            String name = app.getNameField().getText();
            String address = app.getAddressField().getText();
            String gender = app.getMaleRadio().isSelected() ? "Laki-laki" : "Perempuan";
            int age = (int) app.getAgeSpinner().getValue();

            StringBuilder hobbies = new StringBuilder();
            if (app.getReadingCheckBox().isSelected()) hobbies.append("Membaca ");
            if (app.getSportsCheckBox().isSelected()) hobbies.append("Olahraga ");
            if (app.getMusicCheckBox().isSelected()) hobbies.append("Musik ");

            String program = (String) app.getProgramComboBox().getSelectedItem();

            StringBuilder courses = new StringBuilder();
            for (String course : app.getCourseList().getSelectedValuesList()) {
                courses.append(course).append(" ");
            }

            // Update row yang dipilih dengan data baru
            tableModel.setValueAt(name, selectedRow, 0);
            tableModel.setValueAt(address, selectedRow, 1);
            tableModel.setValueAt(gender, selectedRow, 2);
            tableModel.setValueAt(age, selectedRow, 3);
            tableModel.setValueAt(hobbies.toString().trim(), selectedRow, 4);
            tableModel.setValueAt(program, selectedRow, 5);
            tableModel.setValueAt(courses.toString().trim(), selectedRow, 6);
            
            // Bersihkan field input setelah memperbarui data
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(app, "Pilih baris data yang ingin diubah.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method untuk menghapus data dari tabel
    private void deleteStudentData() {
        int selectedRow = app.getStudentTable().getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(app, "Pilih baris data yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Method untuk memuat data dari baris yang dipilih ke field input
    private void loadSelectedRowData() {
        int selectedRow = app.getStudentTable().getSelectedRow();
        if (selectedRow != -1) { // Pastikan ada baris yang dipilih
            String name = (String) tableModel.getValueAt(selectedRow, 0);
            String address = (String) tableModel.getValueAt(selectedRow, 1);
            String gender = (String) tableModel.getValueAt(selectedRow, 2);
            int age = (int) tableModel.getValueAt(selectedRow, 3);
            String hobbies = (String) tableModel.getValueAt(selectedRow, 4);
            String program = (String) tableModel.getValueAt(selectedRow, 5);
            String courses = (String) tableModel.getValueAt(selectedRow, 6);

            app.getNameField().setText(name);
            app.getAddressField().setText(address);
            app.getMaleRadio().setSelected(gender.equals("Laki-laki"));
            app.getFemaleRadio().setSelected(gender.equals("Perempuan"));
            app.getAgeSpinner().setValue(age);
            app.getAgeSlider().setValue(age);

            app.getReadingCheckBox().setSelected(hobbies.contains("Membaca"));
            app.getSportsCheckBox().setSelected(hobbies.contains("Olahraga"));
            app.getMusicCheckBox().setSelected(hobbies.contains("Musik"));

            app.getProgramComboBox().setSelectedItem(program);

            app.getCourseList().clearSelection();
            for (int i = 0; i < app.getCourseList().getModel().getSize(); i++) {
                if (courses.contains(app.getCourseList().getModel().getElementAt(i))) {
                    app.getCourseList().addSelectionInterval(i, i);
                }
            }
        }
    }

    // Method untuk membersihkan field input setelah menambahkan atau memperbarui data
    private void clearInputFields() {
        app.getNameField().setText("");
        app.getAddressField().setText("");
        app.getMaleRadio().setSelected(false);
        app.getFemaleRadio().setSelected(false);
        app.getAgeSpinner().setValue(18);
        app.getAgeSlider().setValue(18);
        app.getReadingCheckBox().setSelected(false);
        app.getSportsCheckBox().setSelected(false);
        app.getMusicCheckBox().setSelected(false);
        app.getProgramComboBox().setSelectedIndex(0);
        app.getCourseList().clearSelection();
    }

    // Method untuk memvalidasi input sebelum data ditambahkan atau diperbarui
    private boolean validateInput() {
        String name = app.getNameField().getText();
        String address = app.getAddressField().getText();
        boolean isGenderSelected = app.getMaleRadio().isSelected() || app.getFemaleRadio().isSelected();
        int age = (int) app.getAgeSpinner().getValue();

        if (name.isEmpty() || address.isEmpty() || !isGenderSelected || age < 15) {
            JOptionPane.showMessageDialog(app, "Harap isi semua field yang diperlukan:\nNama, Alamat, Jenis Kelamin, dan Umur minimal 15 tahun.", "Input Tidak Lengkap", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
