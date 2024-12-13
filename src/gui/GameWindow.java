package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class GameWindow extends JFrame {
    private final JPanel[][] cells = new JPanel[5][9]; // 用于存储每个格子
    private final JPanel[][] cards = new JPanel[5][9]; // 用于存储每个格子中的卡牌
    private static final int CELL_WIDTH = 140; // 格子宽度
    private static final int CELL_HEIGHT = 130; // 格子高度
    private final JPanel gameAreaPanel; // 提升为类的成员变量

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
        gameAreaPanel = new JPanel();
        gameAreaPanel.setLayout(new GridLayout(5, 9));
        gameAreaPanel.setPreferredSize(new Dimension(1260, 650)); // 设置首选大小
        gameAreaPanel.setBorder(new LineBorder(Color.BLACK, 1)); // 设置边框样式

        // 创建一个二维数组来存储单元格
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                JPanel cell = new JPanel();
                cell.setBackground(Color.WHITE); // 设置背景颜色以便观察
                cell.setBorder(new LineBorder(Color.BLACK, 1)); // 设置边框样式

                // 根据索引的奇偶性选择不同的图片
                ImageIcon icon;
                if ((i + j) % 2 == 0) {
                    icon = new ImageIcon("src/resources/tile_0084.png"); // 第一种图片路径
                } else {
                    icon = new ImageIcon("src/resources/tile_0092.png"); // 第二种图片路径
                }
                JLabel label = new JLabel(icon);
                cell.add(label);
                gameAreaPanel.add(cell);

                // 将单元格添加到二维数组中
                cells[i][j] = cell;

                //初始化cards
                cards[i][j] = new JPanel();
                cards[i][j].setLayout(new BorderLayout());

            }
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
            JPanel verticalPanel = getjPanel(scaledImage);

            // 将垂直面板添加到 topSlotPanel
            topSlotPanel.add(verticalPanel);
        } else {
            JLabel sunLabel = new JLabel("无法加载图像");
            topSlotPanel.add(sunLabel);
        }

        // 添加卡片到顶部卡槽框
        addCard(topSlotPanel, "/resources/place_sunflower.png", "50", mainContainer);
        addCard(topSlotPanel, "/resources/peashooter.jpg", "100", mainContainer);
        addCard(topSlotPanel, "/resources/walnut.jpg", "75", mainContainer);

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

    private static JPanel getjPanel(Image scaledImage) {
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
        return verticalPanel;
    }

    // 修改 addCard 方法中的调用
    private void addCard(JPanel panel, String imagePath, String costText, JPanel mainContainer) {
        // 加载图像
        ImageIcon cardIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        Image scaledImage = cardIcon.getImage().getScaledInstance(100, 130, Image.SCALE_SMOOTH); // 将图片调整为100x120像素
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

                // 调用 placeCard 方法
                placeCard(imagePath, mainContainer, newX, newY);

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

private void placeCard(String imagePath, JPanel mainContainer, int x, int y) {
   // 获取 gameAreaPanel 在 mainContainer 中的位置
Point gameAreaLocation = gameAreaPanel.getLocationOnScreen();
Point mainContainerLocation = mainContainer.getLocationOnScreen();

// 将鼠标释放位置转换为 gameAreaPanel 的坐标系
int gameAreaX = x - (gameAreaLocation.x - mainContainerLocation.x);
int gameAreaY = y - (gameAreaLocation.y - mainContainerLocation.y);

// 计算释放位置对应的格子坐标
int col = gameAreaX / CELL_WIDTH;
int row = (gameAreaY+120) / CELL_HEIGHT;
//
//// 打印调试信息
//System.out.println("鼠标释放位置在屏幕上的坐标: (" + x + ", " + y + ")");
//System.out.println("gameAreaPanel 在屏幕上的位置: " + gameAreaLocation);
//System.out.println("mainContainer 在屏幕上的位置: " + mainContainerLocation);
//System.out.println("计算出的 gameAreaPanel 相对位置: (" + gameAreaX + ", " + gameAreaY + ")");
//System.out.println("计算出的格子位置: (" + col + ", " + row + ")");


    // 检查是否在有效范围内
    if (row >= 0 && row < 5 && col >= 0 && col < 9) {
        // 获取目标格子
        JPanel targetCell = cards[row][col];

        // 检查目标格子是否已经有卡片
        if (targetCell.getComponentCount() > 0) {
            targetCell.removeAll();
        }

        // 创建卡片容器
        JPanel cardContainer = new JPanel();
        cardContainer.setLayout(new BorderLayout());


        // 加载并设置卡片图片
        ImageIcon cardIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        Image scaledImage = cardIcon.getImage().getScaledInstance(CELL_WIDTH, CELL_HEIGHT, Image.SCALE_SMOOTH); // 将图片调整为格子大小
        ImageIcon scaledCardIcon = new ImageIcon(scaledImage);

        JLabel cardLabel = new JLabel(scaledCardIcon);
        cardContainer.add(cardLabel, BorderLayout.CENTER);

        // 将卡片添加到目标格子
        targetCell.add(cardContainer);

        // 更新二维数组中的卡牌引用
        cards[row][col] = cardContainer;

        // 重新验证和绘制目标格子，以反映新的布局变化
        targetCell.validate();
        targetCell.repaint();
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
