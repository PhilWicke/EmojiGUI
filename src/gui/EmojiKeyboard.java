package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/***
 * This class provides a GUI keyboard to select from a range of Emoji stored
 * in the Source/images folder. The keyboard will generate the Emoji Unicode
 * of the corresponding Emoji.
 * @author Wicke
 *
 */
public class EmojiKeyboard {

	// Set Emoji images path
	private String emojiPath = "\\images\\";

	private JFrame frame;
	private JButton[] buttons;
	private JButton refresh;
	private File folder = new File("./Source/images/");
	private File[] listOfFiles = folder.listFiles();
	private int num_emoji = listOfFiles.length - noPicNum(listOfFiles);
	private String[] unicodes = new String[num_emoji];
	
	/**
	 * Takes a file and returns the extension as a string
	 * @param file
	 * @return the extension of the file as string
	 */
	private String getExtension(String file){
		String extension = "";

		int i = file.lastIndexOf('.');
		if (i > 0) {
		    extension = file.substring(i+1);
		}
		return extension;	
	}
	
	/**
	 * Returns the number of jpg pictures in a file
	 * @param listOfFiles
	 * @return integer number of jpgs in file
	 */
	private int noPicNum(File[] listOfFiles){
		String extension;
		int count = 0;
		for (int i = 0; i < listOfFiles.length; i++) {
			extension = getExtension(listOfFiles[i].getName()); 
			if(!(extension.equals("jpg"))){
				count++;
			}
		}
		return count;
	}

	/**
	 * Build interface
	 */
	private void makeComponents() {
		frame = new JFrame("Emoji to Unicode - Keyboard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		buttons = new JButton[num_emoji];
		refresh = new JButton("X");
		refresh.setBackground(Color.RED);
		refresh.setOpaque(true);
	
		String tempfile;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				tempfile 	= listOfFiles[i].getName();
				if(tempfile.contains(".jpg")){
					String[] parts 	= tempfile.split(".jpg");
					String unicode 	= parts[0];
					unicodes[i] 		= unicode;
				}
			}
		}
		
		for(int i = 0; i < num_emoji; i++){
			buttons[i] = new JButton(new ImageIcon(getClass().getClassLoader().
					getResource(emojiPath+"\\"+unicodes[i]+".jpg")));
		}
	}


	private JPanel makeEmojiPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Emoji"));
		panel.setLayout(new GridLayout(10, 10));

		for(int i = 0; i < num_emoji; i++)
		{
			panel.add(buttons[i]);
		}
		panel.add(refresh);
		
		ButtonGroup bg = new ButtonGroup();
		for(int i = 0; i < num_emoji; i++)
		{
			bg.add(buttons[i]);
		}
		bg.add(refresh);

		EmojiListener emojiListener = new EmojiListener();

		for(int i = 0; i < num_emoji; i++)
		{
			buttons[i].addActionListener(emojiListener);
		}
		refresh.addActionListener(emojiListener);
		
		return panel;
	}

	private void makeLayout() {
		frame.add(makeEmojiPanel());
		frame.pack();
	}

	private void setVisible() {
		frame.setVisible(true);
	}

	public class EmojiListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < num_emoji; i++)
				if(e.getSource() == buttons[i]) {
					System.out.print(unicodes[i]+ " ");
				}
				else if(e.getSource() == refresh){
					System.out.println();
				}
		}

	}

	public static void main(String[] args) {
		EmojiKeyboard keyboard = new EmojiKeyboard();
		keyboard.makeComponents();
		keyboard.makeLayout();
		keyboard.setVisible();
	}

}