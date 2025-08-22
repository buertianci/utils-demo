package com.example.utils.demo.controller;

import com.example.utils.demo.entity.LotteryPerson;
import com.example.utils.demo.service.LotteryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽奖控制器
 */
@RestController
@RequestMapping("/api/lottery")
@Api(tags = "抽奖管理")
@CrossOrigin(origins = "*")
public class LotteryController {
    
    @Autowired
    private LotteryService lotteryService;
    
    @PostMapping("/person")
    @ApiOperation("添加抽奖人员")
    public Map<String, Object> addPerson(@RequestBody LotteryPerson person) {
        Map<String, Object> result = new HashMap<>();
        try {
            LotteryPerson addedPerson = lotteryService.addPerson(person);
            result.put("success", true);
            result.put("data", addedPerson);
            result.put("message", "添加成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "添加失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/persons")
    @ApiOperation("批量添加抽奖人员")
    public Map<String, Object> addPersons(@RequestBody List<LotteryPerson> persons) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<LotteryPerson> addedPersons = lotteryService.addPersons(persons);
            result.put("success", true);
            result.put("data", addedPersons);
            result.put("message", "批量添加成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "批量添加失败: " + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/persons")
    @ApiOperation("获取所有人员")
    public Map<String, Object> getAllPersons() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<LotteryPerson> persons = lotteryService.getAllPersons();
            result.put("success", true);
            result.put("data", persons);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败: " + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/persons/available")
    @ApiOperation("获取可抽奖人员")
    public Map<String, Object> getAvailablePersons() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<LotteryPerson> persons = lotteryService.getAvailablePersons();
            result.put("success", true);
            result.put("data", persons);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败: " + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/persons/won")
    @ApiOperation("获取已中奖人员")
    public Map<String, Object> getWonPersons() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<LotteryPerson> persons = lotteryService.getWonPersons();
            result.put("success", true);
            result.put("data", persons);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/draw")
    @ApiOperation("执行抽奖")
    public Map<String, Object> draw() {
        Map<String, Object> result = new HashMap<>();
        try {
            LotteryPerson winner = lotteryService.draw();
            if (winner != null) {
                result.put("success", true);
                result.put("data", winner);
                result.put("message", "恭喜 " + winner.getName() + " 中奖！");
            } else {
                result.put("success", false);
                result.put("message", "没有可抽奖的人员");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "抽奖失败: " + e.getMessage());
        }
        return result;
    }
    
    @PostMapping("/reset")
    @ApiOperation("重置抽奖状态")
    public Map<String, Object> resetLottery() {
        Map<String, Object> result = new HashMap<>();
        try {
            lotteryService.resetLottery();
            result.put("success", true);
            result.put("message", "重置成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "重置失败: " + e.getMessage());
        }
        return result;
    }
    
    @DeleteMapping("/person/{id}")
    @ApiOperation("删除人员")
    public Map<String, Object> deletePerson(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            lotteryService.deletePerson(id);
            result.put("success", true);
            result.put("message", "删除成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除失败: " + e.getMessage());
        }
        return result;
    }
} 