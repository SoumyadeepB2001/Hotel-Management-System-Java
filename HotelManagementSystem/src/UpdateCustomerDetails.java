import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.awt.*;

public class UpdateCustomerDetails extends JFrame implements ActionListener, KeyListener {
	JButton checkButton, updateButton;
	JPanel contentPane;
	public JLabel l1, c_idLab, nameLab, roomNumLab, paidLab, pendingLab, ageLab, genderLab, countryLab, phoneLab;
	public JTextField c_id, name, paid, pending, age, country, phone;
	JComboBox<String> gender;
	String genders[] = { "Female", "Male", "Other" };
	Choice roomNum;
	String roomNumberCheck;// Stores the current room number the customer is staying in

	public static void main(String[] args) {
		new UpdateCustomerDetails();
	}

	UpdateCustomerDetails() {
		this.setTitle("Update Customer Details");
		setVisible(true);
		setResizable(false);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		l1 = new JLabel("Update Customer Details");
		l1.setFont(new Font("Serif", Font.BOLD, 17));
		l1.setBounds(120, 25, 338, 30);
		add(l1);

		// Adding textfields and labels
		c_idLab = new JLabel("Customer ID");
		c_idLab.setFont(new Font("Serif", Font.BOLD, 17));
		c_idLab.setBounds(50, 80, 100, 30);
		add(c_idLab);
		c_id = new JTextField();
		c_id.setBounds(180, 80, 210, 30);
		add(c_id);

		nameLab = new JLabel("Name");
		nameLab.setFont(new Font("Serif", Font.BOLD, 17));
		nameLab.setBounds(50, 130, 100, 30);
		add(nameLab);
		name = new JTextField();
		name.setBounds(180, 130, 210, 30);
		add(name);
		name.setEditable(false);

		ageLab = new JLabel("Age");
		ageLab.setFont(new Font("Serif", Font.BOLD, 17));
		ageLab.setBounds(50, 180, 100, 30);
		add(ageLab);
		age = new JTextField();
		age.setBounds(180, 180, 210, 30);
		add(age);
		age.setEditable(false);

		genderLab = new JLabel("Gender");
		genderLab.setFont(new Font("Serif", Font.BOLD, 17));
		genderLab.setBounds(50, 230, 100, 30);
		add(genderLab);
		gender = new JComboBox<>(genders);
		gender.setBounds(180, 230, 210, 30);
		add(gender);
		gender.setVisible(false);

		countryLab = new JLabel("Country");
		countryLab.setFont(new Font("Serif", Font.BOLD, 17));
		countryLab.setBounds(50, 280, 100, 30);
		add(countryLab);
		country = new JTextField();
		country.setBounds(180, 280, 210, 30);
		add(country);
		country.setEditable(false);

		phoneLab = new JLabel("Phone");
		phoneLab.setFont(new Font("Serif", Font.BOLD, 17));
		phoneLab.setBounds(50, 330, 100, 30);
		add(phoneLab);
		phone = new JTextField();
		phone.setBounds(180, 330, 210, 30);
		add(phone);
		phone.setEditable(false);

		roomNumLab = new JLabel("Room No.");
		roomNumLab.setFont(new Font("Serif", Font.BOLD, 17));
		roomNumLab.setBounds(50, 380, 100, 30);
		add(roomNumLab);
		roomNum = new Choice();
		roomNum.setBounds(180, 380, 210, 30);
		add(roomNum);
		roomNum.setVisible(false);

		paidLab = new JLabel("Amount Paid (₹)");
		paidLab.setFont(new Font("Serif", Font.BOLD, 17));
		paidLab.setBounds(50, 430, 125, 30);
		add(paidLab);
		paid = new JTextField();
		paid.setBounds(180, 430, 210, 30);
		add(paid);
		paid.setEditable(false);
		paid.addKeyListener(this);

		pendingLab = new JLabel("Pending (₹)");
		pendingLab.setFont(new Font("Serif", Font.BOLD, 17));
		pendingLab.setBounds(50, 480, 125, 30);
		add(pendingLab);
		pending = new JTextField();
		pending.setBounds(180, 480, 210, 30);
		add(pending);
		pending.setEditable(false);

		updateButton = new JButton("Update");
		updateButton.setBounds(240, 540, 100, 30);
		add(updateButton);

		checkButton = new JButton("Check");
		checkButton.setBounds(90, 540, 100, 30);
		add(checkButton);

		updateButton.addActionListener(this);
		checkButton.addActionListener(this);

		setBounds(550, 200, 438, 630);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "Check":
				check();
				break;

			case "Update":
				update();
				break;

		}

	}

	void check() {
		try {
			DBCon c = new DBCon();
			String c_idStr = c_id.getText();
			String info;
			ResultSet rs, rs2;
			if (c_idStr.equals("")) { // Error message for when the eid is empty
				JOptionPane.showMessageDialog(null, "Please enter the Customer ID");
				// Disabling the textfields
				name.setEditable(false);
				age.setEditable(false);
				country.setEditable(false);
				phone.setEditable(false);
				roomNum.setVisible(false);
				paid.setEditable(false);
				gender.setVisible(false);
				// Setting the text fields
				name.setText("");
				age.setText("");
				country.setText("");
				gender.setSelectedItem("");
				phone.setText("");
				paid.setText("");
				pending.setText("");
				return;
			}
			info = "select * from customer where idnum= '" + c_idStr + "'";
			rs = c.s.executeQuery(info);

			if (!rs.isBeforeFirst()) {
				JOptionPane.showMessageDialog(null, "Record not found");
				c_id.setText("");
				// Disabling the textfields
				name.setEditable(false);
				age.setEditable(false);
				country.setEditable(false);
				phone.setEditable(false);
				roomNum.setVisible(false);
				paid.setEditable(false);
				gender.setVisible(false);
				// Setting the text fields
				name.setText("");
				age.setText("");
				country.setText("");
				gender.setSelectedItem("");
				phone.setText("");
				paid.setText("");
				pending.setText("");
				return;
			}

			while (rs.next()) {
				String nameStr = rs.getString("name");
				int ageInt = rs.getInt("age");
				int depositInt = rs.getInt("deposit");
				String genderStr = rs.getString("gender");
				String countryStr = rs.getString("country");
				long phoneLong = rs.getLong("phone");
				String roomNumStr = rs.getString("r_num");
				roomNumberCheck = rs.getString("r_num");
				// Add the current room the customer is staying in
				roomNum.add(roomNumStr);
				// Fetches the list of currently available rooms
				try {
					DBCon db = new DBCon();
					ResultSet rs1 = db.s.executeQuery("SELECT * FROM room WHERE availability = 'Available'");
					while (rs1.next()) {
						roomNum.add(rs1.getString("r_num"));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				// Enabling the textfields
				name.setEditable(true);
				age.setEditable(true);
				country.setEditable(true);
				phone.setEditable(true);
				roomNum.setVisible(true);
				paid.setEditable(true);
				gender.setVisible(true);
				// Setting the textfields
				name.setText(nameStr);
				age.setText(Integer.toString(ageInt));
				country.setText(countryStr);
				gender.setSelectedItem(genderStr);
				phone.setText(Long.toString(phoneLong));
				paid.setText(Integer.toString(depositInt));
				pending.setText("");

				String roomPrice = "SELECT * FROM room WHERE r_num= '" + roomNum.getSelectedItem() + "'";
				rs2 = c.s2.executeQuery(roomPrice);
				while (rs2.next()) {
					long priceLong = rs2.getLong("price");
					priceLong = priceLong - depositInt;
					pending.setText(Long.toString(priceLong));
				}

			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
			ex.printStackTrace();
		}
	}

	void update() {
		if (c_id.getText().equals("") || name.getText().equals("") || paid.getText().equals("")
				|| age.getText().equals("") || country.getText().equals("") || phone.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please fill all the details");
			// Disabling the textfields
			name.setEditable(false);
			age.setEditable(false);
			country.setEditable(false);
			phone.setEditable(false);
			roomNum.setVisible(false);
			paid.setEditable(false);
			gender.setVisible(false);
			// Setting the text fields
			name.setText("");
			age.setText("");
			country.setText("");
			gender.setSelectedItem("");
			phone.setText("");
			paid.setText("");
			pending.setText("");
			return;
		}

		try {
			String nameStr = name.getText();
			int ageInt = Integer.parseInt(age.getText());
			String countryStr = country.getText();
			long phoneLong = Long.parseLong(phone.getText());
			String genderStr = (String) gender.getSelectedItem();
			String roomNumStr = (String) roomNum.getSelectedItem();
			int paidAmt = Integer.parseInt(paid.getText());
			String c_idStr = c_id.getText();

			// Updating Customer details
			DBCon c = new DBCon();
			String str = "UPDATE customer SET name ='" + nameStr + "', age =" + ageInt + ",gender ='" + genderStr
					+ "', country='" + countryStr + "', phone=" + phoneLong + ", r_num='"
					+ roomNumStr + "', deposit =" + paidAmt + " WHERE idnum = '" + c_idStr + "'";
			c.s.executeUpdate(str);

			// Making previous alloted room available
			try {
				DBCon db2 = new DBCon();
				// Set the current room as available
				db2.s.executeUpdate(
						"UPDATE room SET availability = 'Available' WHERE r_num = '" + roomNumberCheck + "'");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// Updating availability of new alloted room
			DBCon c2 = new DBCon();
			c2.s.executeUpdate("UPDATE room SET availability = 'Unavailable' WHERE r_num = '" + roomNumStr + "'");
			JOptionPane.showMessageDialog(null, "Cutomer details updated");

			dispose();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex + "\nFill all the Text Fields");
			ex.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	// Fetches the pending amount on updating the paid amount
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == paid) {
			DBCon c = new DBCon();
			ResultSet rs;
			String info = "SELECT * FROM room WHERE r_num= '" + roomNum.getSelectedItem() + "'";
			try {
				if (!paid.getText().equals("")) {
					rs = c.s2.executeQuery(info);
					while (rs.next()) {
						long priceLong = rs.getLong("price");
						priceLong = priceLong - Integer.parseInt(paid.getText());
						pending.setText(Long.toString(priceLong));
					}
				} else {
					pending.setText("");
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex);
				ex.printStackTrace();
			}
		}

	}
}
