package worker;

import java.awt.EventQueue;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import professor.StartProfessor;

import java.sql.*;
;public class ProfessorManagement extends JFrame {

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
	public static NewProfessor newprofessor = null;
	public static UpdateProfessor updateprofessor=null;
	String sql_query="";
	
	public static WorkerStart start_frame = null;

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
		Vector<String> header = new Vector<String>();
	    
	    header.add("교수ID");
	    header.add("이름");
	    header.add("소속학과");
	    header.add("이메일");
	    header.add("전화번호");
	    header.add("비밀번호");
	    
		DefaultTableModel model =new DefaultTableModel(header,0);

		
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

		title = new JLabel("교수 관리");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		title.setBounds(332, 24, 116, 55);
		panel.add(title);

		searchTextField = new JTextField("교수명 or 교수id를 입력하세요");
		searchTextField.setBounds(214, 97, 344, 26);
		panel.add(searchTextField);
		searchTextField.setColumns(10);

		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnSearch.setBounds(554, 97, 58, 29);
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
		homeButton.setBounds(6, 6, 81, 29);
		panel.add(homeButton);

		comboBox = new JComboBox<>();
		String[] options = { "교수명", "교수id"};
		comboBox.setModel(new DefaultComboBoxModel<>(options));
		comboBox.setSelectedItem("교수명"); // 기본 선택
		comboBox.setBounds(101, 98, 101, 27);
		panel.add(comboBox);

		table = new JTable();
		table.setBounds(50, 150, 700, 300);
		panel.add(table);
//교수 등록 방법 : 등록 버튼 누르면 
		btnRegister = new JButton("등록");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newprofessor = new NewProfessor();
				newprofessor.setVisible(true);
			      setVisible(false);					
							}
		});
		btnRegister.setBounds(537, 457, 75, 29);
		panel.add(btnRegister);
		//table = new JTable(model);
//교수 수정방법: 콤보박스 선택후 대상 입력
		btnModify = new JButton("수정");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 수정 버튼 눌렀을 때
				//텍스트 필드의 값을 읽어와서 (검색결과는 오직 하나의 튜플만 가진다는 가정)
				switch (comboBox.getSelectedIndex()){
				 case 0:
					 
				      sql_query = String.format("select * from DB2024_Professor  where Name='%s';",searchTextField.getText());

				 break;
				 
				 case 1:
				      sql_query = String.format("select * from DB2024_Professor where ProfessorID =%d;",Integer.valueOf(searchTextField.getText()));

				 break;
				 

				 
			}
				updateprofessor = new UpdateProfessor(sql_query);
				
			}});
		btnModify.setBounds(605, 457, 75, 29);
		panel.add(btnModify);

//교수 삭제 방법: 콤보박스를 선택후 검색창에 대상 검색하고 삭제버튼 클릭
		
		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// 삭제 버튼 
				switch (comboBox.getSelectedIndex()){
				 case 0:
				      sql_query = String.format("delete from DB2024_Professor where Name='%s';",searchTextField.getText());

				 break;
				 
				 case 1:
				      sql_query = String.format("delete from DB2024_Professor where ProfessorID =%d;",Integer.valueOf(searchTextField.getText()));

				 break;
				 
				 case 2:
				      sql_query = String.format("delete from DB2024_Professor where Department='%s';",searchTextField.getText());

				 break;
				 default: JOptionPane.showMessageDialog(null, "삭제할 대상이 존재하지 않음");
					 break;
				}//switch
				try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/DB2024Team04", "root", "root");
						 Statement stmt = conn.createStatement(); ){
					int isDeleted=stmt.executeUpdate(sql_query);
					JOptionPane.showMessageDialog(null, "삭제 되었습니다.");
					if(isDeleted<=0) JOptionPane.showMessageDialog(null, "삭제할 수 없는 교수입니다.");
					ResultSet rset = stmt.executeQuery("select * from DB2024_Professor");
					while( rset.next()) {
						 
						 String id =Integer.toString(rset.getInt("ProfessorID"));
						 String name  =rset.getString("Name");
						 String department =rset.getString("Department");									 
						 String email  =rset.getString("Email");
						 String phone =rset.getString("Phone");
						 String pw =rset.getString("Password");
						 
						 
						 
						 String[] data= {id,name,department,email,phone};
						 model.addRow(data);
						 
						 
						
					 }	//while
					
					 
					 
					 
					
				}catch (SQLException sqle) {
					 System.out.println("SQLException :" + sqle);
					 JOptionPane.showMessageDialog(null, "삭제할 수 없는 교수입니다.");
					 }//catch
				

			}
		});
		btnDelete.setBounds(675, 457, 75, 29);
		panel.add(btnDelete);
		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		add(new JScrollPane(table));        
        setVisible(true);       

		table.setBounds(50, 150, 700, 300);
		panel.add(table);

	}

}
