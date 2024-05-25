package worker.Course;

import worker.Student.Student;
import worker.WorkerStart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentsByCourseView extends JFrame {

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
					StudentsByCourseView frame = new StudentsByCourseView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StudentsByCourseView() {
		courseDAO = new CourseDAO();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel title = new JLabel("강의 별 수강자 명단 조회");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(250, 30, 300, 50);
		contentPane.add(title);

		searchTextField = new JTextField("강의명 or 학수번호를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);

		comboBox = new JComboBox<>();
		String[] options = { "강의명", "학수번호" };
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("강의명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		contentPane.add(comboBox);

		JButton btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				searchStudents(selectedItem, searchText);
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

		tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "StudentID", "Name", "Department", "Email", "Contact", "Password" });
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 150, 700, 300);
		contentPane.add(scrollPane);
	}

	private void searchStudents(String criteria, String value) {
		List<Student> students;
		if (criteria.equals("강의명")) {
			students = courseDAO.getStudentsByCourseName(value);
		} else {
			try {
				int courseID = Integer.parseInt(value);
				students = courseDAO.getStudentsByCourseID(courseID);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(contentPane, "유효한 학수번호를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		displayStudents(students);
	}

	private void displayStudents(List<Student> students) {
		tableModel.setRowCount(0); // Clear existing rows
		for (Student student : students) {
			tableModel.addRow(new Object[] {
					student.getStudentID(), student.getName(), student.getDepartment(), student.getEmail(),
					student.getContact(), student.getPassword()
			});
		}
	}
}
