package view;

import java.awt.AWTEvent;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.CheckInfo;


public class MainFrame extends JFrame implements ActionListener {
	/**
	 * 登陆主界面
	 */
	private static final long serialVersionUID = 1L;
	JTextField idTextField;
	JPasswordField passwdTextField;
	JLabel idLabel, passwdLabel;
	Choice chooice;
	JButton logon;
	JPanel contain;
	
	int count = 0;

	public MainFrame() {
		//软件名字
		super("账号登陆");
		//设置页面大小
		setLocation(300, 200);
		setSize(300, 340);
		//设置面板组件
		contain = new JPanel();
		contain.setLayout(null);
		//新建标签
		idLabel = new JLabel("ID号");
		passwdLabel = new JLabel("密码");
		//创建文本输入框
		idTextField = new JTextField();
		//创建密码输入框
		passwdTextField = new JPasswordField();
		//设置登陆按钮
		logon = new JButton("登陆");
		//设置登陆选项
		chooice = new Choice();
		chooice.addItem("学生");
		chooice.addItem("教师");
		chooice.addItem("系统管理员");
		//分别设置标签、文本框以及选项相对于父容器的位置及大小
		idLabel.setBounds(42, 45, 75, 35);
		idTextField.setBounds(80, 45, 150, 35);
		passwdLabel.setBounds(40, 100, 75, 35);
		passwdTextField.setBounds(80, 100, 150, 35);
		chooice.setBounds(80, 160, 150, 35);
		logon.setBounds(102, 220, 70, 30);
		//将以上所有添加到容器里
		contain.add(idLabel);
		contain.add(idTextField);
		contain.add(passwdLabel);
		contain.add(passwdTextField);
		contain.add(chooice);
		contain.add(logon);
		//将登陆监听
		logon.addActionListener(this);
		//创建容器
		add(contain);
		//图形化
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}
	
	public void actionPerformed(ActionEvent e) {
		// 如果监听到logan
		if (e.getSource() == logon) {
			//检测登陆人员身份
			String ch = (String) chooice.getSelectedItem();
			if (ch == "学生") {
				//验证id与密码是否匹配 如果匹配则关闭该窗口同时新建学生窗口
				if ((new CheckInfo().isMember("student", idTextField.getText(),
						new String(passwdTextField.getPassword()))) == 1) {
					setVisible(false);
					new StudentsPanel(idTextField.getText());
				} else {
					//如果错误次数大于五则退出系统
					count += 1;
					if (count <= 5) {
						JOptionPane.showMessageDialog(null, "无此用户，或者密码输入错误！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
					}
					if (count > 5) {
						JOptionPane.showMessageDialog(null, "错误次数超过5次！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
						setVisible(false);
						System.exit(0);
					}
				}
			} else if (ch == "教师") {
				if ((new CheckInfo().isMember("teacher", idTextField.getText(),
						new String(passwdTextField.getPassword(), 0,
								passwdTextField.getPassword().length))) == 1) {
					setVisible(false);
					new TeachersPanel(idTextField.getText());
				} else {
					count += 1;
					if (count <= 5) {
						JOptionPane.showMessageDialog(null, "无此用户，或者密码输入错误！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
					}
					if (count > 5) {
						JOptionPane.showMessageDialog(null, "错误次数超过5次！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
						setVisible(false);
						System.exit(0);
					}
				}
			} else if (ch == "系统管理员") {
				if ((new CheckInfo().isMember("administrator", idTextField
						.getText(), new String(passwdTextField.getPassword(),
						0, passwdTextField.getPassword().length))) == 1) {
					setVisible(false);
					new AdministratorPanel(idTextField.getText());
				} else {
					count += 1;
					if (count <= 5) {
						JOptionPane.showMessageDialog(null, "无此用户，或者密码输入错误！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
					}
					if (count > 5) {
						JOptionPane.showMessageDialog(null, "错误次数超过5次！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
						setVisible(false);
						System.exit(0);
					}
				}
			}
		}
		
	}

	

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}
	//启动程序
	public static void main(String[] args) {
		new MainFrame();
	}
}