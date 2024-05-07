import java.io.*;
import javax.swing.*;
import java.awt.event.*;

public class BatteryLogGenerator extends JFrame {

    public BatteryLogGenerator() {
        setTitle("Battery Log Generator");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(null);

        JButton confirmButton = new JButton("Generate");
        confirmButton.setBounds(100, 60, 100, 30);
        panel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateBatteryLog();
            }
        });
    }

    private void generateBatteryLog() {
        String batteryLog = "powercfg /batteryreport /output \"" + System.getProperty("user.home")
                + "\\battery_report.html\"";
        String filePath = System.getProperty("user.home") + "\\batteryReport.bat";

        try {
            FileWriter file = new FileWriter(filePath);
            PrintWriter saver = new PrintWriter(file);

            saver.println("@echo off");
            saver.println(batteryLog);

            saver.println("start " + System.getProperty("user.home") + "\\battery_report.html");

            saver.close();
            file.close();

            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", filePath);
            Process process = builder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                JOptionPane.showMessageDialog(this, "File created and executed successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to execute the batch file. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while creating or executing the batch file:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BatteryLogGenerator blg = new BatteryLogGenerator();
                blg.setVisible(true);
            }
        });
    }
}