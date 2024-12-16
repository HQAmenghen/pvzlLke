// GameManager.java
package game;

import model.Plant;
import model.Sunflower;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class GameManager {
    private static int sunPoints;
    private Timer timer;
    private JLabel sunCountLabel; // 引用sunCountLabel
    private List<Plant> plants; // 使用List来存储所有的植物实例

    public GameManager() {
        this.sunPoints = 200; // 初始阳光点数
        this.timer = new Timer();
        this.plants = new ArrayList<>(); // 初始化植物列表
    }

    public void setSunCountLabel(JLabel sunCountLabel) {
        this.sunCountLabel = sunCountLabel;
    }

    public void startGame() {
        scheduleSunIncrease(); // 开始定时增加阳光
        scheduleUpdate(); // 开始定时更新
    }

    public void endGame() {
        // 设置游戏结束标志
        timer.cancel(); // 取消定时任务

        // 显示最终得分或其他结束信息
        System.out.println("游戏结束！");
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

    public static void addSunPoints(int amount) {
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
        timer.scheduleAtFixedRate(task, 1000, 1000); // 每隔1秒执行一次
    }

    public void update() {
        // 更新游戏状态
        for (Plant plant : plants) {
            if (plant instanceof Sunflower) {
                ((Sunflower) plant).update(); // 通过实例调用update方法
            }
            // 如果有其他类型的植物，可以在这里添加相应的更新逻辑
        }
        updateSunCountLabel();
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }
}
