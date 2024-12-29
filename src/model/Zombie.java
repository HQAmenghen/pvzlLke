package model;

import game.GameManager;
import gui.GameWindow;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.Random;

public class Zombie {
    private static final game.GameManager GameManager = game.GameManager.getInstance();
    private int health; // 僵尸的生命值
    private final String imagePath; // 图片路径
    private int currentX; // 当前 X 坐标
    private int currentY; // 当前 Y 坐标
    public final Timer moveTimer; // 移动定时器
    private static Timer zombieTimer;
    private JLabel imageLabel; // 存储僵尸的图片标签
    public static final int damage = 20;
    private final JLabel healthLabel; // 显示血量的标签
    private String name; // 新增字段：僵尸名称
    private final GameWindow gameWindow; // 保存 GameWindow 实例的引用

    public static class Constants {
        public static final int ZOMBIE_HEALTH = 100; // 僵尸的生命值
    }

    public Zombie(GameWindow gameWindow) {
        this.gameWindow = gameWindow; // 初始化 GameWindow 实例的引用
        this.health = Constants.ZOMBIE_HEALTH;
        this.imagePath = "src/resources/place_zombie.png";
        this.currentX = 0; // 初始化 X 坐标
        this.currentY = 0; // 初始化 Y 坐标
        this.imageLabel = getImageLabel(); // 初始化图片标签
        this.healthLabel = new JLabel(String.valueOf(health)); // 初始化血量标签
        this.healthLabel.setForeground(Color.RED); // 设置血量标签颜色为红色
        this.healthLabel.setFont(new Font("Times New Roman", Font.BOLD, 18)); // 设置字体
        // 每隔5秒移动一次
        this.moveTimer = new Timer(5000, _ -> {
            // 启动一个新线程来处理移动逻辑
            new Thread(this::moveInSeparateThread).start();
        });
        this.moveTimer.start(); // 启动移动定时器
    }

    public Zombie(GameWindow gameWindow, String name) {
        this(gameWindow);
        this.name = name;
    }

public static void zombie_generate(GameWindow gameWindow) {
    // 创建一个定时器，每9秒触发一次事件
    zombieTimer = new Timer(9000, _ -> {
        // 获取最后一列的索引
        int lastColumnIndex = GameWindow.cards[0].length - 1;
        // 创建随机数生成器
        Random random = new Random();
        // 随机选择一行（0到4）
        int row = random.nextInt(5);

        // 创建一个新的僵尸实例
        Zombie zombie = new Zombie(gameWindow, "zombie");
        zombie.setName("zombie");
        game.GameManager.getInstance().addZombie(zombie, new game.GameManager.GridPosition(zombie.currentX,zombie.currentY));
        // 设置僵尸的初始位置为随机行的最后一列
        zombie.currentX = row;
        zombie.currentY = lastColumnIndex;

        // 创建一个新的面板用于显示僵尸图像和血量标签
        JPanel zombiePanel = new JPanel(new BorderLayout());
        zombiePanel.add(zombie.getImageLabel(), BorderLayout.CENTER); // 添加僵尸图像
        zombiePanel.add(zombie.getHealthLabel(), BorderLayout.NORTH); // 添加血量标签
        zombiePanel.setOpaque(false); // 设置面板背景透明
        // 将僵尸面板添加到游戏窗口的最后一列
        GameWindow.cards[row][lastColumnIndex].add(zombiePanel);
    });
    // 启动定时器
    zombieTimer.start();
}



    public void moveInSeparateThread() {
        // 计算新的位置
        int newX = currentX;
        int newY = currentY - 1; // 假设僵尸向左移动

        System.out.printf("僵尸位置"+currentX+":"+currentY);
        // 检查目标格子是否存在且没有植物
        if (newY >= 0) {
            if (!hasPlant(newX, newY)) {
                updatePosition(newX, newY);
            } else {
                GameManager.attack(newX, newY);
            }
        } else {
            this.moveTimer.stop();
            zombieTimer.stop(); // 确保 zombieTimer 不为空再停止
            GameManager.endGame(gameWindow.getFrame());
        }
    }


    private void updatePosition(int newX, int newY) {
        // 使用 SwingUtilities.invokeLater 来确保在 EDT 上更新 UI
        SwingUtilities.invokeLater(() -> {
            GameWindow.cards[currentX][currentY].removeAll(); // 移除旧位置的所有组件
            GameWindow.cards[currentX][currentY].revalidate(); // 刷新界面
            GameWindow.cards[currentX][currentY].repaint(); // 重绘界面
            currentX = newX;
            currentY = newY;

            // 创建一个 JPanel 并设置布局管理器为 BorderLayout
            JPanel zombiePanel = new JPanel(new BorderLayout());
            zombiePanel.add(getImageLabel(), BorderLayout.CENTER); // 添加图片标签到中心
            zombiePanel.add(getHealthLabel(), BorderLayout.NORTH); // 添加血量标签到顶部
            zombiePanel.setOpaque(false);

            // 将僵尸面板添加到新位置
            GameWindow.cards[currentX][currentY].add(zombiePanel);
            GameWindow.cards[currentX][currentY].revalidate(); // 刷新界面
            GameWindow.cards[currentX][currentY].repaint(); // 重绘界面
        });
    }

    private boolean hasPlant(int x, int y) {
        for (Component component : GameWindow.cards[x][y].getComponents()) {
            // 检查组件的名称是否为非空
            if (component.getName() != null && !component.getName().isEmpty()) {
                return true;
            }
        }
        return false; // 遍历完所有组件后仍未找到非空组件，返回 false
    }


    public int getHealth() {
        return health;
    }

    public void loseHealth(int damage) {
        health -= damage;
        updateHealthLabel(); // 更新血量标签
        if (health <= 0) {
            // 僵尸死亡的处理逻辑
            die();
        }
    }

    private void die() {
        // 僵尸死亡的处理逻辑
        System.out.println(name + " 死亡"); // 使用僵尸名称
        GameWindow.cards[currentX][currentY].removeAll(); // 移除所有组件
        GameWindow.cards[currentX][currentY].revalidate(); // 刷新界面
        GameWindow.cards[currentX][currentY].repaint(); // 重绘界面
        moveTimer.stop(); // 停止移动定时器
    }

    // 返回僵尸的图片标签
    public JLabel getImageLabel() {
        if (imageLabel == null) {
            ImageIcon icon = new ImageIcon(imagePath);
            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                Image image = icon.getImage().getScaledInstance(140, 130, Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(image));
            } else {
                imageLabel = new JLabel("图片加载失败"); // 处理图片加载失败的情况
            }
        }
        return imageLabel;
    }

    // 返回血量标签
    public JLabel getHealthLabel() {
        return healthLabel;
    }

    // 更新血量标签
    private void updateHealthLabel() {
        healthLabel.setText(String.valueOf(health));
    }

    // 新增方法：获取僵尸名称
    public String getName() {
        return name;
    }

    // 新增方法：设置僵尸名称
    public void setName(String name) {
        this.name = name;
    }
    public Timer getMoveTimer() {
        return moveTimer;
    }
}
