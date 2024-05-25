package worker.Grade;

import worker.WorkerStart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GradeManagement extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panel;
    private JLabel title;
    private JButton btnSearch;
    private JTextField searchTextField;
    private JButton homeButton;
    private JComboBox<String> comboBox;
    private JTable table;
    private GradeDAO gradeDAO = new GradeDAO();

    public static WorkerStart start_frame = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GradeManagement frame = new GradeManagement();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GradeManagement() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 543);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panel = new JPanel();
        panel.setBounds(12, 6, 771, 492);
        contentPane.add(panel);
        panel.setLayout(null);

        title = new JLabel("성적 관리");
        title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
        title.setBounds(317, 21, 124, 55);
        panel.add(title);

        searchTextField = new JTextField("학번 or 강의id를 입력하세요");
        searchTextField.setBounds(214, 97, 344, 26);
        panel.add(searchTextField);
        searchTextField.setColumns(10);

        btnSearch = new JButton("검색");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) comboBox.getSelectedItem();
                String searchText = searchTextField.getText();
                searchGrade(selectedItem, searchText);
            }
        });
        btnSearch.setBounds(574, 97, 75, 29);
        panel.add(btnSearch);

        homeButton = new JButton("home");
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start_frame = new WorkerStart();
                start_frame.setVisible(true);
                setVisible(false);
            }
        });
        homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        homeButton.setBounds(6, 6, 75, 29);
        panel.add(homeButton);

        comboBox = new JComboBox<>();
        String[] options = {"학번", "강의id"};
        comboBox.setModel(new DefaultComboBoxModel<>(options));
        comboBox.setSelectedItem("학번");
        comboBox.setBounds(101, 98, 101, 27);
        panel.add(comboBox);

        table = new JTable();
        table.setBounds(50, 150, 700, 300);
        panel.add(table);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(50, 150, 700, 300);
        panel.add(pane);
    }

    private void searchGrade(String selectedItem, String searchText) {
        List<Grade> grades;
        if (selectedItem.equals("학번")) {
            grades = gradeDAO.getGradesByStudentID(Integer.parseInt(searchText));
        } else {
            grades = gradeDAO.getGradesByCourseID(Integer.parseInt(searchText));
        }

        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);

        model.addColumn("GradeID");
        model.addColumn("StudentID");
        model.addColumn("CourseID");
        model.addColumn("Grade");
        model.addColumn("Semester");
        model.addColumn("Repetition");

        for (Grade grade : grades) {
            model.addRow(new Object[]{grade.getGradeID(), grade.getStudentID(), grade.getCourseID(), grade.getGrade(), grade.getSemester(), grade.isRepetition()});
        }
    }
}
