package worker;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GradeEdit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JTextField textFieldGrade;
	private JTextField textFieldSemester;
	private JButton btnSubmit;
	private Grade grade;

	public GradeEdit(Grade grade) {
		this.grade = grade;
		initialize();
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

		title = new JLabel("성적 수정");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 101, 55);
		panel.add(title);

		JLabel lblGrade = new JLabel("성적:");
		lblGrade.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblGrade.setBounds(150, 150, 100, 30);
		panel.add(lblGrade);

		textFieldGrade = new JTextField(grade.getGrade());
		textFieldGrade.setBounds(250, 150, 300, 30);
		panel.add(textFieldGrade);

		JLabel lblSemester = new JLabel("학기:");
		lblSemester.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblSemester.setBounds(150, 200, 100, 30);
		panel.add(lblSemester);

		textFieldSemester = new JTextField(grade.getSemester());
		textFieldSemester.setBounds(250, 200, 300, 30);
		panel.add(textFieldSemester);

		btnSubmit = new JButton("확인");
		btnSubmit.setBounds(350, 300, 100, 30);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateGrade();
			}
		});
		panel.add(btnSubmit);
	}

	private void updateGrade() {
		String gradeValue = textFieldGrade.getText();
		String semesterValue = textFieldSemester.getText();

		if (gradeValue.isEmpty() || semesterValue.isEmpty()) {
			// 입력 검증
			return;
		}

		grade.setGrade(gradeValue);
		grade.setSemester(semesterValue);

		// DB 업데이트 로직 추가
		GradeDAO gradeDAO = new GradeDAO();
		gradeDAO.updateGrade(grade);

		GradeManagement gradeManagement = new GradeManagement();
		gradeManagement.setVisible(true);
		setVisible(false);
	}
}
