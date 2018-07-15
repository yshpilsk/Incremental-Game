package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Player;
import controller.AutoIncrementer;

public class UserPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// buttons
	private JButton increment = new JButton("Increment");
	private JButton autoClick = new JButton("Auto click OFF");
	//private JButton somethingCute = new JButton("Something cute");
	private JButton quit = new JButton("Quit");
	private ButtonListener buttList = new ButtonListener();
	private Image image = Toolkit.getDefaultToolkit().createImage("art/Goku Level 1.png");
	private ImageIcon xIcon = new ImageIcon(image);
	
	// panels
	private TopPanel top = new TopPanel();
	private ProgressBar progress = new ProgressBar();
	private UserPanel me = this;
	
	// strings
	private String quitMsg = "Woah woah woah, what's that quit button doing there?\nYou can't quit incremental games ! -1 point.";
	private String prestige = "Secret prestige ! Congratulation, you are smart and I love you <3";
	private String dlc = "You win the Zlsa DLC ! Increment value * 2";
	
	// others
	private Player player;
	protected AutoIncrementer incrementer;
	private JLabel score;
	private JLabel level;
	private JLabel characterIcon;

	public UserPanel(Player player) {
		// create player
		this.player = player;
		score = new JLabel("Power Level: "+player.getScore());
		level = new JLabel("Saiyan Level: "+player.getLevel());
		characterIcon = new JLabel(xIcon);
		
		
		xIcon.setImageObserver(this);
		
		// set panel settings
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(TIGview.BACKGROUND);
		
		// configure elements
		increment.addActionListener(buttList);
		autoClick.addActionListener(buttList);
		quit.addActionListener(buttList);

		// align elements
		top.setAlignmentX(CENTER_ALIGNMENT);
		score.setAlignmentX(CENTER_ALIGNMENT);
		level.setAlignmentX(CENTER_ALIGNMENT);
		characterIcon.setAlignmentX(CENTER_ALIGNMENT);
		progress.setAlignmentX(CENTER_ALIGNMENT);
		quit.setAlignmentX(CENTER_ALIGNMENT);
		
		this.add(top);
		this.add(score);
		this.add(level);
		this.add(characterIcon);
		this.add(progress);
		this.add(quit);
	}
	
	public void updateStats() {
		score.setText("Power Level: "+player.getScore());
		level.setText("Saiyan Level: "+player.getLevel());
		xIcon.setImage(Toolkit.getDefaultToolkit().createImage("art/"+player.getLevel()+".gif"));
		progress.repaint();
	}
	
	private class TopPanel extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TopPanel() {
			this.setLayout( new FlowLayout() );
			this.setBackground(TIGview.BACKGROUND);
			
			this.add( increment );
			this.add( autoClick );
			//this.add( somethingCute);
		}
	}
	
	private class ProgressBar extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// define the width and height of the progress bar
		final int BAR_WIDTH = 100;
		final int BAR_HEIGHT = 20;
		
		public ProgressBar() {
			this.setLayout(null);
			this.setBackground(TIGview.BACKGROUND);
			
		}
		
		@Override
		public void paintComponent(Graphics r) {
			super.paintComponent(r);
			Graphics2D g = (Graphics2D) r;
			
			GradientPaint gpGrey = new GradientPaint(0, 0, new Color(0xffffff), 0, BAR_HEIGHT+10, new Color(0xbbbbbb));
			GradientPaint gpGreen = new GradientPaint(0, 0, new Color(0xafdf7f), 0, BAR_HEIGHT+10, new Color(0x7db03a));

			int x = (this.getWidth() / 2) - (BAR_WIDTH / 2);
			
			g.setPaint(gpGrey);
			g.fillRect(x, 10, BAR_WIDTH, BAR_HEIGHT);
			
			int size = (int) ( getPercent() * BAR_WIDTH );
			
			g.setPaint(gpGreen);
			g.fillRect(x, 10, size, BAR_HEIGHT);
			
		}
		
		private double getPercent() {
			double percent = Math.log(player.getScore()) / 10;
			
			return Math.min(percent, 1);
		}
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if( e.getSource() == increment ) {
				player.incrementScore();
				
				me.updateStats();
				
				if( player.getScore() == 50 ) {
					player.changeScoreFacotorBy(2);
					if( player.getScoreFactor() == 2 ) {
						TIGview.SUBTITLE.setText(dlc);
					} else {
						TIGview.SUBTITLE.setText(prestige);
					}
				}
					
			} else if( e.getSource() == quit ) {
				player.decrementScore();

				me.updateStats();
				JOptionPane.showMessageDialog(top, quitMsg);
			} else if( e.getSource() == autoClick ) {
				if( incrementer != null && incrementer.isRunning() ) {
					incrementer.halt();
					autoClick.setText("Auto click OFF");
				} else {
					(incrementer = new AutoIncrementer(player, me)).start();
					autoClick.setText("Auto click ON");
				}
			}
		}
		
	}
	
}
