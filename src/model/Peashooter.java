package model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Peashooter 类继承自 Plant 类，表示游戏中的豌豆射手植物。
 */
public class Peashooter extends Plant {
    private long shotTimer; // 射击时间

    /**
     * 内部静态类 Constants，用于存储豌豆射手的常量值。
     */
    public static class Constants {
        public static final int PEASHOOTER_COST = 100; // 种植豌豆射手所需的阳光数量
        public static final int PEASHOOTER_HEALTH = 100; // 豌豆射手的生命值
        public static final int PEASHOOTER_SHOT_TIME = 2000; // 豌豆射手的射击间隔时间（毫秒）
    }

    /**
     * 构造函数，用于创建一个新的豌豆射手对象。
     *
     * @param position 豌豆射手的位置
     * @param image    豌豆射手的图像资源
     */
    public Peashooter(Point position, BufferedImage image) {
        // 调用父类 Plant 的构造函数，传递必要的参数
        super(Constants.PEASHOOTER_HEALTH, Constants.PEASHOOTER_COST, position, image);

        // 初始化射击时间
        this.shotTimer = System.currentTimeMillis();
    }

    /**
     * 更新豌豆射手的状态，检查是否需要发射豌豆。
     */
    public void update() {
        long currentTime = System.currentTimeMillis();
        // 检查当前时间是否超过上次射击的时间加上射击间隔时间
        if (currentTime - shotTimer >= Constants.PEASHOOTER_SHOT_TIME) {
            // 发射豌豆
            shootPea();
            // 更新上次射击的时间为当前时间
            shotTimer = currentTime;
        }
    }

    /**
     * 发射豌豆的逻辑方法。
     * 可以在这里添加具体的逻辑，例如创建并发射豌豆对象。
     */
    private void shootPea() {
        // 发射豌豆的逻辑
    }

    /**
     * 绘制豌豆射手的方法。
     *
     * @param g 用于绘制的 Graphics 对象
     */
    public void draw(Graphics g) {
        // 绘制豌豆射手的逻辑
    }
}
