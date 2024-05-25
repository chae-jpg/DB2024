package professor;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ProfessorManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel title;
	private JButton btnSearch;
	private JTextField searchTextField;
	private JButton homeButton;
	private JComboBox comboBox;
	private JTable table;
	private JButton btnRegister;
	private JButton btnModify;
	private JButton btnDelete;
	String sql_query="";

	public static StartProfessor start_professor_frame = null;

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

		searchTextField = new JTextField("교수명 or 교수id or 학과를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
Vector<String> header = new Vector<String>();
        
        header.add("교수ID");
        header.add("이름");
        header.add("소속학과");
        header.add("이메일");
        header.add("전화번호");

		DefaultTableModel model =new DefaultTableModel(header,0);
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setNumRows(0);// 이전 검색결과 삭제용
				switch (comboBox.getSelectedIndex()){
				 case 0:
				      sql_query = String.format("select *  from DB2024_Professor where Name='%s';",searchTextField.getText());
				      

				 break;
				 
				 case 1:
				      sql_query = String.format("select * from DB2024_Professor where ProfessorID =%d;",Integer.valueOf(searchTextField.getText()));

				 break;
				 
				 case 2:
				      sql_query = String.format("select * from DB2024_Professor where Department='%s';",searchTextField.getText());

				 break;
				 
				 
				 }//switch
			
				
				 
				 try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/DB2024Team04", "root", "root");
								 Statement stmt = conn.createStatement(); )
								 {
								 
								
								 //검색 쿼리

								 
							
								 ResultSet rset = stmt.executeQuery(sql_query);
								
								 while( rset.next()) {
									 
									 String id =Integer.toString(rset.getInt("ProfessorID"));
									 String name  =rset.getString("Name");
									 String department =rset.getString("Department");									 
									 String email  =rset.getString("Email");
									 String phone =rset.getString("Phone");
									 
									 
									 
									 String[] data= {id,name,department,email,phone};
									 model.addRow(data);
									 
									 
									
								 }	//while
								
											 
								 }//try
								 
								 catch (SQLException sqle) {
								 System.out.println("SQLException : " + sqle);
								 }//catch

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
		String[] options = { "교수명", "교수id", "학과" };
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("교수명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		table = new JTable(model);
		table.setBounds(50, 150, 700, 300);
		panel.add(table);

	}

}
