
package game;

import gui.MainWindow;
import model.Plant;
import model.Sunflower;

import java.util.*;
import java.util.Timer;
import javax.swing.*;

import javax.swing.JOptionPane;


public class GameManager {
    private static int sunPoints;
    private Timer timer;
    private JLabel sunCountLabel; // 引用sunCountLabel
    public static Map<GridPosition, Plant> plants; // 使用Map来存储所有的植物实例，键为植物位置

    public GameManager() {
        plants = new HashMap<>();
        // 初始化plants map
    }


    public void resetGame() {
        sunPoints = 200; // 初始阳光点数
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
        int result = JOptionPane.showConfirmDialog(null, "游戏结束！是否返回主窗口？", "游戏结束", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            currentFrame.dispose(); // 关闭当前窗口

            MainWindow mainWindow = new MainWindow(); // 创建并显示主窗口
            mainWindow.setVisible(true);
        }
    }
    private void scheduleSunIncrease() {
        TimerTask task = new TimerTask() {
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
        };
        timer.scheduleAtFixedRate(task, 5000, 5000); // 每隔5秒执行一次
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
            sunCountLabel.repaint(); // 只重新绘制
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

}
