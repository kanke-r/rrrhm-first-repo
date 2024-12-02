package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GradeAppeal extends JFrame implements ActionListener {
    //定义成绩申诉属性
    JPanel contain;
    JButton check,appeal;
    //引入map便于检测
    HashMap gradeMap,appealMap;


    //构造成绩申诉面板
    public GradeAppeal(HashMap gradeMap) {
        super("成绩申诉");
        setLocation(300, 200);
        setSize(300, 300);
        this.gradeMap = gradeMap;
        contain = new JPanel();
        contain.setLayout(null);
        //查看反馈按钮
        check = new JButton("查看反馈");
        check.setBounds(70, 140, 140, 50);
        //提交申诉按钮
        appeal=new JButton("提交申诉");
        appeal.setBounds(70, 40, 140, 50);

        contain.add(check);
        contain.add(appeal);

        appeal.addActionListener(this);
        check.addActionListener(this);

        add(contain);
        setVisible(true);
    }

    //申诉成绩面板
    void submitAppeal() {}

    //查看反馈面板
    void checkAppeal() {}

    //按钮触发事件
    public void actionPerformed(ActionEvent e) {}

}
