package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class GameWindow extends JFrame {

    public GameWindow() {
        // 标题
        setTitle("游戏");
        // 大小
        setSize(1260, 800);
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

        addCard(topSlotPanel, "/resources/sunflower.jpg", "50");
        addCard(topSlotPanel, "/resources/peashooter.jpg", "100");
        addCard(topSlotPanel, "/resources/walnut.jpg", "75");



        // 创建游戏区域
        JPanel gameAreaPanel = new JPanel();
        gameAreaPanel.setLayout(new GridLayout(5, 9));
        for (int i = 0; i < 5 * 9; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(Color.WHITE); // 设置背景颜色以便观察
            cell.setBorder(new LineBorder(Color.BLACK, 1)); // 设置边框样式
            gameAreaPanel.add(cell);
        }

        // 设置窗口可见
        setVisible(true);
        add(topSlotPanel, BorderLayout.NORTH);
        add(gameAreaPanel, BorderLayout.CENTER);


    }

    private void addCard(JPanel panel, String imagePath, String costText) {
        // 加载图像
        ImageIcon cardIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        Image scaledImage = cardIcon.getImage().getScaledInstance(100, 120, Image.SCALE_SMOOTH); // 将图片调整为100x120像素
        ImageIcon scaledCardIcon = new ImageIcon(scaledImage);

        // 创建 JLabel 并添加到面板
        JLabel cardLabel = new JLabel(scaledCardIcon);

        // 创建费用 JLabel 并添加到面板
        JLabel costLabel = new JLabel(costText);
        costLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置文字居中对齐

        // 创建一个垂直的 JPanel 来放置 cardLabel 和 costLabel
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // 确保垂直面板左对齐

        // 添加卡牌图像
        verticalPanel.add(cardLabel);

        // 添加费用文字
        verticalPanel.add(costLabel);

        // 创建一个新的 JPanel 来作为卡片的外部容器
        JPanel cardContainer = new JPanel();
        cardContainer.setLayout(new OverlayLayout(cardContainer));
        cardContainer.add(verticalPanel);

        // 定义鼠标按下时的位置变量
        final int[] x = {0};
        final int[] y = {0};

        // 添加鼠标监听器
        cardContainer.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                x[0] = e.getX();
                y[0] = e.getY();
            }
        });

        // 添加鼠标移动监听器
        cardContainer.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                // 获取组件原本位置
                int a = cardContainer.getLocation().x;
                int b = cardContainer.getLocation().y;
                // 计算新的位置并设置
                cardContainer.setLocation(a + e.getX() - x[0], b + e.getY() - y[0]);
            }
        });

        // 将外部容器添加到主面板
        panel.add(cardContainer);
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow());
    }
}
