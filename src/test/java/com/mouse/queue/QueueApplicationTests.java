package com.mouse.queue;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
class QueueApplicationTests {

    @Test
    void contextLoads() throws InterruptedException {
        val r = new Random();
        val queue = new PriorityBlockingQueue<Node>();

        r.setSeed(System.currentTimeMillis());

        Executors.newFixedThreadPool(1).submit(()->{
            while (true) {
                System.out.println(System.currentTimeMillis());
                val node = queue.take();
                System.out.println(node);
            }
        });

        for (int i = 0; i < 100;i++) {
            val node = Node.builder().i(i).a(r.nextInt()).build();
            Thread.sleep(10L);
            queue.add(node);
        }
        Thread.sleep(5000L);

//        for (int i = 0; i < queue.size(); i++) {
            //从小到大
//            System.out.println(queue.poll());
//        }
    }

}
