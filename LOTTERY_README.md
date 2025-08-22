# 🎉 炫酷抽奖系统

一个功能完整、界面炫酷的抽奖系统，支持人员管理、抽奖动画、中奖记录等功能。

## ✨ 功能特性

- 🎨 **炫酷界面**: 现代化的渐变背景和动画效果
- 👥 **人员管理**: 支持添加、删除抽奖人员
- 🎯 **智能抽奖**: 已中奖人员自动排除，不会重复中奖
- 🎊 **抽奖动画**: 炫酷的抽奖动画和彩带效果
- 📊 **实时统计**: 显示总人数、可抽奖人数、已中奖人数
- 🔄 **状态重置**: 支持重置所有抽奖状态
- 📱 **响应式设计**: 支持各种设备访问
- 🎲 **随机公平**: 完全随机的抽奖算法，确保公平性

## 🚀 快速开始

### 1. 启动应用

```bash
# 编译项目
mvn clean compile

# 启动应用
mvn spring-boot:run
```

### 2. 访问系统

打开浏览器访问: `http://localhost:8080/lottery`

### 3. 添加人员

点击"管理面板"按钮，可以：
- 手动添加人员信息
- 一键添加示例数据

## 📋 API 接口

### 人员管理

- `POST /api/lottery/person` - 添加单个人员
- `POST /api/lottery/persons` - 批量添加人员
- `GET /api/lottery/persons` - 获取所有人员
- `GET /api/lottery/persons/available` - 获取可抽奖人员
- `GET /api/lottery/persons/won` - 获取已中奖人员
- `DELETE /api/lottery/person/{id}` - 删除人员

### 抽奖功能

- `POST /api/lottery/draw` - 执行抽奖
- `POST /api/lottery/reset` - 重置抽奖状态

## 🎮 使用说明

### 抽奖流程

1. **添加人员**: 在管理面板中添加抽奖人员
2. **开始抽奖**: 点击"开始抽奖"按钮
3. **观看动画**: 享受炫酷的抽奖动画效果
4. **查看结果**: 中奖者会以特殊效果显示
5. **继续抽奖**: 已中奖人员不会再次被抽中

### 管理功能

- **添加人员**: 填写姓名、部门、职位信息
- **示例数据**: 一键添加8个示例人员
- **重置状态**: 清空所有中奖记录，重新开始
- **实时统计**: 查看当前抽奖状态

## 🎨 界面特色

- **渐变背景**: 紫色渐变背景营造神秘氛围
- **毛玻璃效果**: 半透明卡片增加层次感
- **动画效果**: 
  - 标题发光动画
  - 卡片悬停效果
  - 抽奖选择动画
  - 中奖者出现动画
  - 彩带飘落效果
- **响应式布局**: 自适应不同屏幕尺寸

## 🔧 技术栈

- **后端**: Spring Boot 2.3.1
- **前端**: HTML5 + CSS3 + JavaScript
- **存储**: 内存存储（可扩展为数据库）
- **API**: RESTful API
- **文档**: Swagger 2.9.2
- **工具库**: 
  - Lombok 1.18.8
  - Jackson 2.12.0
  - FastDFS Client 1.27.2
  - Disruptor 3.3.4

## 📁 项目结构

```
src/main/java/com/example/utils/demo/
├── config/
│   ├── SwaggerConfig.java        # Swagger配置
│   ├── ZookeeperConfig.java      # Zookeeper配置
│   └── FdfsConfiguration.java    # FastDFS配置
├── controller/
│   ├── LotteryController.java    # 抽奖API控制器
│   ├── PageController.java       # 页面控制器
│   ├── FastDfsController.java    # FastDFS控制器
│   └── EasyexcelController.java  # Excel处理控制器
├── entity/
│   ├── LotteryPerson.java        # 抽奖人员实体
│   ├── DemoData.java             # 示例数据实体
│   └── EnumDemo.java             # 枚举示例
├── service/
│   ├── LotteryService.java       # 抽奖服务接口
│   └── impl/
│       └── LotteryServiceImpl.java # 抽奖服务实现
├── util/                         # 工具类
├── work/                         # 业务工具
├── disrupMq/                     # 消息队列
└── DemoApplication.java          # 启动类

src/main/resources/
├── static/
│   └── lottery.html              # 抽奖页面
├── templates/
│   └── signin.html               # 签到页面
└── application.yml               # 配置文件
```

## 🎯 核心功能实现

### 抽奖逻辑

```java
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
    
    return winner;
}
```

### 动画效果

- **抽奖动画**: 随机高亮不同人员卡片
- **中奖动画**: 中奖者卡片旋转出现
- **彩带效果**: 随机颜色彩带从顶部飘落

## 🔮 扩展建议

1. **数据库存储**: 将内存存储改为数据库存储
2. **用户认证**: 添加登录功能
3. **奖项设置**: 支持多种奖项类型
4. **抽奖历史**: 记录抽奖历史记录
5. **导出功能**: 支持导出中奖名单
6. **音效**: 添加抽奖音效
7. **数据持久化**: 使用Redis或数据库存储
8. **权限管理**: 添加管理员权限控制
9. **活动管理**: 支持多个抽奖活动

## 📝 注意事项

- 当前使用内存存储，重启应用后数据会丢失
- 建议在生产环境中使用数据库存储
- 抽奖结果完全随机，确保公平性
- 生产环境需要配置HTTPS和域名
- 支持跨域访问，已配置CORS

## 🎊 开始使用

现在你可以启动应用，享受这个炫酷的抽奖系统了！

访问 `http://localhost:8080/lottery` 开始你的抽奖之旅吧！ 🎉 