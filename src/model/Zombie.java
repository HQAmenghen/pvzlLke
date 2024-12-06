package model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Zombie 类表示游戏中的僵尸。
 */
public class Zombie {
    private int health; // 僵尸的生命值
    private Point position; // 僵尸的位置
    private BufferedImage image; // 僵尸的图像资源
    private long lastMoveTime; // 上次移动的时间

    /**
     * 内部静态类 Constants，用于存储僵尸的常量值。
     */
    public static class Constants {
        public static final int ZOMBIE_HEALTH = 100; // 僵尸的生命值
        public static final int ZOMBIE_MOVE_SPEED = 1; // 僵尸的移动速度（像素/毫秒）
        public static final int ZOMBIE_MOVE_RATE = 100; // 僵尸移动间隔时间（毫秒）
    }

    /**
     * 构造函数，用于创建一个新的僵尸对象。
     *
     * @param position 僵尸的位置
     * @param image    僵尸的图像资源
     */
    public Zombie(Point position, BufferedImage image) {
        this.health = Constants.ZOMBIE_HEALTH;
        this.position = position;
        this.image = image;
        this.lastMoveTime = System.currentTimeMillis();
    }

    /**
     * 更新僵尸的状态，检查是否需要移动。
     */
    public void update() {
        long currentTime = System.currentTimeMillis();
        // 检查当前时间是否超过上次移动的时间加上移动间隔时间
        if (currentTime - lastMoveTime >= Constants.ZOMBIE_MOVE_RATE) {
            // 移动僵尸
            move();
            // 更新上次移动的时间为当前时间
            lastMoveTime = currentTime;
        }
    }

    /**
     * 移动僵尸的逻辑方法。
     * 可以在这里添加具体的逻辑，例如向左移动。
     */
    private void move() {
        // 获取当前位置
        Point currentPosition = getPosition();
        // 计算新的位置
        Point newPosition = new Point(currentPosition.x - Constants.ZOMBIE_MOVE_SPEED, currentPosition.y);
        // 设置新的位置
        setPosition(newPosition);
    }

    /**
     * 绘制僵尸的方法。
     *
     * @param g 用于绘制的 Graphics 对象
     */
    public void draw(Graphics g) {
        // 获取当前位置
        Point position = getPosition();
        // 绘制僵尸的图像
        g.drawImage(getImage(), position.x, position.y, null);
    }

    /**
     * 获取僵尸的生命值。
     *
     * @return 僵尸的生命值
     */
    public int getHealth() {
        return health;
    }

    /**
     * 设置僵尸的生命值。
     *
     * @param health 僵尸的生命值
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * 减少僵尸的生命值。
     *
     * @param damage 受到的伤害值
     */
    public void loseHealth(int damage) {
        health -= damage;
        if (health <= 0) {
            // 僵尸死亡的处理逻辑
        }
    }

    /**
     * 检测僵尸是否存活。
     *
     * @return 如果僵尸存活返回 true，否则返回 false
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * 获取僵尸的位置。
     *
     * @return 僵尸的位置
     */
    public Point getPosition() {
        return position;
    }

    /**
     * 设置僵尸的位置。
     *
     * @param position 僵尸的位置
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * 获取僵尸的图像资源。
     *
     * @return 僵尸的图像资源
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * 设置僵尸的图像资源。
     *
     * @param image 僵尸的图像资源
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
