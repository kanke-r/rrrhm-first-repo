package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.AddUser;
import controller.DeleteUser;
import controller.EditInfo;



@SuppressWarnings("serial")
public class AdministratorPanel extends JFrame implements ActionListener {
	/*
	 * 管理员登陆后操作主界面
	 */
	JButton deleteUser, addUser, selfInfo, backButton;
	JPanel contain;
	String idd;

	public AdministratorPanel(String idd) {
		super("系统管理员");
		this.idd = idd;
		setLocation(300, 200);
		setSize(300, 380);
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);
		selfInfo = new JButton("修改信息");
		addUser = new JButton("增加用户");
		deleteUser = new JButton("删除用户");
		backButton = new JButton("返回登录");  // 返回登录按钮
		selfInfo.setBounds(70, 45, 140, 30);
		addUser.setBounds(70, 100, 140, 30);
		deleteUser.setBounds(70, 155, 140, 30);
		backButton.setBounds(70, 210, 140, 30); // 设置返回按钮的位置
		contain.add(selfInfo);
		contain.add(addUser);
		contain.add(deleteUser);
		contain.add(backButton);  // 添加返回按钮
		selfInfo.addActionListener(this);
		addUser.addActionListener(this);
		deleteUser.addActionListener(this);
		backButton.addActionListener(this);  // 绑定返回按钮事件
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addUser) {
			new AddUser();    // 用户密码初始化统一为123456
		} else if (e.getSource() == deleteUser) {
			new DeleteUser();
		} else if (e.getSource() == selfInfo) {
			new EditInfo(idd, 3);
		}else if (e.getSource() == backButton) {
			this.dispose();  // 销毁当前管理员面板窗口
			new MainFrame();  // 返回到登录界面
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