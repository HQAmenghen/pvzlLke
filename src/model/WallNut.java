package model;

import java.awt.*;
import java.awt.image.BufferedImage;


public class WallNut extends Plant {



    public static class Constants {
        public static final int WALLNUT_COST = 50; // 种植坚果墙所需的阳光数量
        public static final int WALLNUT_HEALTH = 200; // 坚果墙的生命值
    }

    public WallNut() {
        // 调用父类 Plant 的构造函数，传递必要的参数
        super(Constants.WALLNUT_HEALTH, Constants.WALLNUT_COST, "/resources/place_walnut.png");


    }


}
