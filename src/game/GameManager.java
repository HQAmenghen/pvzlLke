package game;

import gui.GameWindow;
import gui.MainWindow;
import model.Plant;
import model.Sunflower;
import model.Zombie;

import java.util.*;
import java.util.Timer;
import javax.swing.*;

import javax.swing.JOptionPane;

public class GameManager {
    private static int sunPoints;
    private Timer timer;
    private JLabel sunCountLabel; // 引用sunCountLabel
    public  Map<GridPosition, Plant> plants; // 使用Map来存储所有的植物实例，键为植物位置
    public  Map<GridPosition,Zombie> zombies; // 使用Map来存储所有的僵尸实例，键为僵尸位置
    private Zombie currentZombie; // 添加对当前僵尸的引用
    private Timer sunIncreaseTimer; // 声明 sunIncreaseTimer 变量


    // 私有静态实例
    private static volatile GameManager instance;

    // 私有化构造函数
    public GameManager() {
        plants = new HashMap<>();
        zombies = new HashMap<>(); // 初始化 zombies

    }

    // 提供公共的静态方法来获取实例
    public static GameManager getInstance() {
        if (instance == null) {
            synchronized (GameManager.class) {
                if (instance == null) {
                    instance = new GameManager();
                }
            }
        }
        return instance;
    }

public void resetGame() {
    sunPoints = 200; // 初始阳光点数
    removePlants();
    this.timer = new Timer();
    plants = new HashMap<>(); // 初始化植物Map
    if (sunCountLabel != null) {
        sunCountLabel.setText(String.valueOf(sunPoints)); // 更新阳光数量显示
    }
}


    public void setSunCountLabel(JLabel sunCountLabel) {
        this.sunCountLabel = sunCountLabel;
    }

    public void startGame() {
        // 确保在开始游戏前重置游戏状态
        resetGame();
        scheduleSunIncrease(); // 开始定时增加阳光
        scheduleUpdate(); // 开始定时更新
    }

    public void endGame(JFrame currentFrame) {
        removePlants();

        if (currentZombie != null && currentZombie.getMoveTimer() != null) {
            currentZombie.getMoveTimer().stop(); // 使用 stop() 方法停止定时器
        }
        stopSunIncrease();


        int result = JOptionPane.showConfirmDialog(null, "游戏结束！是否返回主窗口？", "游戏结束", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            currentFrame.dispose(); // 关闭当前窗口
            MainWindow mainWindow = new MainWindow(); // 创建并显示主窗口
            mainWindow.setVisible(true);
        }
    }

private void removePlants() {
    for (int i = 0; i < GameWindow.cards.length; i++) {
        for (int j = 0; j < GameWindow.cards[i].length; j++) {
            GameWindow.cards[i][j].removeAll();
            GameWindow.cards[i][j].revalidate();
            GameWindow.cards[i][j].repaint();
            plants.clear(); // 使用 clear() 方法清空 plants 映射
        }
    }
    if (zombies != null) {
        zombies.clear();
    }
}


    private void scheduleSunIncrease() {
        sunIncreaseTimer = new Timer();
        sunIncreaseTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                sunPoints += 25; // 每隔5秒增加25点阳光
                SwingUtilities.invokeLater(() -> {
                    if (sunCountLabel != null) {
                        sunCountLabel.setText(String.valueOf(sunPoints)); // 更新GameWindow中的阳光数量
                        sunCountLabel.validate();
                        sunCountLabel.repaint(); // 只重新绘制
                    }
                });
            }
        }, 0, 5000);
    }

    public void stopSunIncrease() {
        if (sunIncreaseTimer != null) {
            sunIncreaseTimer.cancel();
            sunIncreaseTimer = null; // 可选：将引用置为null，避免内存泄漏
        }
    }

    public int getSunPoints() {
        return sunPoints;
    }

    public void addSunPoints(int amount) {
        sunPoints += amount;
    }

    public void deductSunPoints(int points) {
        if (points <= sunPoints) {
            sunPoints -= points;
        } else {
            // 处理阳光不足的情况
            System.out.println("阳光不足，无法种植植物");
        }
        // 更新阳光数量显示
        updateSunCountLabel();
    }

    private void updateSunCountLabel() {
        if (sunCountLabel != null) {
            sunCountLabel.setText(String.valueOf(sunPoints)); // 更新GameWindow中的阳光数量
            sunCountLabel.validate();
            sunCountLabel.repaint();
        }
    }

    public void scheduleUpdate() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                update(); // 每秒调用一次update方法
            }
        };
        timer.scheduleAtFixedRate(task, 3000, 3000); // 每隔3秒执行一次
    }

    public void update() {
        // 更新游戏状态
        for (Plant plant : plants.values()) {
            if (plant instanceof Sunflower) {
                ((Sunflower) plant).update();
            }
        }
        updateSunCountLabel();
    }

    public static class GridPosition {
        private final int row;
        private final int col;

        public GridPosition(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GridPosition that = (GridPosition) o;
            return row == that.row && col == that.col;
        }

        public int hashCode() {
            return Objects.hash(row, col);
        }

        public String toString() {
            return "GridPosition{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }

    }

    public void addPlant(Plant plant, GridPosition position) {
        plants.put(position, plant);
        System.out.println("添加植物: " + plant.getClass().getSimpleName());
    }

    public void addZombie(Zombie zombie, GridPosition position) {
        zombies.put(position, zombie);
    }

// 在 GameManager 类中
public void attack(int x, int y) {
    // 使用 GameManage 的 GridPosition 方法将 x, y 转换为 position 类型
    GameManager.GridPosition position = new GameManager.GridPosition(x, y);

    // 通过 position 作为键读取 plants 中的植物实例
    Plant targetPlant = plants.get(position);
    System.out.println("传入的坐标  " + x + "  " + y);
    System.out.println("转换的 " + targetPlant);

    if (targetPlant != null) {
        System.out.println("植物名称: " + targetPlant.getName() + ", 位置: (" + targetPlant.getX() + ", " + targetPlant.getY() + ")");

        // 植物受到攻击，减少生命值
        targetPlant.loseHealth(Zombie.damage); // 确保 damage 已经定义


        // 检查植物是否被摧毁
        if (targetPlant.getHealth() <= 0) {
            // 植物死亡的处理逻辑移到其他方法
            handlePlantDestruction(x, y, position);
        }
    } else {
        System.out.println("没有找到目标植物");
    }
}

    private void handlePlantDestruction(int x, int y, GameManager.GridPosition position) {
        System.out.println("死亡");
        GameWindow.cards[x][y].removeAll();
        GameWindow.cards[x][y].revalidate();
        GameWindow.cards[x][y].repaint();
        // 从 plants 映射中删除被摧毁的植物
        plants.remove(position);
    }
}
