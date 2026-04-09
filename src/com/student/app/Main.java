package com.student.app;

import com.student.dao.StudentDAO;
import com.student.model.Student;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentDAO dao = new StudentDAO();

    public static void main(String[] args) {
        System.out.println("===== 学生管理系统（JDBC + MySQL）=====");
        while (true) {
            showMenu();
            int choice = getIntInput();
            scanner.nextLine(); // 吃回车

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> listAll();
                case 3 -> findStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> {
                    System.out.println("再见！");
                    System.exit(0);
                }
                default -> System.out.println("输入错误，请重新选择（1~6）。");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n-------------------");
        System.out.println("1. 添加学生");
        System.out.println("2. 查看所有学生");
        System.out.println("3. 按学号查找");
        System.out.println("4. 修改学生");
        System.out.println("5. 删除学生");
        System.out.println("6. 退出");
        System.out.print("请选择：");
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("请输入数字：");
            scanner.next();
        }
        return scanner.nextInt();
    }

    // 添加学生
    private static void addStudent() {
        System.out.print("学号：");
        String id = scanner.nextLine().trim();
        System.out.print("姓名：");
        String name = scanner.nextLine().trim();
        System.out.print("年龄：");
        int age = getIntInput();
        scanner.nextLine(); // 吃回车
        System.out.print("年级：");
        String grade = scanner.nextLine().trim();

        Student s = new Student(id, name, age, grade);
        if (dao.addStudent(s)) {
            System.out.println("添加成功！");
        } else {
            System.out.println("添加失败，可能是学号重复。");
        }
    }

    // 查看所有学生
    private static void listAll() {
        List<Student> list = dao.findAll();
        if (list.isEmpty()) {
            System.out.println("暂无学生数据。");
        } else {
            System.out.println("\n所有学生列表：");
            for (Student s : list) {
                System.out.println(s);
            }
        }
    }

    // 按学号查找
    private static void findStudent() {
        System.out.print("请输入学号：");
        String id = scanner.nextLine().trim();
        Student s = dao.findById(id);
        if (s == null) {
            System.out.println("未找到该学生。");
        } else {
            System.out.println(s);
        }
    }

    // 修改学生（先查后改）
    private static void updateStudent() {
        System.out.print("请输入要修改的学号：");
        String id = scanner.nextLine().trim();
        Student old = dao.findById(id);
        if (old == null) {
            System.out.println("学号不存在。");
            return;
        }
        System.out.println("当前信息：" + old);
        System.out.print("新姓名（直接回车不变）：");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = old.getName();

        System.out.print("新年龄（直接回车不变）：");
        String ageStr = scanner.nextLine().trim();
        int age = old.getAge();
        if (!ageStr.isEmpty()) {
            try {
                age = Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {
                System.out.println("年龄输入无效，保留原值。");
            }
        }

        System.out.print("新年级（直接回车不变）：");
        String grade = scanner.nextLine().trim();
        if (grade.isEmpty()) grade = old.getGrade();

        Student newStu = new Student(id, name, age, grade);
        if (dao.updateStudent(newStu)) {
            System.out.println("修改成功！");
        } else {
            System.out.println("修改失败。");
        }
    }

    // 删除学生
    private static void deleteStudent() {
        System.out.print("请输入要删除的学号：");
        String id = scanner.nextLine().trim();
        System.out.print("确认删除？(y/n)：");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("y")) {
            if (dao.deleteStudent(id)) {
                System.out.println("删除成功！");
            } else {
                System.out.println("学号不存在，删除失败。");
            }
        } else {
            System.out.println("已取消删除。");
        }
    }
}