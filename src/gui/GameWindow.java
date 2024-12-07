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

        // 创建顶层容器
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setOpaque(false); // 设置为透明

        // 创建游戏区域
        JPanel gameAreaPanel = new JPanel();
        gameAreaPanel.setLayout(new GridLayout(5, 9));
        gameAreaPanel.setPreferredSize(new Dimension(1200, 650)); // 设置首选大小
        gameAreaPanel.setBorder(new LineBorder(Color.BLACK, 1)); // 设置边框样式

        for (int i = 0; i < 5 * 9; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(Color.WHITE); // 设置背景颜色以便观察
            cell.setBorder(new LineBorder(Color.BLACK, 1)); // 设置边框样式
            gameAreaPanel.add(cell);
        }

        // 创建顶部卡槽框
        // 将 topSlotPanel 提升为类的成员变量
        JPanel topSlotPanel = new JPanel(); // 初始化 topSlotPanel
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

        // 添加卡片到顶部卡槽框
        addCard(topSlotPanel, "/resources/sunflower.jpg", "50",  mainContainer);
        addCard(topSlotPanel, "/resources/peashooter.jpg", "100",  mainContainer);
        addCard(topSlotPanel, "/resources/walnut.jpg", "75",  mainContainer);

        // 将 topSlotPanel 添加到 mainContainer
        mainContainer.add(topSlotPanel, BorderLayout.NORTH);

        // 将 mainContainer 添加到 GameWindow
        add(mainContainer, BorderLayout.CENTER);

        // 将 gameAreaPanel 添加到 mainContainer
        mainContainer.add(gameAreaPanel, BorderLayout.CENTER);

        mainContainer.setComponentZOrder(gameAreaPanel, 1);
        mainContainer.setComponentZOrder(topSlotPanel, 0);

        // 设置窗口可见
        setVisible(true);
    }

    // 修改 addCard 方法中的调用
    private void addCard(JPanel panel, String imagePath, String costText, JPanel mainContainer) {
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
        JPanel verticalPanel = createVerticalPanel(cardLabel, costLabel);

        // 使卡片可拖动
        makeDraggable(panel, verticalPanel, imagePath, mainContainer);
    }

    private JPanel createVerticalPanel(JLabel cardLabel, JLabel costLabel) {
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        verticalPanel.add(cardLabel);
        verticalPanel.add(costLabel);
        return verticalPanel;
    }

   private void makeDraggable(JPanel panel, JPanel verticalPanel, String imagePath, JPanel mainContainer) {
    // 定义鼠标按下时的位置变量
    final int[] x = {0};
    final int[] y = {0};
    final Point originalLocation = new Point();
    ImageIcon cardIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
    Image scaledImage = cardIcon.getImage().getScaledInstance(100, 120, Image.SCALE_SMOOTH); // 将图片调整为100x120像素
    ImageIcon scaledCardIcon = new ImageIcon(scaledImage);

    // 创建一个新的 JPanel 来作为卡片的外部容器
    JPanel cardContainer = new JPanel();
    cardContainer.setLayout(new BorderLayout());
    cardContainer.add(verticalPanel, BorderLayout.CENTER);


    // 添加鼠标监听器
    cardContainer.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            x[0] = e.getX();
            y[0] = e.getY();
            // 保存卡片的原始位置
            originalLocation.setLocation(cardContainer.getLocation());

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // 创建新的卡片容器，参数包括缩放后的卡片图标和垂直面板中的某个组件的文本
            JPanel newCardContainer = createCardContainer(scaledCardIcon, ((JLabel) verticalPanel.getComponent(1)).getText());

            // 获取当前卡片容器在屏幕上的位置
            Point cardLocation = cardContainer.getLocationOnScreen();

            // 获取主容器在屏幕上的位置
            Point panelLocation = mainContainer.getLocationOnScreen();

            // 计算新卡片在主容器中的位置
            int newX = cardLocation.x - panelLocation.x + e.getX() - x[0];
            int newY = cardLocation.y - panelLocation.y + e.getY() - y[0];

            // 将卡片移动回原始位置
            cardContainer.setLocation(originalLocation);

            // 将新卡片添加到主容器中
            mainContainer.add(newCardContainer);

            // 重新验证和绘制主容器，以反映新的布局变化
            mainContainer.repaint();
        }
    });

    // 添加鼠标移动监听器
    cardContainer.addMouseMotionListener(new MouseAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            int a = cardContainer.getLocation().x;
            int b = cardContainer.getLocation().y;
            cardContainer.setLocation(a + e.getX() - x[0], b + e.getY() - y[0]);
            mainContainer.setComponentZOrder(cardContainer, 0); // 确保卡片在最前面
        }
    });

    // 将外部容器添加到主面板
    panel.add(cardContainer);
}


    private JPanel createCardContainer(ImageIcon scaledCardIcon, String costText) {
        JPanel newCardContainer = new JPanel();
        newCardContainer.setLayout(new BorderLayout());
        newCardContainer.setOpaque(false);

        JPanel newVerticalPanel = createVerticalPanel(new JLabel(scaledCardIcon), new JLabel(costText));
        newCardContainer.add(newVerticalPanel, BorderLayout.CENTER);
        return newCardContainer;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
