package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.TeacherMapper;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;

    public TeacherDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Teacher createNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE
        String sql = "INSERT INTO Teacher (TeacherFName, TeacherLName) VALUES (?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, teacher.getTeacherFName());
            ps.setString(2, teacher.getTeacherLName());
            return ps;
        }, keyHolder);

        teacher.setTeacherId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return teacher;
        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE
        String sql = "SELECT * FROM Teacher";
        return jdbcTemplate.query(sql, new TeacherMapper());
        //YOUR CODE ENDS HERE
    }

    @Override
    public Teacher findTeacherById(int id) {
        //YOUR CODE STARTS HERE
        String sql = "SELECT * FROM Teacher WHERE TeacherId = ?";
        return jdbcTemplate.queryForObject(sql, new TeacherMapper(), id);
        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateTeacher(Teacher t) {
        //YOUR CODE STARTS HERE
        String sql = "UPDATE Teacher SET TeacherFName = ?, TeacherLName = ? WHERE TeacherId = ?";
        jdbcTemplate.update(sql, t.getTeacherFName(), t.getTeacherLName(), t.getTeacherId());
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteTeacher(int id) {
        //YOUR CODE STARTS HERE
        String sql = "DELETE FROM Teacher WHERE TeacherId = ?";
        jdbcTemplate.update(sql, id);
        //YOUR CODE ENDS HERE
    }
}
