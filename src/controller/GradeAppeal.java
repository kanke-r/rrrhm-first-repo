package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GradeAppeal extends JFrame implements ActionListener {
    //定义成绩申诉属性
    JPanel contain;
    JButton check,appeal;
    //引入map便于检测
    HashMap<String,String[]> gradeMap;
    HashMap<String,String> appealMap;
    String id;


    //构造成绩申诉面板
    public GradeAppeal(HashMap gradeMap,String id) {
        super("成绩申诉");
        setLocation(300, 200);
        setSize(300, 300);
        this.gradeMap = gradeMap;
        this.id = id;
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

        //查询申诉文件获取所有申诉有关信息
        appealMap=new HashMap<String,String>();

        String path = System.getProperty("user.dir")+"/data/grade_appeal";
        File file = new File(path);
        File[] tempList = file.listFiles();
        //找到所有和该学员有关的课程
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isDirectory()) {
                // 这里就不递归了，
                String fileName = tempList[i].getName();
                String[] flag = fileName.split("_");
                if (gradeMap.containsKey(flag[0])&&id.equals(flag[1])) {
                    try{
                        BufferedReader br = new BufferedReader(new FileReader(tempList[i].toString()+"\\appeal.txt"));
                        String s = null;
                        String result = "";
                        while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                            result= result+s+"\n";
                            }
                            br.close();
                            appealMap.put(flag[0],result);
                        }catch (Exception e) {
                        e.printStackTrace();
                    }
                    }
                }
            }
        add(contain);
        setVisible(true);
    }


    //申诉成绩面板
    class submitAppeal extends JFrame implements ActionListener {
        //初始化标签
        JPanel contain;
        JLabel courseid,text;
        JTextField courseidField;
        JTextArea textField;
        JButton submit;
        public submitAppeal() {
            super("提交成绩申诉");
            setLocation(400, 400);
            setSize(400, 400);
            contain = new JPanel();
            contain.setLayout(null);
            courseid=new JLabel("课程号:");
            text = new JLabel("申诉原因:");
            submit = new JButton("提交");
            courseidField = new JTextField();
            textField = new JTextArea();
            //可视化图标
            courseid.setBounds(42,45, 75, 35);
            courseidField.setBounds(120, 45, 200, 35);
            text.setBounds(42,100,75,35);
            textField.setBounds(120,100,200,175);
            submit.setBounds(142,300,70,35);
            contain.add(courseid);
            contain.add(text);
            contain.add(courseidField);
            contain.add(textField);
            contain.add(submit);
            add(contain);
            submit.addActionListener(this);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==submit) {
                if (!gradeMap.containsKey(courseidField.getText())) {
                    JOptionPane.showMessageDialog(null, "这门课程不在你的范围内或者该门课还未公布成绩！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else if (appealMap.containsKey(courseidField.getText())) {
                    JOptionPane.showMessageDialog(null, "你已经提交过这门课程的申诉", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    String path1 = System.getProperty("user.dir")+"/data/grade_appeal/"+courseidField.getText()+"_"+id;
                    try{
                        Path directory = Paths.get(path1);
                        Files.createDirectories(directory);
                        File file1 = new File(path1+"/appeal.txt");
                        file1.createNewFile();
                        FileWriter fw1 = new FileWriter(file1);
                        try (BufferedWriter writer = new BufferedWriter(fw1)) {
                            writer.write(textField.getText());
                            appealMap.put(courseidField.getText(),textField.getText());
                            JOptionPane.showMessageDialog(null, "保存成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            setVisible(false);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "保存失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    //查看反馈面板
    class checkAppeal extends JFrame implements ActionListener {
        //声明属性
        JPanel contain;
        JLabel courseNum;
        JButton submit;
        HashMap<JButton,String> events;
        //构造函数
        public checkAppeal() {
            super("查看申诉反馈");
            setLocation(400, 400);
            setSize(400, 200);
            events=new HashMap<>();
            contain = new JPanel();
            contain.setLayout(new BorderLayout()); // 使用 BorderLayout布局管理器
            int num = appealMap.size();
            if (num==0) {
                courseNum=new JLabel("你还未提交过申诉！");
                submit = new JButton("提交申诉");
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(submit);
                contain.add(courseNum,BorderLayout.NORTH);
                contain.add(buttonPanel,BorderLayout.CENTER);
                submit.addActionListener(this);
            }else{
                courseNum=new JLabel("你还有"+num+"条申诉未被处理！");
                contain.add(courseNum,BorderLayout.NORTH);
                //将所有申诉请求列出
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                for (String key:appealMap.keySet()){
                    String[] courseInfo = gradeMap.get(key);
                    JButton event = new JButton(courseInfo[1]);
                    events.put(event,key);
                    buttonPanel.add(event);
                    event.addActionListener(this);
                }
                contain.add(buttonPanel,BorderLayout.CENTER);
            }
            add(contain);
            setVisible(true);
        }
        //处理反馈模块
        class appealHandler extends JFrame implements ActionListener {
            //面板属性
            String courseId;
            JPanel contain;
            JTextArea stuText,TchText;
            JButton accept,refuse;
            //构造函数
            public appealHandler(String courseId) {
                super(gradeMap.get(courseId)[1]+"成绩申诉页面");
                setLocation(400, 400);
                setSize(400, 400);
                this.courseId=courseId;

                contain = new JPanel();
                contain.setLayout(new BorderLayout());
                stuText=new JTextArea();
                TchText=new JTextArea();
                stuText.setEditable(false);
                TchText.setEditable(false);
                stuText.append("申诉内容：\n");
                stuText.append(appealMap.get(courseId));
                TchText.append("教师回复:\n");
                JScrollPane stu = new JScrollPane(stuText);
                JScrollPane Tch = new JScrollPane(TchText);
                JPanel TextPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                // 设置约束以均分区域
                gbc.fill = GridBagConstraints.BOTH; // 让按钮填满区域
                gbc.weightx = 1.0; // 水平方向权重设置为1，均分空间
                // 添加第一个按钮
                gbc.gridx = 0; // 第0列
                gbc.gridy = 0; // 第0行
                TextPanel.add(stu, gbc);
                // 添加第二个按钮
                gbc.gridx = 1; // 第1列
                TextPanel.add(Tch, gbc);
                contain.add(TextPanel,BorderLayout.CENTER);

                //读取老师回复消息
                try{
                    String path = System.getProperty("user.dir")+"/data/grade_appeal/"+courseId+"_"+id+"/reply.txt";
                    BufferedReader br = new BufferedReader(new FileReader(path));
                    String s = null;
                    String result = "";
                    accept =new JButton("接收，撤销申诉");
                    refuse = new JButton("拒绝，继续提出申诉");
                    while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                        result= result+s+"\n";
                    }
                    TchText.append(result);
                    br.close();
                }catch (FileNotFoundException e){
                    String text = "老师还未对本次申诉进行反馈，请耐心等候";
                    TchText.append(text);
                    accept = new JButton("撤销申诉");
                    refuse = new JButton("重新申诉");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                accept.addActionListener(this);
                refuse.addActionListener(this);
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(accept);
                buttonPanel.add(refuse);
                contain.add(buttonPanel,BorderLayout.SOUTH);

                add(contain);
                setVisible(true);
            }

            //重写申请界面
            class rewrite extends JFrame implements ActionListener {
                JPanel contain;
                JLabel text;
                JTextArea textt;
                JButton ok;

                public rewrite() {
                    super("成绩申诉");
                    setSize(300, 340);
                    setLocation(600, 400);
                    contain = new JPanel(new BorderLayout());
                    text = new JLabel("申诉内容");
                    ok = new JButton("提交");
                    ok.addActionListener(this);
                    textt =new JTextArea();
                    JScrollPane scrollPane = new JScrollPane(textt);
                    contain.add(text,BorderLayout.NORTH);
                    contain.add(scrollPane, BorderLayout.CENTER);
                    contain.add(ok,BorderLayout.SOUTH);
                    add(contain);
                    setVisible(true);
                }
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource()==ok) {
                        String path =System.getProperty("user.dir")+"/data/grade_appeal/"+courseId+"_"+id+"/appeal.txt";
                        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, false))) { // false 表示覆盖
                            bufferedWriter.write(textt.getText());
                            JOptionPane.showMessageDialog(null, "保存成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException x) {
                            JOptionPane.showMessageDialog(null, "保存失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        }
                        setVisible(false);
                    }
                }
            }


            //监听按钮
            public void actionPerformed(ActionEvent e){
                if (e.getSource()==accept){
                    String path =System.getProperty("user.dir")+"/data/grade_appeal/"+courseId+"_"+id;
                    File directory = new File(path);
                    if (!directory.exists()){
                        JOptionPane.showMessageDialog(null, "申诉已撤回，请勿重复撤回！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        File file1 = new File(directory,"appeal.txt");
                        if (file1.exists()){
                            file1.delete();
                        }
                        File file2 = new File(directory,"reply.txt");
                        if (file2.exists()){
                            file2.delete();
                        }
                        if (directory.delete()){
                            JOptionPane.showMessageDialog(null, "撤回成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            appealMap.remove(courseId);
                            setVisible(false);
                        }
                    }
                }else if (e.getSource()==refuse){
                    setVisible(false);
                    new rewrite();
                }
            }
        }

        //实现接口
        public void actionPerformed(ActionEvent e){
            if (e.getSource()==submit) {
                new submitAppeal();
                setVisible(false);
            }else if (events.containsKey(e.getSource())){
                String courseId = events.get(e.getSource());
                new appealHandler(courseId);
                setVisible(false);
            }
        }
    }

    //按钮触发事件
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==appeal) {
            new submitAppeal();
        }else if(e.getSource()==check) {
            new checkAppeal();
        }
    }

}
