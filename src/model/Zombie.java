package model;

import game.GameManager;
import gui.GameWindow;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Zombie {
    private int health; // 僵尸的生命值
    private final String imagePath; // 图片路径
    private int currentX; // 当前 X 坐标
    private int currentY; // 当前 Y 坐标
    private final Timer moveTimer; // 移动定时器
    private JLabel imageLabel; // 存储僵尸的图片标签
    private final int damage;
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
        this.damage = 20;
        this.healthLabel = new JLabel(String.valueOf(health)); // 初始化血量标签
        this.healthLabel.setForeground(Color.RED); // 设置血量标签颜色为红色
        this.healthLabel.setFont(new Font("Times New Roman", Font.BOLD, 18)); // 设置字体

        this.moveTimer = new Timer(3000, new ActionListener() { // 每隔3秒移动一次
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
            }
        });
        this.moveTimer.start(); // 启动移动定时器
    }

    // 新增构造函数，允许设置名称
    public Zombie(GameWindow gameWindow, String name) {
        this(gameWindow);
        this.name = name;
    }

    public static void zombie_generate(GameWindow gameWindow) {
        // 初始化定时器，每5000毫秒（5秒）执行一次
            Timer timer = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 获取最后一列的索引
                    int lastColumnIndex = GameWindow.cards[0].length - 1;

                    // 随机选择一行
                    Random random = new Random();
                    int row = random.nextInt(5); // 假设只有前5行有格子

                    // 创建一个新的僵尸对象，并设置其位置为最后一列
                    Zombie zombie = new Zombie(gameWindow, "zombie"); // 直接设置名称为 "zombie"
                    zombie.currentX = row; // 设置初始 X 坐标
                    zombie.currentY = lastColumnIndex; // 设置初始 Y 坐标

                    // 创建一个 JPanel 并设置布局管理器为 BorderLayout
                    JPanel zombiePanel = new JPanel(new BorderLayout());
                    zombiePanel.add(zombie.getImageLabel(), BorderLayout.CENTER); // 添加图片标签到中心
                    zombiePanel.add(zombie.getHealthLabel(), BorderLayout.NORTH); // 添加血量标签到顶部
                    zombiePanel.setOpaque(false);

                    // 将僵尸面板添加到对应的 JPanel 中
                    GameWindow.cards[row][lastColumnIndex].add(zombiePanel);

                }
            });
            timer.start(); // 启动定时器
        }

    private void move() {
        // 计算新的位置
        int newX = currentX;
        int newY = currentY - 1; // 假设僵尸向左移动

        // 检查目标格子是否存在且没有植物
        if (newY >= 0 && !hasPlant(newX, newY)) {
            // 更新僵尸的位置
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
        } else {
            // 如果目标格子有植物或不存在，则停止移动并攻击
            attack(newX, newY);
        }
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

    private void attack(int x, int y) {
        // 攻击逻辑
        // 获取所有植物
        java.util.List<Plant> plants = GameManager.getPlants();
        for (Plant plant : plants) {
            if (plant.getX() == x && plant.getY() == y) {
                plant.loseHealth(damage);
                System.out.println(name + " 攻击: " + plant.getName());
                gameWindow.updatePlantHealth(plant.getHealthLabel(), plant); // 使用保存的 GameWindow 实例
                break;
            }
            else {
                System.out.println("未有");
            }
        }
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
}
