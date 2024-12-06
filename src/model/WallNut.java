package model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * WallNut 类继承自 Plant 类，表示游戏中的坚果墙植物。
 */
public class WallNut extends Plant {
    private long healthTimer; // 健康状态更新时间

    /**
     * 内部静态类 Constants，用于存储坚果墙的常量值。
     */
    public static class Constants {
        public static final int WALLNUT_COST = 50; // 种植坚果墙所需的阳光数量
        public static final int WALLNUT_HEALTH = 200; // 坚果墙的生命值
        public static final int WALLNUT_HEALTH_UPDATE_TIME = 1000; // 健康状态更新间隔时间（毫秒）
    }

    /**
     * 构造函数，用于创建一个新的坚果墙对象。
     *
     * @param position 坚果墙的位置
     * @param image    坚果墙的图像资源
     */
    public WallNut(Point position, BufferedImage image) {
        // 调用父类 Plant 的构造函数，传递必要的参数
        super(Constants.WALLNUT_HEALTH, Constants.WALLNUT_COST, position, image);

        // 初始化健康状态更新时间
        this.healthTimer = System.currentTimeMillis();
    }

    /**
     * 更新坚果墙的状态，检查是否需要更新健康状态。
     */
    public void update() {
        long currentTime = System.currentTimeMillis();
        // 检查当前时间是否超过上次健康状态更新的时间加上更新间隔时间
        if (currentTime - healthTimer >= Constants.WALLNUT_HEALTH_UPDATE_TIME) {
            // 更新健康状态的逻辑
            updateHealth();
            // 更新上次健康状态更新的时间为当前时间
            healthTimer = currentTime;
        }
    }

    /**
     * 更新坚果墙健康状态的逻辑方法。
     * 可以在这里添加具体的逻辑，例如减少生命值或增加防护效果。
     */
    private void updateHealth() {
        // 更新健康状态的逻辑
    }

    /**
     * 绘制坚果墙的方法。
     *
     * @param g 用于绘制的 Graphics 对象
     */
    public void draw(Graphics g) {
        // 绘制坚果墙的逻辑
    }
}
