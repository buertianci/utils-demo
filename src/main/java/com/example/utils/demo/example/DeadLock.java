package com.example.utils.demo.example;

/**
 * 死锁样例
 * 死锁的四个必要条件
 * 1.互斥条件
 * 2.请求与保持条件
 * 3.不可剥夺条件
 * 4.循环等待条件
 */
public class DeadLock {
    private static String LOCK1 = "LOCK_1";
    private static String LOCK2 = "LOCK_2";

    public static void main(String[] args) {
        Thread threadA = new Thread(() ->{
            try {
                while (true) {
                    synchronized (DeadLock.LOCK1) {
                        System.out.println(Thread.currentThread().getName() + "锁住了 LOCK_1");
                        Thread.sleep(1000);
                        synchronized (DeadLock.LOCK2) {
                            System.out.println(Thread.currentThread().getName() + "锁住了 LOCK_2");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() ->{
            try {
                while (true) {
                    synchronized (DeadLock.LOCK2) {
                        System.out.println(Thread.currentThread().getName() + "锁住了 LOCK_2");
                        Thread.sleep(1000);
                        synchronized (DeadLock.LOCK1) {
                            System.out.println(Thread.currentThread().getName() + "锁住了 LOCK_1");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
    }
}
