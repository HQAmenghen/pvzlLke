   package model;

   import java.awt.*;

   public class Peashooter extends Plant {
       private long shotTimer; // 射击时间

       public static class Constants {
           public static final int PEASHOOTER_COST = 100; // 种植豌豆射手所需的阳光数量
           public static final int PEASHOOTER_HEALTH = 100; // 豌豆射手的生命值
           public static final int PEASHOOTER_SHOT_TIME = 2000; // 豌豆射手的射击间隔时间（毫秒）
       }

       public Peashooter() {
           // 调用父类 Plant 的构造函数，传递必要的参数
           super(Constants.PEASHOOTER_HEALTH, Constants.PEASHOOTER_COST,"/resources/place_peashooter.png");
           // 初始化射击时间
           this.shotTimer = System.currentTimeMillis();

       }

       public void update() {
           long currentTime = System.currentTimeMillis();
           // 检查当前时间是否超过上次射击的时间加上射击间隔时间
           if (currentTime - shotTimer >= Constants.PEASHOOTER_SHOT_TIME) {
               // 发射豌豆
               shootPea();
               // 更新上次射击的时间为当前时间
               shotTimer = currentTime;
           }
       }

       private void shootPea() {
           // 发射豌豆的逻辑
       }


   }
   