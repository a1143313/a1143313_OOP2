import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class hw918_2 extends JFrame {
    private JLabel statusLabel;  // 上方統計訊息
    private JLabel diceLabel;    // 中央骰子點數
    private JButton rollButton;  // 下方擲骰子按鈕

    private int count = 0;       // 擲骰次數 N
    private int sum = 0;         // 總和 M
    private Random random = new Random();

    public hw918_2() {
        // 1. 視窗標題與基本設定
        setTitle("骰子模擬器");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 開啟時置中
        setLayout(new BorderLayout());

        // 2. 視窗上方顯示統計訊息
        statusLabel = new JLabel("已擲 0 次，總和 0，平均 0.00", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        add(statusLabel, BorderLayout.NORTH);

        // 3. 中央 JLabel 顯示目前點數 (預設為 - 或 0，字體 60pt)
        diceLabel = new JLabel("-", SwingConstants.CENTER);
        diceLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        add(diceLabel, BorderLayout.CENTER);

        // 4. 下方「擲骰子」按鈕
        rollButton = new JButton("擲骰子");
        rollButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        add(rollButton, BorderLayout.SOUTH);

        // 5. 按鈕事件處理
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });
    }

    private void rollDice() {
        // 隨機產生 1–6 的點數
        int diceValue = random.nextInt(6) + 1;

        // 更新統計數據
        count++;
        sum += diceValue;
        double average = (double) sum / count;

        // 更新上方狀態欄
        statusLabel.setText(String.format("已擲 %d 次，總和 %d，平均 %.2f", count, sum, average));

        // 更新中央點數顯示
        diceLabel.setText(String.valueOf(diceValue));

        // 根據點數變更顏色：6 為綠色、1 為紅色、其餘黑色
        if (diceValue == 6) {
            diceLabel.setForeground(Color.GREEN);
        } else if (diceValue == 1) {
            diceLabel.setForeground(Color.RED);
        } else {
            diceLabel.setForeground(Color.BLACK);
        }
    }

    public static void main(String[] args) {
        // 確保 GUI 在 EDT (Event Dispatch Thread) 中啟動
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new hw918_2().setVisible(true);
            }
        });
    }
}