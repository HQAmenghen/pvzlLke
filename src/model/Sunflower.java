   package model;

   import game.GameManager;

   import java.util.Timer;
   import java.util.TimerTask;

   public class Sunflower extends Plant {
       private final GameManager gameManager; // 创建 GameManager 实例
       private final Timer timer;

       private static class Constants {
           public static final int SUNFLOWER_COST = 50; // 种植太阳花所需的阳光数量
           public static final int SUNFLOWER_HEALTH = 60; // 太阳花的生命值
           public static final int SUNFLOWER_SUN_AMOUNT = 25; // 太阳花每次生成的阳光数量
       }

       public Sunflower() {
           // 调用父类 Plant 的构造函数，传递必要的参数
           super(Constants.SUNFLOWER_HEALTH, Constants.SUNFLOWER_COST, "/resources/place_sunflower.png");
           // 初始化 GameManager 实例
           gameManager = new GameManager();
           // 初始化 Timer 实例
           timer = new Timer();
       }


       public void update() {
           TimerTask task = new TimerTask() {
               public void run() {
                   // 生成新的阳光
                   generateSun();
                   // 输出日志
                   System.out.println("生成阳光");
               }
           };
           // 设置延迟时间为3000毫秒（3秒），并且只执行一次
           timer.schedule(task, 3000);
       }

       private void generateSun() {
           // 增加玩家的阳光数量
           gameManager.addSunPoints(Constants.SUNFLOWER_SUN_AMOUNT);
       }

   }
   