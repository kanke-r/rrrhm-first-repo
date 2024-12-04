package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GradeEntryConfirmation extends JFrame {
    private GradeEnter gradeEnter;
    private JFrame gradeInputFrame;
    private JTextField stuIdt, stuGradet, stuNamet;

    public GradeEntryConfirmation(GradeEnter gradeEnter, JFrame gradeInputFrame, JTextField stuIdt, JTextField stuGradet, JTextField stuNamet) {
        this.gradeEnter = gradeEnter;
        this.gradeInputFrame = gradeInputFrame;
        this.stuIdt = stuIdt;
        this.stuGradet = stuGradet;
        this.stuNamet = stuNamet;

        // 确认框界面
        setSize(250, 150);
        setLocation(650, 450);
        setTitle("成绩录入");

        JPanel panel = new JPanel();
        JLabel label = new JLabel("成绩录入完成，是否继续？");

        JButton continueButton = new JButton("继续录入");
        JButton finishButton = new JButton("结束录入");

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 清空界面继续录入
                stuIdt.setText("");
                stuGradet.setText("");
                stuNamet.setText("");
                setVisible(false);
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭所有相关界面
                gradeInputFrame.dispose();
                gradeEnter.dispose();
                setVisible(false);
            }
        });

        panel.add(label);
        panel.add(continueButton);
        panel.add(finishButton);

        add(panel);
        setVisible(true);
    }
}
