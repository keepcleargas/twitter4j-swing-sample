package com.github.twitterswingsample.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.twitterswingsample.view.listener.WindowCloser;

public class ErrorMessageFrame extends JFrame {

	public ErrorMessageFrame(String message) {
		setUndecorated(true);
		setSize(300, 70);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		
		JPanel cp = (JPanel) getContentPane();
		cp.setLayout(new BorderLayout(5, 5));
		JEditorPane jep = new JEditorPane("text/plain", message);
		jep.setEditable(false);
		jep.setFont(new Font("Arial", Font.PLAIN, 25));
		cp.add(jep, BorderLayout.CENTER);
		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(new WindowCloser(this));
		cp.add(okBtn, BorderLayout.SOUTH);
	}
}