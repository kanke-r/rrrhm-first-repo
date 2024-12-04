package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.*;


@SuppressWarnings("serial")
public class TeachersPanel extends JFrame implements ActionListener {
	/*
	 * 教师登陆后操作主界面
	 */

	String idd;
	JPanel contain;
	JButton infoButton, gradeButton, courseButton, editButton, courseView, sortGrade,appealHandler, backButton;

	public TeachersPanel(String idd) {
		super("教师");
		this.idd = idd;
		setLocation(300, 200);
		setSize(300, 440);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		infoButton = new JButton("信息查询");
		gradeButton = new JButton("成绩登录");
		courseButton = new JButton("全部课程");
		editButton = new JButton("修改信息");
		courseView = new JButton("开课");
		appealHandler = new JButton("反馈学生申诉");
		sortGrade = new JButton("成绩统计");
		backButton = new JButton("返回登录");
		infoButton.setBounds(70, 40, 140, 30);
		editButton.setBounds(70, 80, 140, 30);
		courseView.setBounds(70, 120, 140, 30);
		courseButton.setBounds(70, 160, 140, 30);
		gradeButton.setBounds(70, 200, 140, 30);
		sortGrade.setBounds(70, 240, 140, 30);
		appealHandler.setBounds(70, 280, 140, 30);
		backButton.setBounds(70, 320, 140, 30);
		contain.add(infoButton);
		infoButton.addActionListener(this);
		contain.add(gradeButton);
		gradeButton.addActionListener(this);
		contain.add(courseView);
		courseView.addActionListener(this);
		contain.add(courseButton);
		courseButton.addActionListener(this);
		contain.add(editButton);
		editButton.addActionListener(this);
		contain.add(appealHandler);
		appealHandler.addActionListener(this);

		contain.add(sortGrade);
		sortGrade.addActionListener(this);

		contain.add(backButton);
		backButton.addActionListener(this);

		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == infoButton) {
			//查询信息
			new Info(idd, 0);
		}
		if (e.getSource() == gradeButton) {
			//成绩登陆
			new GradeEnter(idd);
		}
		if (e.getSource() == courseButton) {
			//查看课程
			new CourseView(idd, 1);
		}
		if (e.getSource() == editButton) {
			//修改信息
			new EditInfo(idd, 1);
		}
		if (e.getSource() == courseView) {
			//添加课程
			new AddCourse();
		}
		if(e.getSource() == sortGrade){
			//成绩统计
			new SortGradeFrame();
		}
		if(e.getSource() == appealHandler){
			//处理成绩反馈
			new AppealHandler(idd);
		}
		if (e.getSource() == backButton) {
			this.dispose();   // 销毁当前教师面板窗口
			new MainFrame();//返回登录
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}
}
