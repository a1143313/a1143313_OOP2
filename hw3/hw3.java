import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class hw3 extends JFrame {
    // 介面元件
    private JComboBox<String> typeComboBox;
    private JTextField sourceField;
    private JComboBox<String> sourceUnitComboBox;
    private JTextField targetField;
    private JComboBox<String> targetUnitComboBox;
    private JButton convertButton;

    // 單位選項資料
    private final String[] types = {"長度", "重量", "溫度"};
    private final String[] lengthUnits = {"公尺", "公分", "英吋", "英尺"};
    private final String[] weightUnits = {"公斤", "公克", "磅", "盎司"};
    private final String[] tempUnits = {"攝氏", "華氏", "克氏"};

    public hw3() {
        // 1. 視窗基本設定 (480x280, BorderLayout)
        setTitle("單位換算器");
        setSize(480, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 視窗置中
        setLayout(new BorderLayout(10, 10));

        // 2. NORTH：JComboBox 選換算類型
        typeComboBox = new JComboBox<>(types);
        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("換算類型："));
        northPanel.add(typeComboBox);
        add(northPanel, BorderLayout.NORTH);

        // 3. CENTER：兩列（來源單位、目標單位）
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        // 第一列：來源
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        sourceField = new JTextField(12);
        sourceUnitComboBox = new JComboBox<>(lengthUnits); // 預設為長度
        row1.add(new JLabel("來源："));
        row1.add(sourceField);
        row1.add(sourceUnitComboBox);

        // 第二列：目標
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        targetField = new JTextField(12);
        targetField.setEditable(false); // 結果欄位設為唯讀
        targetUnitComboBox = new JComboBox<>(lengthUnits); // 預設為長度
        row2.add(new JLabel("目標："));
        row2.add(targetField);
        row2.add(targetUnitComboBox);

        centerPanel.add(row1);
        centerPanel.add(row2);
        add(centerPanel, BorderLayout.CENTER);

        // 4. SOUTH：「換算」按鈕
        convertButton = new JButton("換算");
        JPanel southPanel = new JPanel();
        southPanel.add(convertButton);
        add(southPanel, BorderLayout.SOUTH);

        // 5. 事件綁定：切換換算類型時，更新兩個單位下拉選單
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUnitComboBoxes();
            }
        });

        // 6. 事件綁定：點擊換算按鈕時計算結果
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
    }

    // 更新單位下拉選單選項
    private void updateUnitComboBoxes() {
        String selectedType = (String) typeComboBox.getSelectedItem();
        String[] unitsToSet;

        if ("長度".equals(selectedType)) {
            unitsToSet = lengthUnits;
        } else if ("重量".equals(selectedType)) {
            unitsToSet = weightUnits;
        } else {
            unitsToSet = tempUnits;
        }

        // 重新設定選項模型並清空輸入輸出框
        sourceUnitComboBox.setModel(new DefaultComboBoxModel<>(unitsToSet));
        targetUnitComboBox.setModel(new DefaultComboBoxModel<>(unitsToSet));
        sourceField.setText("");
        targetField.setText("");
    }

    // 執行單位換算邏輯
    private void performConversion() {
        try {
            double inputValue = Double.parseDouble(sourceField.getText().trim());
            String type = (String) typeComboBox.getSelectedItem();
            String fromUnit = (String) sourceUnitComboBox.getSelectedItem();
            String toUnit = (String) targetUnitComboBox.getSelectedItem();
            double result = 0.0;

            if ("長度".equals(type)) {
                result = convertLength(inputValue, fromUnit, toUnit);
            } else if ("重量".equals(type)) {
                result = convertWeight(inputValue, fromUnit, toUnit);
            } else if ("溫度".equals(type)) {
                result = convertTemperature(inputValue, fromUnit, toUnit);
            }

            // 格式化輸出至第二個 JTextField（最多顯示 4 位小數並去除多餘的 0）
            targetField.setText(String.format("%.4f", result).replaceAll("0*$", "").replaceAll("\\.$", ""));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "請在來源欄位輸入有效的數字！", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 長度換算（以「公尺」為基準 - Java 8 相容寫法）
    private double convertLength(double val, String from, String to) {
        double meters;
        switch (from) {
            case "公分":
                meters = val / 100.0;
                break;
            case "英吋":
                meters = val * 0.0254;
                break;
            case "英尺":
                meters = val * 0.3048;
                break;
            default: // 公尺
                meters = val;
                break;
        }

        switch (to) {
            case "公分":
                return meters * 100.0;
            case "英吋":
                return meters / 0.0254;
            case "英尺":
                return meters / 0.3048;
            default: // 公尺
                return meters;
        }
    }

    // 重量換算（以「公斤」為基準 - Java 8 相容寫法）
    private double convertWeight(double val, String from, String to) {
        double kg;
        switch (from) {
            case "公克":
                kg = val / 1000.0;
                break;
            case "磅":
                kg = val * 0.45359237;
                break;
            case "盎司":
                kg = val * 0.02834952;
                break;
            default: // 公斤
                kg = val;
                break;
        }

        switch (to) {
            case "公克":
                return kg * 1000.0;
            case "磅":
                return kg / 0.45359237;
            case "盎司":
                return kg / 0.02834952;
            default: // 公斤
                return kg;
        }
    }

    // 溫度換算（以「攝氏」為基準 - Java 8 相容寫法）
    private double convertTemperature(double val, String from, String to) {
        double celsius;
        switch (from) {
            case "華氏":
                celsius = (val - 32.0) * 5.0 / 9.0;
                break;
            case "克氏":
                celsius = val - 273.15;
                break;
            default: // 攝氏
                celsius = val;
                break;
        }

        switch (to) {
            case "華氏":
                return celsius * 9.0 / 5.0 + 32.0;
            case "克氏":
                return celsius + 273.15;
            default: // 攝氏
                return celsius;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new hw3().setVisible(true);
        });
    }
}