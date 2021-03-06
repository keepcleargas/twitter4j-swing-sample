package com.github.twitterswingsample.view.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.twitterswingsample.view.listener.authorized.Follower;
import com.github.twitterswingsample.view.listener.authorized.UnFollower;

import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * Panel displaying some information about an single user
 * 
 * @author multiprogger
 */
public class TwitterUserPanel extends JPanel{
	
	private Twitter twitter;
	private User user;
	private JButton followBtn;
	private Follower follower;
	private UnFollower unFollower;

	public TwitterUserPanel(Twitter twitter, User user) throws IllegalStateException, TwitterException {
		this.twitter = twitter;
		this.user = user;
		
		follower = new Follower(twitter, user, this);
		unFollower = new UnFollower(twitter, user, this);
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		try {
			gbc.gridheight = 10;
			gbc.gridy = 1;
			add(new JLabel(new ImageIcon(new URL(user.getOriginalProfileImageURL()))), gbc);
			gbc.gridheight = 1;
		} catch (MalformedURLException e) {
			ConsolePanel.getSingleton().printMessage(new String[]{
					"Could not load profile image of " + user.getScreenName()
			});
		}
		
		gbc.gridwidth = 3;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(new JLabel(user.getName() + " - @" + user.getScreenName()), gbc);

		/*gbc.gridwidth = 1;
		gbc.gridx = 2;
		add(new JLabel(), gbc);*/

		gbc.gridwidth = 10;
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(new JLabel(user.getDescription()), gbc);

		gbc.gridy = 2;
		add(new JLabel("is following " + user.getFriendsCount() + " other users"), gbc);

		gbc.gridy = 3;
		add(new JLabel("is followed by " + user.getFollowersCount() + " other users"), gbc);

		gbc.gridy = 4;
		add(new JLabel("is listed in " + user.getListedCount() + " lists"), gbc);
		
		gbc.gridy = 5;
		add(new JLabel("has written " + user.getStatusesCount() + " tweets"), gbc);
		
		gbc.gridy = 6;
		add(new JLabel("has favourited " + user.getFavouritesCount() + " tweets"), gbc);
		
		gbc.gridy = 7;
		add(new JLabel("has created his account at " + user.getCreatedAt()), gbc);
		
		followBtn = new JButton();
		followStatusChanged();
		gbc.gridy = 8;
		add(followBtn, gbc);
	}
	
	public void followStatusChanged() throws IllegalStateException, TwitterException {
		PagableResponseList<User> list = twitter.getFriendsList(twitter.getId(), -1);
		
		while(true){
			if(list.contains(user)){
				followBtn.setText("unfollow");
				for (ActionListener listener : followBtn.getActionListeners()) {
					followBtn.removeActionListener(listener);
				}
				followBtn.addActionListener(unFollower);
				break;
			}
			else if(list.hasNext()) {
				list = twitter.getFriendsList(twitter.getId(), list.getNextCursor());
			}
			else {
				followBtn.setText("follow");
				for (ActionListener listener : followBtn.getActionListeners()) {
					followBtn.removeActionListener(listener);
				}
				followBtn.addActionListener(follower);
				break;
			}
		}
	}
}