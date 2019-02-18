import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

	private JFrame frame;
	private JTextField textField;
	private String password; //text that was saved
	private int passLength = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		password = "";
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				String rawPass = textField.getText();
				if(rawPass.length() > passLength) {
					for(int i = 0 ; i < rawPass.length() ; i++) {
						if(rawPass.charAt(i) != '*') {
							password += rawPass.charAt(i);
						}
					}	
				}else if(rawPass.length() < passLength) {
					password = password.substring(0, rawPass.length());
				}
				passLength = rawPass.length();
				String asterisks = "";
				for(int i = 0 ; i < passLength ; i++) {
					asterisks += "*";
				}
				System.out.println(password);
				textField.setText(asterisks);
			}
		});
		
		textField.setBounds(137, 113, 208, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}
}
