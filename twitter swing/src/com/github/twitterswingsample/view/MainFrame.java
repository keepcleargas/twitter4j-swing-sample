package com.github.twitterswingsample.view;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import com.github.twitterswingsample.model.Credentials;
import com.github.twitterswingsample.view.listener.ProgramCloser;
import com.github.twitterswingsample.view.listener.UserSelectionFrameCreator;
import com.github.twitterswingsample.view.panels.ConsolePanel;
import com.github.twitterswingsample.view.panels.UserPanel;

public class MainFrame extends JFrame{
	
	public MainFrame() {
		setBounds(50, 0, 600, 700);
		setTitle("Twitter4J Swing Sample");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));
		
		try {
			setIconImage(ImageIO.read(getClass().getResource("images/icon.png")));
		} catch (IOException e) {}
		
		JMenuBar jmb = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem open = new JMenuItem("Open User Selection"),
				exit = new JMenuItem("Close");
		
		open.addActionListener(new UserSelectionFrameCreator());
		exit.addActionListener(new ProgramCloser());
		
		file.add(open);
		file.addSeparator();
		file.add(exit);
		jmb.add(file);
		setJMenuBar(jmb);
		
//		JSplitPane jsp = new JSplitPane();
//		jsp.setOrientation(jsp.VERTICAL_SPLIT);
//		jsp.setResizeWeight(0.75);
//		jsp.setRightComponent(ConsolePanel.getSingleton());
		
		try {
//			jsp.setLeftComponent(new UserPanel(new Credentials(0)));
			add(new UserPanel(new Credentials(0)), BorderLayout.CENTER);
		} catch (FileNotFoundException e) {
			ConsolePanel.getSingleton().printMessage(new String[]{
				"File 'login.xml' not found",
				"See the project homepage for further information"
			});
		} catch (IOException e) {
			ConsolePanel.getSingleton().printMessage(new String[]{
				"File 'login.xml' destroyed",
				"See the project homepage for further information"
			});
		} catch (Exception e) {
			ConsolePanel.getSingleton().printException("Internal Error. Please report the bug", e);
		}

		add(ConsolePanel.getSingleton(), BorderLayout.SOUTH);
//		add(jsp, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) throws Exception {
		new MainFrame().setVisible(true);
	}
}