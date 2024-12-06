package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Plant {
    protected int health;//生命值
    protected int cost;//费用
    protected Point position;//面板位置
    protected BufferedImage image;//图像


    public Plant(int health, int cost, Point position, BufferedImage image) {
        this.health = health;
        this.cost = cost;
        this.position = position;
        this.image = image;
    }

    public void loseHealth(int damage) {
        health -= damage;
        if (health <= 0) {
            // 植物死亡的处理逻辑
        }
    }

    public boolean isAlive() {//检测是否存活
        return health > 0;
    }

    public Point getPosition() {//获取位置
        return position;
    }

    public void setPosition(Point position) {//设置位置
        this.position = position;
    }

    public int getCost() {//获取费用
        return cost;
    }

    public void draw(Graphics g) {
    }


    public byte[] getImagePath() {
        return new byte[0];
    }
}
