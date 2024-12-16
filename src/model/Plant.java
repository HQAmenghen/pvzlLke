package model;

import java.awt.*;

public abstract class Plant {
    protected int health; // 生命值
    protected int cost; // 费用
    protected String imagePath; // 图片路径


    public Plant(int health, int cost, String imagePath) {
        this.health = health;
        this.cost = cost;
        this.imagePath = imagePath;
    }

    public void loseHealth(int damage) {
        health -= damage;
        if (health <= 0) {
            // 可以在这里添加植物死亡后的逻辑
        }
    }

    public int getCost() { // 获取费用
        return cost;
    }

    public String getImagePath() { // 获取图像路径
        return imagePath;
    }
}

