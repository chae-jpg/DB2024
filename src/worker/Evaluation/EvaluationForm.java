package worker.Evaluation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluationForm extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField scoreField;
    private JTextField dateField;
    private JTextArea commentArea;
    private Evaluation evaluation;
    private boolean isEditMode;

    public EvaluationForm(Frame owner, Evaluation evaluation, boolean isEditMode) {
        super(owner, true);
        this.evaluation = evaluation;
        this.isEditMode = isEditMode;
        initialize();
        if (isEditMode && evaluation != null) {
            loadData();
        }
    }

    private void initialize() {
        setTitle(isEditMode ? "강의평가 수정" : "강의평가 등록");
        setBounds(100, 100, 450, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblScore = new JLabel("점수:");
        lblScore.setBounds(50, 50, 80, 25);
        contentPanel.add(lblScore);

        scoreField = new JTextField();
        scoreField.setBounds(150, 50, 200, 25);
        contentPanel.add(scoreField);
        scoreField.setColumns(10);

        JLabel lblDate = new JLabel("날짜:");
        lblDate.setBounds(50, 100, 80, 25);
        contentPanel.add(lblDate);

        dateField = new JTextField();
        dateField.setBounds(150, 100, 200, 25);
        contentPanel.add(dateField);
        dateField.setColumns(10);

        JLabel lblComment = new JLabel("댓글:");
        lblComment.setBounds(50, 150, 80, 25);
        contentPanel.add(lblComment);

        commentArea = new JTextArea();
        commentArea.setBounds(150, 150, 200, 100);
        contentPanel.add(commentArea);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("저장");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveEvaluation();
            }
        });
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("취소");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(cancelButton);
    }

    private void loadData() {
        scoreField.setText(String.valueOf(evaluation.getScore()));
        dateField.setText(String.valueOf(evaluation.getDate()));
        commentArea.setText(evaluation.getComment());
    }

    private void saveEvaluation() {
        try {
            int score = Integer.parseInt(scoreField.getText());
            java.sql.Date date = java.sql.Date.valueOf(dateField.getText());
            String comment = commentArea.getText();

            if (isEditMode) {
                evaluation = new Evaluation(evaluation.getEvaluationID(), score, date, comment, evaluation.getStudentID(), evaluation.getCourseID());
            }
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "유효한 값을 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }
}
