package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import javax.swing.*;

public class GradeInfo extends JFrame implements ActionListener {
	/**
	 * 学生根据学号查询所有成绩
	 */
	private static final long serialVersionUID = 1L;
	JPanel contain;
	JTextArea list;
	JButton searchGrade,appealGrade;
	String id;


	String courseid;
	String coursename;
	String teacherid;
	String teachername;
	String studentid;
	String studentname;
	String grade;
	HashMap map;
	

	public GradeInfo(String id) {
		super("课程");
		this.id = id;
		setSize(600, 400);
		contain = new JPanel();
		contain.setLayout(new BorderLayout()); // 使用 BorderLayout布局管理器
		setLocation(600, 400);
		list = new JTextArea();
		list.setEditable(false);
		contain.add(list);
		
		list.append("课程号" + "\t");
		list.append("课程名" + "\t");
		list.append("教师工号" + "\t");
		list.append("教师姓名" + "\t");
		list.append("学号" + "\t");
		list.append("学生姓名" + "\t");
		list.append("成绩" + "\n");

		// 将 JTextArea 放入 JScrollPane 中，以便支持滚动
		JScrollPane scrollPane = new JScrollPane(list);
		contain.add(scrollPane, BorderLayout.CENTER); // 将 JTextArea 添加到 CENTER 区域
		searchGrade = new JButton("查询特定课程");
		appealGrade = new JButton("成绩申诉");
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(searchGrade);
		buttonPanel.add(appealGrade);

		// 将面板添加到 SOUTH 区域
		contain.add(buttonPanel, BorderLayout.SOUTH);
		searchGrade.addActionListener(this);
		appealGrade.addActionListener(this);

		//初始化字典
		 map = new HashMap<String,String[]>();

		// String path = "D://test//grade";
		String path = System.getProperty("user.dir")+"/data/grade";

		List<String> files = new ArrayList<String>(); // 目录下所有文件
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				files.add(tempList[i].toString());
				// 文件名，不包含路径
				// String fileName = tempList[i].getName();
			}
			if (tempList[i].isDirectory()) {
				// 这里就不递归了，
			}
		}

		try {
			for (int i = 0; i < files.size(); i++) {
				BufferedReader br = new BufferedReader(new FileReader(
						files.get(i)));
				String s = null;
				while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
					String[] result = s.split(" ");
					if (result[4].equals(id)) { // 学生学号相等时
						courseid = result[0];
						coursename = result[1];
						teacherid = result[2];
						teachername = result[3];
						studentid = result[4];
						studentname = result[5];
						grade = result[6];
						map.put(result[0], result);

						list.append(courseid + "\t");
						list.append(coursename + "\t");
						list.append(teacherid + "\t");
						list.append(teachername + "\t");
						list.append(studentid + "\t");
						list.append(studentname + "\t");
						list.append(grade + "\n");
					}
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		add(contain);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchGrade) {
			//查询信息
			new Search("grade",map);
		}else if (e.getSource()==appealGrade) {
			new GradeAppeal(map);
		}
	}
}
