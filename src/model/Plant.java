package model;

import javax.swing.*;
import java.awt.*;

public abstract class Plant extends JLabel {
    protected int health; // 生命值
    protected int cost; // 费用
    protected String imagePath; // 图片路径
    protected int x; // 植物的X坐标
    protected int y; // 植物的Y坐标
    protected JLabel healthLabel; // 植物的生命值标签

    public Plant(int health, int cost, String imagePath) {
        super();
        this.health = health;
        this.cost = cost;
        this.imagePath = imagePath;

        // 设置图标
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(image));

        // 初始化健康标签
        healthLabel = new JLabel(String.valueOf(health));
        healthLabel.setForeground(Color.GREEN); // 设置生命值标签颜色为绿色
        healthLabel.setFont(new Font("Arial", Font.BOLD, 14)); // 设置字体

        // 将健康标签添加到当前组件（假设你需要这样做）
        add(healthLabel); // 这里假设你需要将健康标签添加到植物的UI中
    }

    public void loseHealth(int damage) {
        health -= damage;
        updateHealthLabel(); // 更新生命值标签
    }

    private void updateHealthLabel() {
        healthLabel.setText(String.valueOf(health));
        healthLabel.revalidate();
        healthLabel.repaint();
    }

    public int getCost() { // 获取费用
        return cost;
    }

    public String getImagePath() { // 获取图像路径
        return imagePath;
    }

    public int getHealth() {
        return health;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
