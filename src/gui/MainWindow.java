package gui;

import game.GameManager;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        // 设置窗口属性
        setTitle("开始菜单");
        setSize(1260, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // 设置背景图片
        ImageIcon backgroundImage = new ImageIcon("src/resources/Main_window.jpg");
        backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
        JLabel background = new JLabel(backgroundImage);
        background.setBounds(0, 0, getWidth(), getHeight());
        add(background);

        // 设置顶部文字
        JLabel titleLabel = new JLabel("欢迎来到游戏");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(480, 200, 300, 50);
        background.add(titleLabel);

        // 设置开始游戏按钮
        JButton startButton = new JButton("开始游戏");
        startButton.setFont(new Font("微软雅黑", Font.BOLD, 24));
        startButton.setBounds(500, 400, 200, 50);
        startButton.addActionListener(_ -> {
            GameManager gameManager = new GameManager();
            GameWindow gameWindow = new GameWindow();
            gameWindow.setVisible(true);
            MainWindow.this.setVisible(false);
            gameManager.startGame();
        });
        background.add(startButton);

        // 设置制作人员名单按钮
        JButton creditsButton = new JButton("制作人员名单");
        creditsButton.setFont(new Font("微软雅黑", Font.BOLD, 24));
        creditsButton.setBounds(500, 500, 200, 50);
        creditsButton.addActionListener(_ -> {
            // 处理制作人员名单按钮点击事件
            JOptionPane.showMessageDialog(MainWindow.this, "制作人员名单：...");
        });
        background.add(creditsButton);

        // 设置窗口可见
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
