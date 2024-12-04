package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.CourseView;
import controller.EditInfo;
import controller.GradeInfo;
import controller.Info;



@SuppressWarnings("serial")
public class StudentsPanel extends JFrame implements ActionListener {
	/*
	 * 学生登陆后操作主界面
	 */
	JPanel contain;
	String id;
	JButton infoButton, gradeButton, courseButton, editButton,backButton;

	public StudentsPanel(String id) {
		super("学生");
		this.id = id;
		setLocation(300, 200);
		setSize(300, 380);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		infoButton = new JButton("信息查询");
		gradeButton = new JButton("成绩查询");
		courseButton = new JButton("课程查询");
		editButton = new JButton("修改信息");
		backButton = new JButton("返回登录");
		infoButton.setBounds(70, 40, 140, 30);
		gradeButton.setBounds(70, 80, 140, 30);
		courseButton.setBounds(70, 120, 140, 30);
		editButton.setBounds(70, 160, 140, 30);
		backButton.setBounds(70, 200, 140, 30); // 设置返回按钮的位置
		contain.add(infoButton);
		infoButton.addActionListener(this);
		contain.add(gradeButton);
		gradeButton.addActionListener(this);
		contain.add(courseButton);
		courseButton.addActionListener(this);
		contain.add(editButton);
		editButton.addActionListener(this);
		contain.add(backButton); // 添加返回按钮
		backButton.addActionListener(this);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		//监听功能选项
		if (e.getSource() == infoButton) {
			//查询信息
			new Info(id, 1);
		}
		if (e.getSource() == gradeButton) {
			//查询成绩
			new GradeInfo(id);
		}
		if (e.getSource() == courseButton) {
			//查看课程
			new CourseView(id, 0);
		}
		if (e.getSource() == editButton) {
			//修改信息
			new EditInfo(id, 0);
		}
		if (e.getSource() == backButton) {
			this.dispose();  // 销毁当前学生面板窗口
			new MainFrame();  // 返回到登录界面
		}
	}
	//关闭窗口
	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}
}
