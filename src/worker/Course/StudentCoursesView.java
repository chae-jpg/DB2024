package worker.Course;

import worker.WorkerStart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentCoursesView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField searchTextField;
    private JComboBox<String> comboBox;
    private JTable table;
    private DefaultTableModel tableModel;
    private CourseDAO courseDAO;

    public static WorkerStart start_frame = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StudentCoursesView frame = new StudentCoursesView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public StudentCoursesView() {
        courseDAO = new CourseDAO();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("학생 별 수강 과목 조회");
        title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
        title.setBounds(250, 30, 300, 50);
        contentPane.add(title);

        searchTextField = new JTextField("학생 이름 or 학번을 입력하세요");
        searchTextField.setBounds(214, 97, 344, 26);
        contentPane.add(searchTextField);
        searchTextField.setColumns(10);

        comboBox = new JComboBox<>();
        String[] options = { "학생 이름", "학번" };
        comboBox.setModel(new DefaultComboBoxModel<>(options));
        comboBox.setSelectedItem("학생 이름"); // 기본 선택
        comboBox.setBounds(101, 98, 101, 27);
        contentPane.add(comboBox);

        JButton btnSearch = new JButton("검색");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) comboBox.getSelectedItem();
                String searchText = searchTextField.getText();
                searchCourses(selectedItem, searchText);
            }
        });
        btnSearch.setBounds(554, 97, 58, 29);
        contentPane.add(btnSearch);

        JButton homeButton = new JButton("home");
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start_frame = new WorkerStart();
                start_frame.setVisible(true);
                setVisible(false);
            }
        });
        homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        homeButton.setBounds(6, 6, 75, 29);
        contentPane.add(homeButton);

        tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "CourseID", "CourseName", "Classroom", "Credit", "Semester", "Day", "Time", "ProfessorID" });
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 150, 700, 300);
        contentPane.add(scrollPane);
    }

    private void searchCourses(String criteria, String value) {
        List<Course> courses;
        if (criteria.equals("학생 이름")) {
            courses = courseDAO.getCoursesByStudentName(value);
        } else {
            try {
                int studentID = Integer.parseInt(value);
                courses = courseDAO.getCoursesByStudentID(studentID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(contentPane, "유효한 학번을 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        displayCourses(courses);
    }

    private void displayCourses(List<Course> courses) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Course course : courses) {
            tableModel.addRow(new Object[] {
                    course.getCourseID(), course.getCourseName(), course.getClassroom(), course.getCredit(),
                    course.getSemester(), course.getDay(), course.getTime(), course.getProfessorID()
            });
        }
    }
}
