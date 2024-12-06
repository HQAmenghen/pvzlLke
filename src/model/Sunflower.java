package model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Sunflower 类继承自 Plant 类，表示游戏中的太阳花植物。
 */
public class Sunflower extends Plant {
    private long sunGenerationRate; // 生成阳光的频率（毫秒）
    private long lastSunGeneratedTime; // 上次生成阳光的时间

    /**
     * 内部静态类 Constants，用于存储太阳花的常量值。
     */
    private static class Constants {
        public static final int SUNFLOWER_COST = 50; // 种植太阳花所需的阳光数量
        public static final int SUNFLOWER_HEALTH = 60; // 太阳花的生命值
        public static final int SUNFLOWER_SUN_GENERATION_RATE = 5000; // 太阳花生成阳光的频率（毫秒）
    }

    /**
     * 构造函数，用于创建一个新的太阳花对象。
     *
     * @param position 太阳花的位置
     * @param image    太阳花的图像资源
     */
    public Sunflower(Point position, BufferedImage image) {
        // 调用父类 Plant 的构造函数，传递必要的参数
        super(Constants.SUNFLOWER_HEALTH, Constants.SUNFLOWER_COST, position, image);

        // 初始化太阳花的生成阳光频率
        this.sunGenerationRate = Constants.SUNFLOWER_SUN_GENERATION_RATE;

        // 记录上次生成阳光的时间为当前时间
        this.lastSunGeneratedTime = System.currentTimeMillis();
    }

    /**
     * 更新太阳花的状态，检查是否需要生成新的阳光。
     */
    public void update() {
        long currentTime = System.currentTimeMillis();
        // 检查当前时间是否超过上次生成阳光的时间加上生成频率
        if (currentTime - lastSunGeneratedTime >= sunGenerationRate) {
            // 生成新的阳光
            generateSun();
            // 更新上次生成阳光的时间为当前时间
            lastSunGeneratedTime = currentTime;
        }
    }

    /**
     * 生成阳光的逻辑方法。
     * 可以在这里添加具体的逻辑，例如增加玩家的阳光计数。
     */
    private void generateSun() {
        // 生成阳光的逻辑，例如添加阳光到玩家的阳光计数
    }

    /**
     * 绘制太阳花的方法。
     *
     * @param g 用于绘制的 Graphics 对象
     */
    public void draw(Graphics g) {
        // 绘制太阳花的逻辑
    }
}
