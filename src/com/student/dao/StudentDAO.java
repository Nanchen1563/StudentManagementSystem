package com.student.dao;

import com.student.model.Student;
import com.student.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // 1. 添加学生
    public boolean addStudent(Student s) {
        String sql = "INSERT INTO student(id, name, age, grade) VALUES(?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getId());
            ps.setString(2, s.getName());
            ps.setInt(3, s.getAge());
            ps.setString(4, s.getGrade());
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            // 如果主键重复，会抛异常，可捕获后返回 false
            if (e.getErrorCode() == 1062) { // MySQL 重复键错误码
                System.out.println("学号已存在，添加失败。");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    // 2. 删除学生（按学号）
    public boolean deleteStudent(String id) {
        String sql = "DELETE FROM student WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. 修改学生信息
    public boolean updateStudent(Student s) {
        String sql = "UPDATE student SET name=?, age=?, grade=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setInt(2, s.getAge());
            ps.setString(3, s.getGrade());
            ps.setString(4, s.getId());
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. 按学号查找学生
    public Student findById(String id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Student(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("grade")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 5. 查询所有学生
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student s = new Student(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("grade")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}