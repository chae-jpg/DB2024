package professor.Course;

import professor.StartProfessor;
import worker.Course.CourseDAO;
import worker.Student.Student;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ProfessorStudentsByCourse extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox<String> comboBox;
	private JTable table;
	private DefaultTableModel tableModel;
	private CourseDAO courseDAO;

	public static StartProfessor start_professor_frame = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorStudentsByCourse frame = new ProfessorStudentsByCourse();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProfessorStudentsByCourse() {
		courseDAO = new CourseDAO();
		initialize();
		loadAllStudentsByCourses(); // Load all students for the professor's courses when the frame is initialized
	}

	private void initialize() {
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

		title = new JLabel("수강자 조회");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(323, 24, 124, 55);
		panel.add(title);

		searchTextField = new JTextField("강의명 or 학수번호를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				searchStudents(selectedItem, searchText);
			}
		});
		btnSearch.setBounds(554, 97, 58, 29);
		panel.add(btnSearch);

		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start_professor_frame = new StartProfessor();
				start_professor_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 63, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = { "강의명", "학수번호" };
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("강의명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] { "StudentID", "Name", "Department", "Email", "Contact" }
		);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 150, 700, 300);
		panel.add(scrollPane);
	}

	private void loadAllStudentsByCourses() {
		// Replace with the actual professor ID or name
		String professorName = "교수이름"; // Example professor name
		List<Student> students = courseDAO.getAllStudentsByProfessor(professorName);
		displayStudents(students);
	}

	private void searchStudents(String criteria, String value) {
		List<Student> students;
		if (criteria.equals("강의명")) {
			students = courseDAO.getStudentsByCourseName(value);
		} else {
			int courseID = Integer.parseInt(value);
			students = courseDAO.getStudentsByCourseID(courseID);
		}
		displayStudents(students);
	}

	private void displayStudents(List<Student> students) {
		tableModel.setRowCount(0); // Clear existing rows
		for (Student student : students) {
			tableModel.addRow(new Object[] {
					student.getStudentID(), student.getName(), student.getDepartment(), student.getEmail(),
					student.getContact()
			});
		}
	}
}
