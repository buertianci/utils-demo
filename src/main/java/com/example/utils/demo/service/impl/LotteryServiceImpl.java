package com.example.utils.demo.service.impl;

import com.example.utils.demo.entity.LotteryPerson;
import com.example.utils.demo.service.LotteryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 抽奖服务实现类
 */
@Service
public class LotteryServiceImpl implements LotteryService {
    
    // 使用内存存储人员信息
    private final Map<Long, LotteryPerson> personMap = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private Long nextId = 1L;
    
    @Override
    public LotteryPerson addPerson(LotteryPerson person) {
        person.setId(nextId++);
        person.setCreateTime(LocalDateTime.now());
        person.setUpdateTime(LocalDateTime.now());
        person.setIsWon(0);
        personMap.put(person.getId(), person);
        return person;
    }
    
    @Override
    public List<LotteryPerson> addPersons(List<LotteryPerson> persons) {
        List<LotteryPerson> addedPersons = new ArrayList<>();
        for (LotteryPerson person : persons) {
            addedPersons.add(addPerson(person));
        }
        return addedPersons;
    }
    
    @Override
    public List<LotteryPerson> getAvailablePersons() {
        return personMap.values().stream()
                .filter(person -> person.getIsWon() == 0)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<LotteryPerson> getAllPersons() {
        return new ArrayList<>(personMap.values());
    }
    
    @Override
    public List<LotteryPerson> getWonPersons() {
        return personMap.values().stream()
                .filter(person -> person.getIsWon() == 1)
                .collect(Collectors.toList());
    }
    
    @Override
    public LotteryPerson draw() {
        List<LotteryPerson> availablePersons = getAvailablePersons();
        if (availablePersons.isEmpty()) {
            return null;
        }
        
        // 随机选择一个人
        int index = random.nextInt(availablePersons.size());
        LotteryPerson winner = availablePersons.get(index);
        
        // 标记为中奖
        winner.setIsWon(1);
        winner.setWonTime(LocalDateTime.now());
        winner.setUpdateTime(LocalDateTime.now());
        
        return winner;
    }
    
    @Override
    public void resetLottery() {
        personMap.values().forEach(person -> {
            person.setIsWon(0);
            person.setWonTime(null);
            person.setPrize(null);
            person.setUpdateTime(LocalDateTime.now());
        });
    }
    
    @Override
    public void deletePerson(Long id) {
        personMap.remove(id);
    }
} 