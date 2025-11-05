package mthree.com.fullstackschool.dao.mappers;

import mthree.com.fullstackschool.model.Course;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        //YOUR CODE STARTS HERE

        Course course = new Course();
        course.setCourseId(rs.getInt("courseid"));
        course.setCourseName(rs.getString("coursename"));
        course.setCourseDesc(rs.getString("coursedesc"));
        course.setTeacherId(rs.getInt("teacherid"));
        return course;

        //YOUR CODE ENDS HERE
    }
}
