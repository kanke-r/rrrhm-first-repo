package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Search extends JFrame implements ActionListener {
    JPanel contain;
    JLabel id;
    JTextField idt;
    JButton search;

    String table;
    HashMap<String,String[]> map;

    public Search(String table, HashMap dictionary){
        super("查询");
        setSize(300, 340);
        setLocation(600, 400);
        contain = new JPanel();
        contain.setLayout(null);
        add(contain);
        id = new JLabel("课程号");
        idt = new JTextField();
        search = new JButton("查询");
        id.setBounds(38, 50, 75, 35);
        idt.setBounds(80, 50, 150, 35);
        search.setBounds(102, 125, 70, 30);
        contain.add(id);
        contain.add(idt);
        contain.add(search);
        search.addActionListener(this);
        setVisible(true);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        this.map=dictionary;
        this.table=table;
    }

    void output(String courseid){
        JFrame fm = new JFrame("查看特定课程");
        fm.setLocation(600, 400);
        JPanel ct = new JPanel();
        ct.setLayout(new BorderLayout());
        JTextArea newlist = new JTextArea();
        newlist.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(newlist);

        if (table=="course"){
            fm.setSize(330, 400);
            newlist.append("课程编号\t课程名\t学分\t学时\n");
            try{
                String s = null;
                String path1 = System.getProperty("user.dir")+"/data/course.txt";
                BufferedReader br1 = new BufferedReader(
                        new FileReader(path1));        // 构造一个BufferedReader类来读取文件

                while ((s = br1.readLine()) != null) { // 使用readLine方法，一次读一行
                    String[] result1 = s.split(" ");
                    if (courseid.equals(result1[0])) {
                        newlist.append(courseid + "\t");
                        newlist.append(result1[1] + "\t");
                        newlist.append(result1[2] + "\t");
                        newlist.append(result1[3] + "\n");
                        break;
                    }
                }
                br1.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            fm.setSize(600, 400);
            newlist.append("课程号" + "\t");
            newlist.append("课程名" + "\t");
            newlist.append("教师工号" + "\t");
            newlist.append("教师姓名" + "\t");
            newlist.append("学号" + "\t");
            newlist.append("学生姓名" + "\t");
            newlist.append("成绩" + "\n");
            String[] result=map.get(courseid);
            newlist.append(result[0] + "\t"+result[1] + "\t"+result[2] + "\t"+result[3] + "\t"+result[4]+"\t"+result[5]+"\t"+result[6]+"\n");
        }
        ct.add(scrollPane, BorderLayout.CENTER); // 将 JScrollPane 添加到面板的中心
        fm.add(ct);
        fm.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        //如果点击查询按钮则触发
        if (e.getSource() == search){
            if (map.containsKey(idt.getText())){
                output(idt.getText());
            }else{
                JOptionPane.showMessageDialog(null, "这门课程不在你的范围内！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


}
