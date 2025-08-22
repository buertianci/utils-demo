package com.example.utils.demo.service;

import com.example.utils.demo.entity.LotteryPerson;

import java.util.List;

/**
 * 抽奖服务接口
 */
public interface LotteryService {
    
    /**
     * 添加抽奖人员
     */
    LotteryPerson addPerson(LotteryPerson person);
    
    /**
     * 批量添加抽奖人员
     */
    List<LotteryPerson> addPersons(List<LotteryPerson> persons);
    
    /**
     * 获取所有未中奖人员
     */
    List<LotteryPerson> getAvailablePersons();
    
    /**
     * 获取所有人员
     */
    List<LotteryPerson> getAllPersons();
    
    /**
     * 获取已中奖人员
     */
    List<LotteryPerson> getWonPersons();
    
    /**
     * 执行抽奖
     */
    LotteryPerson draw();
    
    /**
     * 重置抽奖状态
     */
    void resetLottery();
    
    /**
     * 删除人员
     */
    void deletePerson(Long id);
} 