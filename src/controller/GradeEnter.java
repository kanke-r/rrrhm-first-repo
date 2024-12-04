package controller;

import javax.swing.*;
import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GradeEnter extends JFrame implements ActionListener {
	// 教师登录界面中的变量
	String idd;  // 教师号
	JPanel contain;
	JLabel id;
	JTextField idt, stuIdt, stuGradet, stuNamet;
	String targetFile;
	JButton submit, bn;
	ArrayList<String> modifiedContent = new ArrayList<>();

	// 成绩输入界面的 JFrame
	JFrame gradeInputFrame;

	public GradeEnter(String idd) {
		super("查看");
		this.idd = idd;
		setSize(300, 340);
		setLocation(600, 400);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		id = new JLabel("课程号");
		idt = new JTextField();
		submit = new JButton("提交");
		id.setBounds(38, 50, 75, 35);
		idt.setBounds(80, 50, 150, 35);
		submit.setBounds(102, 125, 70, 30);
		contain.add(id);
		contain.add(idt);
		contain.add(submit);
		submit.addActionListener(this);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	// 成绩录入界面
	void enter() {
		gradeInputFrame = new JFrame("登录成绩");  // 初始化成绩输入界面的 JFrame
		gradeInputFrame.setSize(300, 340);
		JPanel contain = new JPanel();
		gradeInputFrame.setLocation(600, 400);
		contain.setLayout(null);
		bn = new JButton("提交");
		JLabel stuId = new JLabel("学号");
		JLabel stuGrade = new JLabel("成绩");
		JLabel stuName = new JLabel("姓名");

		stuIdt = new JTextField();
		stuGradet = new JTextField();
		stuNamet = new JTextField();

		stuId.setBounds(38, 50, 75, 35);
		stuIdt.setBounds(80, 50, 150, 35);

		stuGrade.setBounds(38, 110, 75, 35);
		stuGradet.setBounds(80, 110, 150, 35);

		stuName.setBounds(38, 170, 75, 35);
		stuNamet.setBounds(80, 170, 150, 35);

		bn.setBounds(170, 220, 70, 30);
		contain.add(stuId);
		contain.add(stuIdt);
		contain.add(stuGrade);
		contain.add(stuGradet);
		contain.add(stuName);
		contain.add(stuNamet);
		contain.add(bn);
		gradeInputFrame.add(contain);
		bn.addActionListener(this);
		gradeInputFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if (hasThisCourse(idt.getText()) == 1) {
				enter();   // 进入成绩输入界面
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "您未开设此课程！", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == bn) {      // 成绩录入逻辑...


			if (hasThisStu() == 1) {   // 登录成绩
				String path = System.getProperty("user.dir") + "/data/grade";

				List<String> files = new ArrayList<String>(); // 课程成绩目录下所有科目成绩文件
				File file = new File(path);
				File[] tempList = file.listFiles();

				for (int i = 0; i < tempList.length; i++) {
					if (tempList[i].isFile()) {
						files.add(tempList[i].toString());
					}
				}

				try {
					for (int i = 0; i < files.size(); i++) {
						BufferedReader br = new BufferedReader(new FileReader(files.get(i)));
						String s = null;
						String[] result = null;
						while ((s = br.readLine()) != null) {
							result = s.split(" ");
							if (result[0].equals(idt.getText())) {
								targetFile = files.get(i);

								// 将原来的内容先复制
								String s1 = "";
								for (int j = 0; j < result.length - 1; j++) {
									s1 = s1 + result[j];
									s1 = s1 + " ";
								}
								s1 = s1 + result[result.length - 1];
								modifiedContent.add(s1);
							}
						}
						if (result[0].equals(idt.getText())) {
							String gradeInfo = idt.getText();
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + result[1];
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + result[2];
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + result[3];
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + stuIdt.getText();
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + stuNamet.getText();
							gradeInfo = gradeInfo + " ";
							gradeInfo = gradeInfo + stuGradet.getText();
							modifiedContent.add(gradeInfo);
						}
						br.close();
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}

				try {
					FileWriter fw = new FileWriter(targetFile);
					BufferedWriter bw = new BufferedWriter(fw);
					for (int i = 0; i < modifiedContent.size(); i++) {
						bw.write(modifiedContent.get(i));
						bw.newLine();
					}
					bw.close();
					fw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "成绩登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);

				// 调用 GradeEntryConfirmation 类来显示选择框
				new GradeEntryConfirmation(this, gradeInputFrame, stuIdt, stuGradet, stuNamet);  // 传递当前 GradeEnter 实例和成绩输入界面
			} else {
				JOptionPane.showMessageDialog(null, "课程号为" + idt.getText() + "无此学生", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	// 判断是否存在学生
	int hasThisStu() {
		String stuId = stuIdt.getText();
		String path = System.getProperty("user.dir") + "/data/course_student";
		List<String> files = new ArrayList<String>(); // 目录下所有文件
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				files.add(tempList[i].toString());
			}
		}

		try {
			for (int i = 0; i < files.size(); i++) {
				BufferedReader br = new BufferedReader(new FileReader(files.get(i)));
				String s = null;
				while ((s = br.readLine()) != null) {
					String[] result = s.split(" ");
					if (result[0].equals(idt.getText()) && result[2].equals(stuIdt.getText())) {
						return 1;
					}
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 判断是否存在该课程
	int hasThisCourse(String idd) {
		String file = System.getProperty("user.dir") + "/data/course.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");
				if (result[0].equals(idd)) {
					return 1;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void processWindowEvent(java.awt.event.WindowEvent e) {
		if (e.getID() == java.awt.event.WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
