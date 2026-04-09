package com.student.model;

public class Student {
    private String id;      // 学号（唯一标识）
    private String name;
    private int age;
    private String grade;   // 年级

    // 构造方法
    public Student(String id, String name, int age, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    // Getter 和 Setter 方法
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    // 重写 toString，用于打印学生信息
    @Override
    public String toString() {
        return "学号：" + id + " | 姓名：" + name + " | 年龄：" + age + " | 年级：" + grade;
    }
}