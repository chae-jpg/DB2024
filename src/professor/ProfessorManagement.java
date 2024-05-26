package professor;

import professor.StartProfessor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProfessorManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox<String> comboBox;
	private JTable table;
	private ProfessorDAO professorDAO;

	public static StartProfessor start_professor_frame = null; //교수 학사관리 페이지

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorManagement frame = new ProfessorManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ProfessorManagement() {
		professorDAO = new ProfessorDAO();

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

		title = new JLabel("교수 정보 조회");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(318, 24, 182, 55);
		panel.add(title);

		searchTextField = new JTextField("교수명 or 교수id or 학과를 입력하세요");// 텍스트 필드를 통해 교수명, 교수id,학과를 입력받는다.
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {//만약 검색버튼이 눌린다면
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();//콤보박스의 아이템을 확인
				String searchText = searchTextField.getText();//텍스트필드의 텍스트를 확인
				searchProfessors(selectedItem, searchText); //함수를 실행해 교수를 검색한다.
			}
		});
		btnSearch.setBounds(574, 97, 75, 29);
		panel.add(btnSearch);

		homeButton = new JButton("home");//홈버튼
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//홈버튼을 누르면 교수의 학사관리 페이지로 간다.
				start_professor_frame = new StartProfessor();
				start_professor_frame.setVisible(true);
				setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 85, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = { "교수명", "교수id", "학과" };//콤보박스로 텍스트필드의 값의 속성 지정
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
		displayProfessors(professors);//검색결과를 보여준다.
	}

	private void displayProfessors(List<Professor> professors) {
		DefaultTableModel model = new DefaultTableModel();
		table.setModel(model);

		model.addColumn("ProfessorID");//테이블에 속성추가
		model.addColumn("Name");
		model.addColumn("Department");
		model.addColumn("Email");
		model.addColumn("Phone");

		for (Professor professor : professors) {
			model.addRow(new Object[]{professor.getId(), professor.getName(), professor.getDepartment(), professor.getEmail(), professor.getPhone()});
			//테이블에 행 넣기
		}
	}
}
