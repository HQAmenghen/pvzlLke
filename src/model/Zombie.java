package model;

import java.awt.*;


public class Zombie {
    private int health; // 僵尸的生命值
    private Point position; // 僵尸的位置
    protected String imagePath; // 图片路径


    public static class Constants {
        public static final int ZOMBIE_HEALTH = 100; // 僵尸的生命值
        public static final int ZOMBIE_MOVE_SPEED = 1; // 僵尸的移动速度（像素/毫秒）
    }

    public Zombie(Point position,String imagePath) {
        this.health = Constants.ZOMBIE_HEALTH;
        this.position = position;
        this.imagePath = "/src/resources/zombie.jpg";

    }


    private void move() {
        // 获取当前位置
        Point currentPosition = getPosition();
        // 计算新的位置
        Point newPosition = new Point(currentPosition.x - Constants.ZOMBIE_MOVE_SPEED, currentPosition.y);
        // 设置新的位置
        setPosition(newPosition);
    }



    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public void loseHealth(int damage) {
        health -= damage;
        if (health <= 0) {
            // 僵尸死亡的处理逻辑
        }
    }
    public boolean isAlive() {
        return health > 0;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getImage() {
        return imagePath ;
    }

}
