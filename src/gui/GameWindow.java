package gui;

import game.GameManager;
import model.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;


public class GameWindow extends JFrame {
    public static JPanel[][] cards = new JPanel[5][9]; // 用于存储每个格子中的卡牌
    private static final int CELL_WIDTH = 140; // 格子宽度
    private static final int CELL_HEIGHT = 130; // 格子高度
    private final JPanel gameAreaPanel; // 提升为类的成员变量
    private  JLabel healthLabel;
    private final game.GameManager GameManager;


    public GameWindow(GameManager gameManager) {
        this.GameManager = gameManager;
        // 标题
        setTitle("游戏");
        // 大小
        setSize(1260, 850);
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
        gameAreaPanel.setLayout(new BorderLayout()); // 使用 BorderLayout 布局
        gameAreaPanel.setPreferredSize(new Dimension(1260, 650)); // 设置首选大小
        gameAreaPanel.setBorder(new LineBorder(Color.BLACK, 1)); // 设置边框样式

        // 创建一个 JLayeredPane 来包含 cells 和 cards
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1260, 650)); // 设置与 gameAreaPanel 相同的首选大小

        // 创建一个二维数组来存储单元格
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                JPanel cell = new JPanel();
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

                // 初始化 cards
                cards[i][j] = new JPanel();
                cards[i][j].setLayout(new BorderLayout());
                cards[i][j].setOpaque(false); // 设置为不透明以允许背景显示

                // 将 cell 和 cards 添加到 JLayeredPane 中
                layeredPane.add(cell, JLayeredPane.DEFAULT_LAYER);
                layeredPane.add(cards[i][j], JLayeredPane.PALETTE_LAYER);

                // 设置 cell 和 cards 的位置和大小
                cell.setBounds(j * CELL_WIDTH, i * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
                cards[i][j].setBounds(j * CELL_WIDTH, i * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
            }
        }

        // 将 JLayeredPane 添加到 gameAreaPanel 中
        gameAreaPanel.add(layeredPane, BorderLayout.CENTER);

        // 创建顶部卡槽框
        JPanel topSlotPanel = new JPanel(); // 初始化 topSlotPanel
        topSlotPanel.setBackground(Color.GRAY); // 设置背景颜色以便观察
        topSlotPanel.setPreferredSize(new Dimension(1200, 150)); // 设置首选大小
        topSlotPanel.setBorder(new LineBorder(Color.BLACK, 1)); // 设置边框样式
        topSlotPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5)); // 使用 FlowLayout 左对齐，并设置组件之间的间距

        // 添加阳光图标和数量数字
        ImageIcon sunIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/sun.png")));
        if (sunIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            // 调整图片大小
            Image scaledImage = sunIcon.getImage().getScaledInstance(100, 120, Image.SCALE_SMOOTH); // 将图片调整为100x100像素

            // 创建一个垂直的 JPanel 来放置 sunLabel 和 sunCountLabel
            JPanel verticalPanel = new JPanel();
            verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
            verticalPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // 确保垂直面板左对齐

            // 创建阳光图标标签
            ImageIcon scaledSunIcon = new ImageIcon(scaledImage);
            JLabel sunLabel = new JLabel(scaledSunIcon);
            verticalPanel.add(sunLabel);

            // 创建阳光数量数字标签
            JLabel sunCountLabel = new JLabel(String.valueOf(gameManager.getSunPoints())); // 使用GameManager中的阳光数量
            verticalPanel.add(sunCountLabel);

            // 将sunCountLabel设置为GameManager的回调目标
            gameManager.setSunCountLabel(sunCountLabel);

            // 将垂直面板添加到 topSlotPanel
            topSlotPanel.add(verticalPanel);
        } else {
            JLabel sunLabel = new JLabel("无法加载图像");
            topSlotPanel.add(sunLabel);
        }

        // 创建一个 Sunflower 实例
        Plant sunflower = new Sunflower();
        addCard(topSlotPanel, sunflower, "50", mainContainer);

        // 创建一个 Peashooter 实例
        Plant peashooter = new model.Peashooter();
        addCard(topSlotPanel, peashooter, "100", mainContainer);

        // 创建一个 WallNut 实例
        Plant walnut = new WalNut();
        addCard(topSlotPanel, walnut, "75", mainContainer);

        // 将 topSlotPanel 添加到 mainContainer
        mainContainer.add(topSlotPanel, BorderLayout.NORTH);

        // 将 gameAreaPanel 添加到 mainContainer
        mainContainer.add(gameAreaPanel, BorderLayout.CENTER);

        mainContainer.setComponentZOrder(gameAreaPanel, 1);
        mainContainer.setComponentZOrder(topSlotPanel, 0);

        // 将 mainContainer 添加到 GameWindow
        add(mainContainer, BorderLayout.CENTER);

        updateGameState();

        Zombie.zombie_generate(this);

        // 设置窗口可见
        setVisible(true);
    }

    private void addCard(JPanel slotPanel, Plant plant, String cost, JPanel mainContainer) {
        // 创建卡片容器
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setOpaque(false);

        // 设置边框
        card.setBorder(new LineBorder(Color.BLACK, 2)); // 黑色边框，宽度为2像素

        // 加载图像
        ImageIcon cardIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(plant.getImagePath())));
        Image scaledImage = cardIcon.getImage().getScaledInstance(100, 120, Image.SCALE_SMOOTH); // 将图片调整为100x120像素
        ImageIcon scaledCardIcon = new ImageIcon(scaledImage);

        // 创建 JLabel 并添加到面板
        JLabel cardLabel = new JLabel(scaledCardIcon);

        // 创建费用 JLabel 并添加到面板
        JLabel costLabel = new JLabel(cost);
        costLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置文字居中对齐

        // 创建一个垂直的 JPanel 来放置 cardLabel 和 costLabel
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        verticalPanel.add(cardLabel);
        verticalPanel.add(costLabel);

        // 将 verticalPanel 添加到 card 容器
        card.add(verticalPanel, BorderLayout.CENTER);

        // 使卡片可拖动
        makeDraggable(slotPanel, card, plant, mainContainer);

        // 将卡片添加到顶部卡槽框
        slotPanel.add(card);

    }


    private void makeDraggable(JPanel topSlotPanel, JPanel card, Plant plant, JPanel mainContainer) {
        // 定义鼠标按下时的位置变量
        final int[] x = {0};
        final int[] y = {0};
        final Point originalLocation = new Point();

        // 添加鼠标监听器
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x[0] = e.getX();
                y[0] = e.getY();
                originalLocation.setLocation(card.getX(), card.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {


                // 获取当前卡片在屏幕上的位置
                Point cardLocation = card.getLocationOnScreen();

                // 获取主容器在屏幕上的位置
                Point panelLocation = mainContainer.getLocationOnScreen();

                int newX = cardLocation.x - panelLocation.x + e.getX() - x[0];
                int newY = cardLocation.y - panelLocation.y + e.getY() - y[0];

                topSlotPanel.add(card);
                // 调用 placeCard 方法并传递 Plant 对象
                String plantName = plant.getName();
                placeCard(plantName, mainContainer, newX, newY);


                // 重置卡片的位置为顶部卡槽的位置
                card.setLocation(originalLocation);

                // 重新验证和绘制主容器，以反映新的布局变化
                mainContainer.repaint();


            }
        });


        // 添加鼠标移动监听器
        card.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int a = card.getX();
                int b = card.getY();
                card.setLocation(a + e.getX() - x[0], b + e.getY() - y[0]);
                mainContainer.setComponentZOrder(card, 0); // 确保副本卡片在最前面
                mainContainer.repaint();

            }
        });
    }


   public void placeCard(String plantType, JPanel mainContainer, int x, int y) {
    Plant plant;

switch (plantType) {
    case "Sunflower":
        plant = new Sunflower();
        break;
    case "Peashooter":
        plant = new Peashooter();
        break;
    case "WalNut":
        plant = new WalNut();
        break;
    default:
        return;
}


    // 获取 gameAreaPanel 在 mainContainer 中的位置
    Point gameAreaLocation = gameAreaPanel.getLocationOnScreen();
    Point mainContainerLocation = mainContainer.getLocationOnScreen();

    // 将鼠标释放位置转换为 gameAreaPanel 的坐标系
    int gameAreaX = x - (gameAreaLocation.x - mainContainerLocation.x);
    int gameAreaY = y - (gameAreaLocation.y - mainContainerLocation.y);

    // 计算释放位置对应的格子坐标
    int col = (gameAreaX + 50) / CELL_WIDTH;
    int row = (gameAreaY + 120) / CELL_HEIGHT;

    // 检查是否在有效范围内
    if (row >= 0 && row < 5 && col >= 0 && col < 9) {
        // 获取目标格子
        JPanel targetCell = cards[row][col];

        // 检查目标格子是否已经有卡片
        if (targetCell.getComponentCount() > 0) {
            return;
        }

        // 获取植物的成本
        int cost = plant.getCost();

        // 检查阳光数量是否足够
        if (GameManager.getSunPoints() < cost) {
            // 阳光数量不足，可以选择性地向用户显示一条消息
            JOptionPane.showMessageDialog(this, "阳光不足，无法种植 " + plant.getClass().getSimpleName(), "阳光不足", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 打印传入的植物类的简单名称
        System.out.println("放置的是" + plant.getClass().getSimpleName());

        // 扣除阳光数量
        GameManager.deductSunPoints(cost);

        // 加载并设置卡片图片
        String imagePath = plant.getImagePath(); // 提取图片路径
        ImageIcon cardIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
        Image scaledImage = cardIcon.getImage().getScaledInstance(CELL_WIDTH, CELL_HEIGHT, Image.SCALE_SMOOTH); // 将图片调整为格子大小
        ImageIcon scaledCardIcon = new ImageIcon(scaledImage);

        // 创建一个新的 JLabel 来显示图标
        JLabel label = new JLabel(scaledCardIcon);
        label.setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
        label.setOpaque(false); // 设置 JLabel 透明

        // 创建一个新的 JLabel 来显示血量
        healthLabel = new JLabel(String.valueOf(plant.getHealth())); // 假设 getHealth() 返回血量值
        healthLabel.setForeground(Color.GREEN); // 设置血量标签颜色为绿色
        healthLabel.setFont(new Font("Times New Roman", Font.BOLD, 18)); // 设置字体
        healthLabel.setOpaque(false); // 设置血量标签透明

        // 创建一个 JPanel 并设置布局管理器为 BorderLayout
        JPanel plantPanel = new JPanel(new BorderLayout());
        plantPanel.setOpaque(false); // 设置 JPanel 透明
        plantPanel.add(label, BorderLayout.CENTER); // 添加植物图片标签到中心
        plantPanel.add(healthLabel, BorderLayout.NORTH); // 添加血量标签到顶部

        // 给每个 plantPanel 唯一命名
        plantPanel.setName("Plant_" + row + "_" + col);

        // 清除目标格子中的所有组件
        targetCell.removeAll();

        // 将 JPanel 添加到目标格子
        targetCell.add(plantPanel);
        targetCell.repaint(); // 重绘界面

        // 更新二维数组中的卡牌引用
        cards[row][col] = targetCell;

        // 设置植物的坐标
        plant.setY(col);
        plant.setX(row);

        game.GameManager.GridPosition position = new GameManager.GridPosition(row, col);
        GameManager.addPlant(plant, position);
        System.out.println("放置的植物坐标为：" + position);
    }
}


    private void updateGameState() {
        // 更新植物的状态
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                JPanel cell = cards[i][j];
                Plant plant = (Plant) cell.getClientProperty("plant");
                if (plant != null) {
                    GameManager.scheduleUpdate();
                }
            }
        }

    }


public JLabel getHealthLabel() {
        return healthLabel;
}


public  void updatePlantHealth(JLabel healthLabel, Plant plant) {
    // 更新植物的生命值标签
    healthLabel.setText(String.valueOf(plant.getHealth()));
    System.out.println("更新后的血量: " + plant.getHealth());
    healthLabel.repaint();
}
    public JFrame getFrame() {
        return this;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameManager gameManager = new GameManager(); // 创建GameManager实例
            new GameWindow(gameManager); // 将GameManager实例传递给GameWindow构造函数
            gameManager.startGame();
        });
    }


}

