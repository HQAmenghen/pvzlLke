package model;

import game.GameManager;
import gui.GameWindow;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.Map;
import java.util.Random;

public class Zombie {
    private final GameManager gameManager;
    private int health; // 僵尸的生命值
    private final String imagePath; // 图片路径
    private int currentX; // 当前 X 坐标
    private int currentY; // 当前 Y 坐标
    public final Timer moveTimer; // 移动定时器
    private static Timer zombieTimer;
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
        this.gameManager = new GameManager();
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
        zombieTimer = new Timer(9000, _ -> {
            int lastColumnIndex = GameWindow.cards[0].length - 1;
            Random random = new Random();
            int row = random.nextInt(5);

            Zombie zombie = new Zombie(gameWindow, "zombie");
            zombie.currentX = row;
            zombie.currentY = lastColumnIndex;

            JPanel zombiePanel = new JPanel(new BorderLayout());
            zombiePanel.add(zombie.getImageLabel(), BorderLayout.CENTER);
            zombiePanel.add(zombie.getHealthLabel(), BorderLayout.NORTH);
            zombiePanel.setOpaque(false);
            GameWindow.cards[row][lastColumnIndex].add(zombiePanel);
        });
        zombieTimer.start();
    }


    private void moveInSeparateThread() {
        // 计算新的位置
        int newX = currentX;
        int newY = currentY - 1; // 假设僵尸向左移动

        // 检查目标格子是否存在且没有植物
        if (newY >= 0) {
            if (!hasPlant(newX, newY)) {
                updatePosition(newX, newY);
            } else {
                attack(newX, newY);
            }
        } else {
            this.moveTimer.stop();
            zombieTimer.stop(); // 确保 zombieTimer 不为空再停止
            gameManager.endGame(gameWindow.getFrame());
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


private void attack(int x, int y) {
    // 使用 GameManage 的 GridPosition 方法将 x, y 转换为 position 类型
    GameManager.GridPosition position = new GameManager.GridPosition(x, y);

    Map<GameManager.GridPosition, Plant> plants = GameManager.plants; // 获取植物地图

    // 通过 position 作为键读取 plants 中的植物实例
    Plant targetPlant = plants.get(position);
    System.out.println("传入的坐标  " + x + "  " + y);
    System.out.println("转换的 " + targetPlant);

    if (targetPlant != null) {
        System.out.println("植物名称: " + targetPlant.getName() + ", 位置: (" + targetPlant.getX() + ", " + targetPlant.getY() + ")");

        while (targetPlant.getHealth() > 0) {
            targetPlant.loseHealth(damage); // 确保 damage 已经定义
            System.out.println(name + " 攻击: " + targetPlant.getName());

            // 确保在 EDT 上更新 UI
            SwingUtilities.invokeLater(() -> {
                gameWindow.updatePlantHealth(gameWindow.getHealthLabel(), targetPlant);
            });

            // 检查植物是否被摧毁
            if (targetPlant.getHealth() <= 0) {
                // 植物死亡的处理逻辑
                System.out.println(getName() + " 死亡");
                GameWindow.cards[x][y].removeAll();
                GameWindow.cards[x][y].revalidate();
                GameWindow.cards[x][y].repaint();
                // 从 plants 映射中删除被摧毁的植物
                plants.remove(position);
                break;
            }

            // 可选：添加延迟以模拟攻击间隔（例如每秒攻击一次）
            try {
                Thread.sleep(1000); // 休眠1秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("攻击线程被中断");
            }
        }
    } else {
        System.out.println("没有找到目标植物");
    }
}





private void stopAttack(Thread thread) {
    thread.interrupt(); // 中断线程
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
