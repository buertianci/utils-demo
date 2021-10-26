package com.example.utils.demo.entity;

public class EnumDemo {

    enum EnumExample {
        MONDAY("星期一"),
        TUESDAY("星期二"),
        WEDNESDAY("星期三"),
        THURSDAY("星期四"),
        FRIDAY("星期五"),
        SATURDAY("星期六"),
        SUNDAY("星期日");//记住要用分号结束

        private String desc;//中文描述

        private EnumExample(String desc){
            this.desc = desc;
        }

        private String getDesc(){
            return desc;
        }

        public static void main(String[] args) {
            for (EnumExample enumExample:EnumExample.values()){
                System.out.println("name: "+enumExample.name());
                System.out.println("desc: "+enumExample.getDesc());
            }
        }
    }

    enum EnumExample1 {

        FIRST {
            @Override
            public String getInfo() {
                return "I am first";
            }
        },
        SECOND {
            @Override
            public String getInfo() {
                return "I am second";
            }
        };

        public abstract String getInfo();

        public static void main(String[] args) {
            System.out.println(EnumExample1.FIRST.getInfo());
            System.out.println(EnumExample1.SECOND.getInfo());
        }
    }

}
