import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class hw918_1 extends JFrame {
    private JTextField textUser;
    private JPasswordField textPassword;

    public hw918_1() {
        setTitle("登入");
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 視窗置中

        // 使用 LayoutManager（例如 GridBagLayout 或 GridLayout）
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel labelUser = new JLabel("帳號:");
        textUser = new JTextField();

        JLabel labelPassword = new JLabel("密碼:");
        textPassword = new JPasswordField(); // 正確使用密碼輸入框

        JButton btnLogin = new JButton("登入");

        // 加入元件
        panel.add(labelUser);
        panel.add(textUser);
        panel.add(labelPassword);
        panel.add(textPassword);
        panel.add(new JLabel("")); // 佔位格
        panel.add(btnLogin);

        add(panel);

        // 事件監聽與正確的字串內容比較 (.equals)
        btnLogin.addActionListener((ActionEvent e) -> {
            String user = textUser.getText();
            String password = new String(textPassword.getPassword());

            if ("admin".equals(user) && "123456".equals(password)) {
                JOptionPane.showMessageDialog(this, "登入成功！");
            } else {
                JOptionPane.showMessageDialog(this, "帳號或密碼錯誤", "錯誤", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 最後才顯示視窗
        setVisible(true);
    }

    public static void main(String[] args) {
        // 確保在 EDT 執行緒中建立 UI
        SwingUtilities.invokeLater(() -> new hw918_1());
    }
}