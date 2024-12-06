package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Objects;

public class GameWIindou extends JFrame {
    public GameWIindou() {
        // 标题
        setTitle("游戏");
        // 大小
        setSize(1200, 800);
        // 设置窗口关闭操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置布局管理器为 BorderLayout
        setLayout(new BorderLayout());

        // 创建顶部卡槽框
        JPanel topSlotPanel = new JPanel();
        topSlotPanel.setBackground(Color.GRAY); // 设置背景颜色以便观察
        topSlotPanel.setPreferredSize(new Dimension(1200, 150)); // 设置首选大小
        topSlotPanel.setBorder(new LineBorder(Color.BLACK, 1)); // 设置边框样式
        topSlotPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // 使用 FlowLayout 左对齐，并设置组件之间的间距

        // 添加阳光图标和数量数字
        ImageIcon sunIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/sun.png")));
        if (sunIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            // 调整图片大小
            Image scaledImage = sunIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 将图片调整为100x100像素
            ImageIcon scaledSunIcon = new ImageIcon(scaledImage);
            JLabel sunLabel = new JLabel(scaledSunIcon);

            // 创建一个垂直的 JPanel 来放置 sunLabel 和 sunCountLabel
            JPanel verticalPanel = new JPanel();
            verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
            verticalPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // 确保垂直面板左对齐

            // 添加阳光图标
            verticalPanel.add(sunLabel);

            // 添加阳光数量数字
            JLabel sunCountLabel = new JLabel("100"); // 假设初始阳光数量为 100
            sunCountLabel.setFont(new Font("Arial", Font.BOLD, 24));
            verticalPanel.add(sunCountLabel);

            // 将垂直面板添加到 topSlotPanel
            topSlotPanel.add(verticalPanel);
        } else {
            JLabel sunLabel = new JLabel("无法加载图像");
            topSlotPanel.add(sunLabel);
        }

        add(topSlotPanel, BorderLayout.NORTH);



        // 创建游戏区域
        JPanel gameAreaPanel = new JPanel();
        gameAreaPanel.setLayout(new GridLayout(5, 9));
        for (int i = 0; i < 5 * 9; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(Color.WHITE); // 设置背景颜色以便观察
            cell.setBorder(new LineBorder(Color.BLACK, 1)); // 设置边框样式
            gameAreaPanel.add(cell);
        }
        add(gameAreaPanel, BorderLayout.CENTER);

        // 设置窗口可见
        setVisible(true);
    }

    public static void main(String[] args) {
        new GameWIindou();
    }
}
