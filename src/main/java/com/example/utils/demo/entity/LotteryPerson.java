package com.example.utils.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 抽奖人员实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotteryPerson {
    
    /**
     * 人员ID
     */
    private Long id;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 部门
     */
    private String department;
    
    /**
     * 职位
     */
    private String position;
    
    /**
     * 头像URL
     */
    private String avatar;
    
    /**
     * 是否已中奖 (0: 未中奖, 1: 已中奖)
     */
    private Integer isWon;
    
    /**
     * 中奖时间
     */
    private LocalDateTime wonTime;
    
    /**
     * 中奖奖项
     */
    private String prize;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    public LotteryPerson(String name, String department, String position) {
        this.name = name;
        this.department = department;
        this.position = position;
        this.isWon = 0;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
} 