package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AppealHandler extends JFrame implements ActionListener {
    //面板元素
    JPanel contain;
    int courseNum,stuNum;
    HashMap<JButton,String> events;
    HashMap<String, ArrayList<String>> map;
    String tid;
    HashMap<String,String> courseName;

    //构造函数
    public AppealHandler(String tid) {
        // 构建面板
        super("查看申诉反馈");
        setLocation(400, 400);
        setSize(400, 200);
        events=new HashMap<>();
        contain = new JPanel();
        contain.setLayout(new BorderLayout()); // 使用 BorderLayout布局管理器
        this.tid=tid;
        map=new HashMap<>();
        courseName=hasThisCourse();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // 查询该老师所教授课程的申诉
        String path = System.getProperty("user.dir")+"/data/grade_appeal";
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isDirectory()) {
                // 这里就不递归了，
                String fileName = tempList[i].getName();
                String[] flag = fileName.split("_");
                String courseId = flag[0];
                String stuId = flag[1];
                if (courseName.containsKey(courseId)) {
                    File f = new File(tempList[i].getPath()+"/reply.txt");
                    if (!f.exists()) {
                        //将课程学生树构造方便后续调用
                        if (map.containsKey(courseId)) {
                            map.get(courseId).add(stuId);
                        }else{
                            ArrayList<String> list = new ArrayList<>();
                            list.add(stuId);
                            map.put(courseId,list);
                        }
                        stuNum++;
                    }
                }
            }
        }

        courseNum =map.size();
        if (stuNum==0){
            buttonPanel.add(new JLabel("你并未受到有关申诉"));
        }else{
            contain.add(new JLabel("你还有" +courseNum+"门相关课程,和"+stuNum+"个申诉未回复"),BorderLayout.NORTH);
            for(String key:map.keySet()){
                JButton event = new JButton(courseName.get(key));
                events.put(event,key);
                buttonPanel.add(event);
                event.addActionListener(this);
            }
        }
        contain.add(buttonPanel,BorderLayout.CENTER);
        add(contain);
        setVisible(true);
    }

    HashMap<String,String> hasThisCourse() {

        String file = System.getProperty("user.dir")+"/data/course.txt";
        HashMap<String,String> map = new HashMap<>();
        // String file = "D://test//course.txt";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                String[] result = s.split(" ");
                if(result[4].equals(tid)){
                    map.put(result[0],result[1]);
                }
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }


    //接口实现
    public void actionPerformed(ActionEvent e) {
        if (events.containsKey(e.getSource())) {
            new stuTree(events.get(e.getSource()));
            setVisible(false);
        }
    }

    class stuTree extends JFrame implements ActionListener {
        JPanel contain;
        HashMap<JButton,String> events;
        String cId;

        public stuTree(String cId) {
            super(courseName.get(cId)+"反馈页面");
            this.cId=cId;
            setLocation(400, 400);
            setSize(400, 200);
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            events=new HashMap<>();
            contain = new JPanel();
            contain.setLayout(new BorderLayout()); // 使用 BorderLayout布局管理器
            ArrayList<String> list = map.get(cId);
            int nums= list.size();
            contain.add(new JLabel(courseName.get(cId)+"中还有"+nums+"个申诉未回复"),BorderLayout.NORTH);
            for(String key:list){
                JButton event = new JButton(key);
                events.put(event,key);
                event.addActionListener(this);
                buttonPanel.add(event);
            }
            contain.add(buttonPanel,BorderLayout.CENTER);
            add(contain);
            setVisible(true);
        }
        public void actionPerformed(ActionEvent e) {
            if (events.containsKey(e.getSource())) {
                new handler(cId,events.get(e.getSource()));
                setVisible(false);
            }
        }
    }

    class handler extends JFrame implements ActionListener {
        JPanel contain;
        JTextArea stuText;
        JButton accept,refuse;
        String cId,sId;


        public handler(String cId, String sId) {
            super(courseName.get(cId)+"中"+sId+"申诉页面");
            setLocation(400, 400);
            setSize(400, 400);
            contain = new JPanel();
            contain.setLayout(new BorderLayout());
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            stuText=new JTextArea();
            stuText.setEditable(false);
            stuText.append("申诉内容：\n");
            JScrollPane stu = new JScrollPane(stuText);
            contain.add(stu,BorderLayout.CENTER);
            accept = new JButton("同意申诉并更改成绩 ");
            refuse = new JButton("拒绝并给出原因");
            accept.addActionListener(this);
            refuse.addActionListener(this);
            buttonPanel.add(accept);
            buttonPanel.add(refuse);
            contain.add(buttonPanel,BorderLayout.SOUTH);
            this.cId=cId;
            this.sId=sId;
            //读取学生申诉内容
            try{
                String path = System.getProperty("user.dir")+"/data/grade_appeal/"+cId+"_"+sId+"/appeal.txt";
                BufferedReader br = new BufferedReader(new FileReader(path));
                String s = null;
                while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                    stuText.append(s+"\n");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            add(contain);
            setVisible(true);
        }

        //反馈提交
        class reply extends JFrame implements ActionListener {
            JPanel contain;
            JLabel grade,reason;
            JTextField gradeUpdate;
            JTextArea text;
            JButton submit;
            int flag;
            public reply(int flag){
                super("回复申诉");
                setLocation(400, 400);
                setSize(400, 400);
                this.flag=flag;
                contain = new JPanel();
                contain.setLayout(null);
                submit = new JButton("提交");
                reason = new JLabel("原因");
                text = new JTextArea();
                reason.setBounds(42,100,75,35);
                text.setBounds(120,100,200,175);
                submit.setBounds(142,300,70,35);
                contain.add(reason);
                contain.add(text);
                contain.add(submit);
                //检测是拒绝还是同意
                if (flag==0){
                    grade=new JLabel("更改成绩为:");
                    gradeUpdate = new JTextField();
                    grade.setBounds(42,45, 75, 35);
                    gradeUpdate.setBounds(120, 45, 200, 35);
                    contain.add(grade);
                    contain.add(gradeUpdate);
                }
                contain.add(submit);
                add(contain);
                submit.addActionListener(this);
                setVisible(true);
            }

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == submit) {
                    String reply = "";
                    String temp ="";
                    String tempPath="";
                    if (flag==0){
                        int number = Integer.parseInt(gradeUpdate.getText());
                        if ( number >100 || number <0){
                            JOptionPane.showMessageDialog(null, "输入成绩错误，应该为0-100之间的整数", "提示", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }else{
                            // 找对应课程成绩文件
                            // 查询该老师所教授课程的申诉
                            String path1 = System.getProperty("user.dir")+"/data/grade";
                            File file = new File(path1);
                            File[] tempList = file.listFiles();

                            //修改文件中成绩
                            for (int i = 0; i < tempList.length; i++) {
                                if (tempList[i].isFile()) {
                                    try{
                                        //读取文件内容
                                        BufferedReader br = new BufferedReader(new FileReader(tempList[i].getPath()));
                                        String s = br.readLine();
                                        String[] result = s.split(" ");
                                        if(!result[0].equals(cId)){
                                            continue;
                                        }
                                        if(result[4].equals(sId)){
                                            result[6]=gradeUpdate.getText();
                                        }
                                        tempPath=tempList[i].getPath();
                                        temp+=String.join(" ", Arrays.asList(result))+"\n";
                                        while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                                            result = s.split(" ");
                                            if(result[4].equals(sId)){
                                                result[6]=gradeUpdate.getText();
                                            }
                                            temp+=String.join(" ", Arrays.asList(result))+"\n";
                                        }
                                        br.close();
                                        //写入新内容
                                    }catch(Exception x){
                                        x.printStackTrace();
                                    }
                                }
                            }
                            reply+="老师更改你的成绩为"+gradeUpdate.getText()+"\n";
                        }
                    }
                    reply+=text.getText();
                    String path =System.getProperty("user.dir")+"/data/grade_appeal/"+cId+"_"+sId+"/reply.txt";
                    try {
                        //写入新成绩
                        if (flag==0) {
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempPath, false));
                            bufferedWriter.write(temp);
                            bufferedWriter.close();
                        }
                        File file1 = new File(path);
                        file1.createNewFile();
                        FileWriter fw1 = new FileWriter(file1);
                        BufferedWriter bw = new BufferedWriter(fw1);
                        bw.write(reply);
                        bw.close();
                        JOptionPane.showMessageDialog(null, "保存成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException x) {
                        System.out.println(cId+sId);
                        JOptionPane.showMessageDialog(null, "保存失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                    setVisible(false);
                }
            }
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == accept) {
                new reply(0);
            }else if (e.getSource() == refuse) {
                new reply(1);
            }
            setVisible(false);
        }
    }


}
