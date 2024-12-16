package model;

import game.GameManager;

public class Sunflower extends Plant {
    private long sunGenerationRate; // 生成阳光的频率（毫秒）
    private long lastSunGeneratedTime; // 上次生成阳光的时间

    private static class Constants {
        public static final int SUNFLOWER_COST = 50; // 种植太阳花所需的阳光数量
        public static final int SUNFLOWER_HEALTH = 60; // 太阳花的生命值
        public static final int SUNFLOWER_SUN_GENERATION_RATE = 5000; // 太阳花生成阳光的频率（毫秒）
        public static final int SUNFLOWER_SUN_AMOUNT = 25; // 太阳花每次生成的阳光数量
    }

    public Sunflower() {
        // 调用父类 Plant 的构造函数，传递必要的参数
        super(Constants.SUNFLOWER_HEALTH, Constants.SUNFLOWER_COST, "/resources/place_sunflower.png");

        // 初始化太阳花的生成阳光频率
        sunGenerationRate = Constants.SUNFLOWER_SUN_GENERATION_RATE;
        // 记录上次生成阳光的时间为当前时间
        lastSunGeneratedTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        // 检查当前时间是否超过上次生成阳光的时间加上生成频率
        if (currentTime - lastSunGeneratedTime >= sunGenerationRate) {
            // 生成新的阳光
            generateSun();
            // 更新上次生成阳光的时间为当前时间
            lastSunGeneratedTime = currentTime;
            // 输出日志
            System.out.println("生成阳光");
        }
    }

    private void generateSun() {
        // 增加玩家的阳光数量
        GameManager.addSunPoints(Constants.SUNFLOWER_SUN_AMOUNT);
    }
}
