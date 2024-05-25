package worker.Evaluation;

import worker.WorkerStart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CourseEvaluationManagement extends JFrame {

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
    private JButton btnDelete;
    public static WorkerStart start_frame = null;
    private CourseEvaluationDAO courseEvaluationDAO;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CourseEvaluationManagement frame = new CourseEvaluationManagement();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CourseEvaluationManagement() {
        courseEvaluationDAO = new CourseEvaluationDAO();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panel = new JPanel();
        panel.setBounds(12, 6, 771, 492);
        contentPane.add(panel);
        panel.setLayout(null);

        title = new JLabel("강의평가 관리");
        title.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
        title.setBounds(315, 21, 173, 55);
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
                searchEvaluations(selectedItem, searchText);
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
        homeButton.setBounds(6, 6, 85, 29);
        panel.add(homeButton);

        comboBox = new JComboBox<>();
        String[] options = { "강의명", "학수번호" };
        comboBox.setModel(new DefaultComboBoxModel<>(options));
        comboBox.setSelectedItem("강의명"); // 기본 선택
        comboBox.setBounds(101, 98, 101, 27);
        panel.add(comboBox);

        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] { "EvaluationID", "Score", "Date", "Comment", "StudentID", "CourseID" }
        );
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 150, 700, 300);
        panel.add(scrollPane);

        btnDelete = new JButton("삭제");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int evaluationID = (int) tableModel.getValueAt(selectedRow, 0);
                    courseEvaluationDAO.deleteEvaluation(evaluationID);
                    loadAllEvaluations(); // 모든 평가를 다시 로드
                } else {
                    JOptionPane.showMessageDialog(panel, "삭제할 평가를 선택하세요.", "삭제 오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnDelete.setBounds(675, 457, 75, 29);
        panel.add(btnDelete);

        // Load all evaluations on startup
        loadAllEvaluations();
    }

    private void searchEvaluations(String criteria, String value) {
        List<Evaluation> evaluations;
        if (criteria.equals("강의명")) {
            evaluations = courseEvaluationDAO.getEvaluationsByCourseName(value);
        } else {
            try {
                int courseID = Integer.parseInt(value);
                evaluations = courseEvaluationDAO.getEvaluationsByCourseID(courseID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(panel, "유효한 학수번호를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        displayEvaluations(evaluations);
    }

    private void loadAllEvaluations() {
        List<Evaluation> evaluations = courseEvaluationDAO.getAllEvaluations();
        displayEvaluations(evaluations);
    }

    private void displayEvaluations(List<Evaluation> evaluations) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Evaluation evaluation : evaluations) {
            tableModel.addRow(new Object[] {
                    evaluation.getEvaluationID(), evaluation.getScore(), evaluation.getDate(), evaluation.getComment(),
                    evaluation.getStudentID(), evaluation.getCourseID()
            });
        }
    }
}
