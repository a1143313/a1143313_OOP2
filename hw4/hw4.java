import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class hw4 extends JFrame {
    private JLabel statsLabel;
    private JLabel diceLabel;
    private JButton rollButton;
    
    private int rollCount = 0; // 已擲次數 N
    private int totalSum = 0;  // 總和 M
    private Random random;

    public hw4() {
        random = new Random();

        // 1. 視窗標題「骰子模擬器」，尺寸 400x320，關閉時結束程式，開啟時置中
        setTitle("骰子模擬器");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 開啟時置中
        setLayout(new BorderLayout());

        // 5. 視窗上方顯示「已擲 N 次，總和 M，平均 X.XX」
        statsLabel = new JLabel("已擲 0 次，總和 0，平均 0.00", SwingConstants.CENTER);
        statsLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
        statsLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        add(statsLabel, BorderLayout.NORTH);

        // 2. 中央有一個 JLabel 顯示目前點數，字體 60pt
        diceLabel = new JLabel("?", SwingConstants.CENTER);
        diceLabel.setFont(new Font("Arial", Font.BOLD, 60));
        add(diceLabel, BorderLayout.CENTER);

        // 3. 下方一個「擲骰子」按鈕
        rollButton = new JButton("擲骰子");
        rollButton.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        rollButton.setFocusPainted(false);
        add(rollButton, BorderLayout.SOUTH);

        // 4. 每按一次隨機產生 1-6 的點數並更新顯示
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });
    }

    private void rollDice() {
        // 產生 1 到 6 的隨機整數
        int point = random.nextInt(6) + 1;

        // 更新統計數據
        rollCount++;
        totalSum += point;
        double average = (double) totalSum / rollCount;

        // 更新上方統計文字（平均格式化至小數點後兩位 X.XX）
        statsLabel.setText(String.format("已擲 %d 次，總和 %d，平均 %.2f", rollCount, totalSum, average));

        // 6. 點數為 6 時變綠色，為 1 時變紅色，其餘黑色
        Color pipColor;
        if (point == 6) {
            pipColor = new Color(0, 165, 60); // 質感翠綠色
        } else if (point == 1) {
            pipColor = new Color(225, 35, 35); // 質感亮紅色
        } else {
            pipColor = new Color(35, 35, 40); // 深炭黑色
        }

        // 清除初始的 "?" 文字，並設定動態繪製的 150x150 立體骰子圖示
        diceLabel.setText("");
        diceLabel.setForeground(pipColor);
        diceLabel.setIcon(create3DDiceIcon(point, pipColor, 150));
    }

    /**
     * 動態繪製帶有陰影與立體漸層效果的骰子圖示
     */
    private ImageIcon create3DDiceIcon(int point, Color pipColor, int canvasSize) {
        BufferedImage img = new BufferedImage(canvasSize, canvasSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        // 開啟反鋸齒，讓圓角與圓點邊緣極度平滑
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int padding = 16;
        int diceSize = canvasSize - padding * 2;
        int arc = 32; // 骰子圓角弧度

        // 1. 繪製多層柔和外部陰影（營造懸浮立體感）
        for (int i = 6; i >= 1; i--) {
            g2.setColor(new Color(0, 0, 0, 10));
            g2.fill(new RoundRectangle2D.Double(padding + i, padding + i + 2, diceSize, diceSize, arc, arc));
        }

        // 2. 繪製骰子本體（左上亮白到右下淺灰的立體漸層）
        GradientPaint bodyGradient = new GradientPaint(
                padding, padding, Color.WHITE,
                padding + diceSize, padding + diceSize, new Color(222, 226, 232)
        );
        g2.setPaint(bodyGradient);
        g2.fill(new RoundRectangle2D.Double(padding, padding, diceSize, diceSize, arc, arc));

        // 3. 繪製骰子外框與內部高光邊緣
        g2.setColor(new Color(185, 190, 200));
        g2.setStroke(new BasicStroke(2f));
        g2.draw(new RoundRectangle2D.Double(padding, padding, diceSize, diceSize, arc, arc));

        g2.setColor(new Color(255, 255, 255, 220));
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Double(padding + 2, padding + 2, diceSize - 4, diceSize - 4, arc - 2, arc - 2));

        // 4. 繪製立體凹陷點數
        int pipRadius = (point == 1) ? 16 : 12; // 1 點依傳統骰子習慣畫得稍大一點
        int left = padding + diceSize / 4;
        int center = padding + diceSize / 2;
        int right = padding + (diceSize * 3) / 4;
        int top = padding + diceSize / 4;
        int middle = padding + diceSize / 2;
        int bottom = padding + (diceSize * 3) / 4;

        if (point == 1 || point == 3 || point == 5) {
            draw3DPip(g2, center, middle, pipRadius, pipColor);
        }
        if (point >= 2) {
            draw3DPip(g2, left, top, pipRadius, pipColor);
            draw3DPip(g2, right, bottom, pipRadius, pipColor);
        }
        if (point >= 4) {
            draw3DPip(g2, right, top, pipRadius, pipColor);
            draw3DPip(g2, left, bottom, pipRadius, pipColor);
        }
        if (point == 6) {
            draw3DPip(g2, left, middle, pipRadius, pipColor);
            draw3DPip(g2, right, middle, pipRadius, pipColor);
        }

        g2.dispose();
        return new ImageIcon(img);
    }

    /**
     * 繪製單顆帶有內凹陰影與高光的立體點數
     */
    private void draw3DPip(Graphics2D g2, int cx, int cy, int r, Color color) {
        // 點數下方微亮反光（模擬凹槽邊緣受光）
        g2.setColor(new Color(255, 255, 255, 200));
        g2.fillOval(cx - r, cy - r + 2, r * 2, r * 2);

        // 點數本體漸層（上方深色內陰影、下方原色）
        GradientPaint pipGradient = new GradientPaint(
                cx, cy - r, color.darker().darker(),
                cx, cy + r, color
        );
        g2.setPaint(pipGradient);
        g2.fillOval(cx - r, cy - r, r * 2, r * 2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new hw4().setVisible(true);
            }
        });
    }
}