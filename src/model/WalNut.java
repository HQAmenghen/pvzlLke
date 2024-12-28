package model;



public class WalNut extends Plant {



    public static class Constants {
        public static final int WALNUT_COST = 75; // 种植坚果墙所需的阳光数量
        public static final int WALNUT_HEALTH = 200; // 坚果墙的生命值
    }

    public WalNut() {
        // 调用父类 Plant 的构造函数，传递必要的参数
        super(Constants.WALNUT_HEALTH, Constants.WALNUT_COST, "/resources/place_walnut.png");


    }


}
