package br.ufpe.cin.siscom.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.ufpe.cin.siscom.dfsa.estimator.EomLee;
import br.ufpe.cin.siscom.dfsa.estimator.Estimator;
import br.ufpe.cin.siscom.dfsa.estimator.LowerBound;
import br.ufpe.cin.siscom.dfsa.thread.DFSASimulator;

public class MainView extends JFrame {

	private static final long serialVersionUID = 2521996542244705542L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblSteps;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(33, 66, 114, 19);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(153, 66, 114, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int tags = Integer.parseInt(textField.getText());
					int increaseStep = Integer.parseInt(textField_1.getText());
					int simulations = Integer.parseInt(textField_2.getText());
					Estimator estimator = null;
					
					if (textField_3.getText().equalsIgnoreCase("Lower bound")) {
						estimator = new LowerBound();
					}
					else if (textField_3.getText().equalsIgnoreCase("Eom Lee")) {
						estimator = new EomLee();
					}
					
					if (estimator == null) {
						JOptionPane.showMessageDialog(null, "Invalid estimator");
					}
					else {						
						(new Thread(new DFSASimulator(tags, increaseStep, simulations, estimator))).start();
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Please enter valid numbers");
				}
			}
		});
		btnStart.setBounds(163, 210, 117, 25);
		contentPane.add(btnStart);

		textField_2 = new JTextField();
		textField_2.setBounds(279, 66, 114, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblTags = new JLabel("Tags");
		lblTags.setBounds(40, 40, 70, 15);
		contentPane.add(lblTags);

		JLabel lblIncreaseStep = new JLabel("Increase Step");
		lblIncreaseStep.setBounds(153, 40, 118, 15);
		contentPane.add(lblIncreaseStep);
		
		lblSteps = new JLabel("Max number of tags");
		lblSteps.setBounds(285, 0, 140, 83);
		contentPane.add(lblSteps);
		
		JLabel lblEstimator = new JLabel("Estimator");
		lblEstimator.setBounds(33, 138, 70, 15);
		contentPane.add(lblEstimator);
		
		textField_3 = new JTextField();
		textField_3.setBounds(33, 165, 114, 19);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
	}
}
