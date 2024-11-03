// File: StudentRegistrationApp.java
package Tugas3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class StudentRegistrationApp extends JFrame {
    private JTextField nameField;
    private JTextArea addressField;
    private JRadioButton maleRadio, femaleRadio;
    private JCheckBox readingCheckBox, sportsCheckBox, musicCheckBox;
    private JComboBox<String> programComboBox;
    private JList<String> courseList;
    private JTable studentTable;
    private JSpinner ageSpinner;
    private JSlider ageSlider;
    private JButton submitButton, updateButton, deleteButton;

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentRegistrationApp app = new StudentRegistrationApp();
            new StudentRegistrationController(app); // Initialize controller
            app.setVisible(true);
        });
    }

    // Constructor for setting up the application window
    public StudentRegistrationApp() {
        setTitle("Registrasi Mahasiswa");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Setup the UI components and menu bar
        setupUI();
        setupMenuBar();
    }

    // Method to setup the UI components
    private void setupUI() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nama field
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Nama:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);

        // Alamat field
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Alamat:"), gbc);

        gbc.gridx = 1;
        addressField = new JTextArea(3, 20);
        inputPanel.add(new JScrollPane(addressField), gbc);

        // Jenis Kelamin field
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Jenis Kelamin:"), gbc);

        gbc.gridx = 1;
        JPanel genderPanel = new JPanel();
        maleRadio = new JRadioButton("Laki-laki");
        femaleRadio = new JRadioButton("Perempuan");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        inputPanel.add(genderPanel, gbc);

        // Umur field (Spinner and Slider)
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Umur:"), gbc);

        gbc.gridx = 1;
        JPanel agePanel = new JPanel();

        // Spinner for age
        ageSpinner = new JSpinner(new SpinnerNumberModel(18, 15, 100, 1));
        agePanel.add(ageSpinner);

        // Slider for age
        ageSlider = new JSlider(15, 100, 18);
        ageSlider.setMajorTickSpacing(10);
        ageSlider.setMinorTickSpacing(1);
        ageSlider.setPaintTicks(true);
        ageSlider.setPaintLabels(true);
        agePanel.add(ageSlider);

        // Synchronize Spinner and Slider values
        ageSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                ageSlider.setValue((int) ageSpinner.getValue());
            }
        });
        ageSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                ageSpinner.setValue(ageSlider.getValue());
            }
        });

        inputPanel.add(agePanel, gbc);

        // Hobi field
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Hobi:"), gbc);

        gbc.gridx = 1;
        JPanel hobbiesPanel = new JPanel();
        readingCheckBox = new JCheckBox("Membaca");
        sportsCheckBox = new JCheckBox("Olahraga");
        musicCheckBox = new JCheckBox("Musik");
        hobbiesPanel.add(readingCheckBox);
        hobbiesPanel.add(sportsCheckBox);
        hobbiesPanel.add(musicCheckBox);
        inputPanel.add(hobbiesPanel, gbc);

        // Program Studi field
        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Program Studi:"), gbc);

        gbc.gridx = 1;
        String[] programs = {"Ilmu Komputer", "Sistem Informasi", "Teknik Elektro"};
        programComboBox = new JComboBox<>(programs);
        inputPanel.add(programComboBox, gbc);

        // Mata Kuliah field
        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(new JLabel("Mata Kuliah:"), gbc);

        gbc.gridx = 1;
        String[] courses = {"Matematika", "Pemrograman", "Fisika", "Basis Data"};
        courseList = new JList<>(courses);
        inputPanel.add(new JScrollPane(courseList), gbc);

        // Tabel untuk menampilkan data
        studentTable = new JTable(new DefaultTableModel(
            new String[]{"Nama", "Alamat", "Jenis Kelamin", "Umur", "Hobi", "Program Studi", "Mata Kuliah"}, 0
        ));
        JScrollPane tableScrollPane = new JScrollPane(studentTable);

        // Tombol Submit, Update, dan Delete
        submitButton = new JButton("Tambah");
        updateButton = new JButton("Ubah");
        deleteButton = new JButton("Hapus");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Menambahkan semua komponen ke dalam frame
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to setup the Menu Bar
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu "File" dengan opsi "Exit"
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        // Menambahkan menu ke menu bar
        menuBar.add(fileMenu);
        
        // Mengatur menu bar pada frame
        setJMenuBar(menuBar);
    }

    // Getter methods for UI component references
    public JTextField getNameField() { return nameField; }
    public JTextArea getAddressField() { return addressField; }
    public JRadioButton getMaleRadio() { return maleRadio; }
    public JRadioButton getFemaleRadio() { return femaleRadio; }
    public JCheckBox getReadingCheckBox() { return readingCheckBox; }
    public JCheckBox getSportsCheckBox() { return sportsCheckBox; }
    public JCheckBox getMusicCheckBox() { return musicCheckBox; }
    public JComboBox<String> getProgramComboBox() { return programComboBox; }
    public JList<String> getCourseList() { return courseList; }
    public JTable getStudentTable() { return studentTable; }
    public JSpinner getAgeSpinner() { return ageSpinner; }
    public JSlider getAgeSlider() { return ageSlider; }
    public JButton getSubmitButton() { return submitButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getDeleteButton() { return deleteButton; }
}
