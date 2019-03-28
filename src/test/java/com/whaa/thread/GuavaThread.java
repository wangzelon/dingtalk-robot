package com.whaa.thread;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by wangzelong 2019/3/26 17:11
 */
public class GuavaThread {

    /**
     * 生产车票
     *
     * @return
     */
    public List<String> createTickets() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("车票" + i);
        }
        return list;
    }

    public void sellTicket() {
        long start = System.currentTimeMillis();
        List<String> list = createTickets();//获取车票

        List<ListenableFuture<Integer>> futures = Lists.newArrayList();
        ExecutorService pool = Executors.newFixedThreadPool(10);//定义线程数
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(pool);
        for (int i = 0; i < list.size(); i++) {
            futures.add(executorService.submit(new Task(list.get(i))));
        }
        final ListenableFuture<List<Integer>> resultsFuture = Futures.successfulAsList(futures);
        try {//所有都执行完毕
//            resultsFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("操作完毕");
            pool.shutdown();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start)+"ms");
    }

    /**
     * 内部类，用于处理售票
     */
    class Task implements Callable<Integer> {
        private String ticket;

        /**
         * 构造方法，用于参数传递
         *
         * @param ticket
         */
        public Task(String ticket) {
            this.ticket = ticket;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("已卖" + ticket);//执行卖票过程
            return 1;
        }
    }

    public static void main(String[] args) {
        GuavaThread guavaThread = new GuavaThread();
        guavaThread.sellTicket();
    }
}
