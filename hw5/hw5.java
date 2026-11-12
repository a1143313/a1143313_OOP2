import java.awt.*;
import javax.swing.*;

public class hw5 extends JPanel {

    public hw5() {
        // 設定畫布大小為 500x400[cite: 1]
        setPreferredSize(new Dimension(500, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. 天空與草地 (上半部天藍色、下半部綠色)[cite: 1]
        g.setColor(new Color(135, 206, 235)); // 天藍色
        g.fillRect(0, 0, 500, 260);
        g.setColor(new Color(102, 187, 106)); // 草綠色
        g.fillRect(0, 260, 500, 140);

        // 2. 太陽 (移至右上角：黃色正圓 + for迴圈搭配 Math.cos/Math.sin 畫至少 8 條光芒)[cite: 1]
        g.setColor(Color.YELLOW);
        int sunCenterX = 420;
        int sunCenterY = 70;
        int sunRadius = 30;
        g.fillOval(sunCenterX - sunRadius, sunCenterY - sunRadius, sunRadius * 2, sunRadius * 2);

        int rayCount = 12;
        for (int i = 0; i < rayCount; i++) {
            double angle = Math.toRadians(i * (360.0 / rayCount));
            int startX = (int) (sunCenterX + (sunRadius + 6) * Math.cos(angle));
            int startY = (int) (sunCenterY + (sunRadius + 6) * Math.sin(angle));
            int endX = (int) (sunCenterX + (sunRadius + 20) * Math.cos(angle));
            int endY = (int) (sunCenterY + (sunRadius + 20) * Math.sin(angle));
            g.drawLine(startX, startY, endX, endY);
        }

        // 3. 雲朵 (移至左上角：至少 3 個互相重疊的白色 fillOval)[cite: 1]
        g.setColor(Color.WHITE);
        g.fillOval(50, 50, 55, 40);
        g.fillOval(80, 40, 65, 50);
        g.fillOval(115, 52, 55, 38);

        // 4. 房屋主體 (fillRect + drawRect)[cite: 1]
        g.setColor(new Color(255, 243, 205)); // 淺米黃色牆面
        g.fillRect(160, 180, 180, 130);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(160, 180, 180, 130);

        // 5. 屋頂 (只能用 drawLine 畫出三角形，三條線段)[cite: 1]
        g.setColor(new Color(178, 34, 34)); // 磚紅色線條
        int topX = 250, topY = 100;         // 屋頂頂點
        int leftX = 145, leftY = 180;       // 屋頂左下角
        int rightX = 355, rightY = 180;     // 屋頂右下角
        g.drawLine(topX, topY, leftX, leftY);
        g.drawLine(leftX, leftY, rightX, rightY);
        g.drawLine(rightX, rightY, topX, topY);

        // 6. 門 (fillRoundRect + 小的 fillOval 當門把)[cite: 1]
        g.setColor(new Color(139, 69, 19)); // 咖啡色木門
        g.fillRoundRect(230, 235, 40, 75, 12, 12);
        g.setColor(Color.ORANGE);           // 金黃色門把
        g.fillOval(260, 272, 6, 6);

        // 7. 窗戶 (至少 2 扇，fillRect + drawRect + drawLine 畫十字窗框)[cite: 1]
        // 左邊窗戶
        g.setColor(new Color(224, 247, 250));
        g.fillRect(180, 200, 36, 36);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(180, 200, 36, 36);
        g.drawLine(198, 200, 198, 236); // 垂直線
        g.drawLine(180, 218, 216, 218); // 水平線

        // 右邊窗戶
        g.setColor(new Color(224, 247, 250));
        g.fillRect(284, 200, 36, 36);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(284, 200, 36, 36);
        g.drawLine(302, 200, 302, 236); // 垂直線
        g.drawLine(284, 218, 320, 218); // 水平線

        // 8. 在畫面角落簽名 (請修改為你的學號與姓名)[cite: 1]
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("A1143313 梁祐甄", 360, 385);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("回家作業：我的小房子");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new hw5());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}