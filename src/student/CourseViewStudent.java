package student;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CourseViewStudent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox comboBox;
	private JTable table;
	public static StudentStart start_frame=null;

	/**
	 * Create the frame.
	 */
	public CourseViewStudent() {
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
		
		title = new JLabel("강의 조회");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 118, 55);
		panel.add(title);
		
		searchTextField = new JTextField("강의명 or 학수번호 or 교수명을 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);
		
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBox.getSelectedItem();
				String searchText = searchTextField.getText();
				System.out.println(selectedItem);
				System.out.println(searchText);
				
				
			}
		});
		btnSearch.setBounds(554, 97, 58, 29);
		panel.add(btnSearch);
		
		homeButton = new JButton("home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				start_frame = new StudentStart();
			    start_frame.setVisible(true);
			    setVisible(false);
			}
		});
		homeButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		homeButton.setBounds(6, 6, 81, 29);
		panel.add(homeButton);
		
		comboBox = new JComboBox<>();
		String[] options = {"강의명", "학수번호","교수"};
		comboBox.setModel(new DefaultComboBoxModel<>(options)); 
		comboBox.setSelectedItem("강의명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);
	
        table = new JTable();
        table.setBounds(50, 150, 700, 300);
        panel.add(table);
	}

}
