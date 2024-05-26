package student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProfessorViewStudent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox<String> comboBox;
	private JTable table;
	private ProfessorDAO professorDAO = new ProfessorDAO();

	public static StudentStart start_frame = null;//학생의 학사관리 페이지

	public ProfessorViewStudent() {
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

		title = new JLabel("교수 조회");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 129, 55);
		panel.add(title);

		searchTextField = new JTextField("교수명 or 교수id or 학과를 입력하세요");//텍스트 필드에서 정보를 입력하게 한다.
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();//텍스트필드의 텍스트를 가져온다.
				searchProfessors(selectedItem, searchText);//교수를 검색하는 함수
			}
		});
		btnSearch.setBounds(574, 97, 75, 29);
		panel.add(btnSearch);

		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {//홈버튼을 누른다면 학생의 학사관리 페이지로 간다.
			public void actionPerformed(ActionEvent e) {
				start_frame = new StudentStart();
				start_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 63, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = {"교수명", "교수id", "학과"};//콤보박스로 텍스트필드의 속성 지정
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("교수명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		table = new JTable();
		table.setBounds(50, 150, 700, 300);
		panel.add(table);

		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(50, 150, 700, 300);
		panel.add(pane);
	}

	private void searchProfessors(String searchType, String searchText) {
		List<Professor> professors = professorDAO.searchProfessors(searchType, searchText);//professorDAO클래스를 통해 sql쿼리 실행후 결과
		DefaultTableModel model = new DefaultTableModel();//검색결과를 저장할 테이블
		table.setModel(model);

		model.addColumn("ProfessorID");//속성 추가
		model.addColumn("Name");
		model.addColumn("Department");
		model.addColumn("Email");
		model.addColumn("Phone");

		for (Professor professor : professors) {
			model.addRow(new Object[]{professor.getProfessorID(), professor.getName(), professor.getDepartment(), professor.getEmail(), professor.getPhone()});
		}
	}
}
