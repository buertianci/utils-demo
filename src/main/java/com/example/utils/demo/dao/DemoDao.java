package com.example.utils.demo.dao;

import com.example.utils.demo.entity.DemoData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 假设这个是你的DAO存储。当然还要这个类让spring管理，当然你不用需要存储，也不需要这个类。
 **/
@Slf4j
public class DemoDao {
    public void save(List<DemoData> list) {
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
        log.info("开始存储数据：{}", list.toString());
    }
}
