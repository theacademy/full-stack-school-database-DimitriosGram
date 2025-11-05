package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.TeacherDao;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherServiceInterface {

    //YOUR CODE STARTS HERE
    private final TeacherDao teacherDao;

    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }
    //YOUR CODE ENDS HERE

    @Override
    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE
        return teacherDao.getAllTeachers();
        //YOUR CODE ENDS HERE
    }

    @Override
    public Teacher getTeacherById(int id) {
        //YOUR CODE STARTS HERE
        try {
            return teacherDao.findTeacherById(id);
        } catch (DataAccessException e) {
            Teacher t = new Teacher();
            t.setTeacherFName("Teacher Not Found");
            t.setTeacherLName("Teacher Not Found");
            return t;
        }
        //YOUR CODE ENDS HERE
    }

    @Override
    public Teacher addNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE
        if (teacher.getTeacherFName() == null || teacher.getTeacherFName().isBlank() ||
                teacher.getTeacherLName() == null || teacher.getTeacherLName().isBlank()) {
            teacher.setTeacherFName("First Name blank, teacher NOT added");
            teacher.setTeacherLName("Last Name blank, teacher NOT added");
            return teacher;
        }
        return teacherDao.createNewTeacher(teacher);
        //YOUR CODE ENDS HERE
    }

    @Override
    public Teacher updateTeacherData(int id, Teacher teacher) {
        //YOUR CODE STARTS HERE
        if (id != teacher.getTeacherId()) {
            teacher.setTeacherFName("IDs do not match, teacher not updated");
            teacher.setTeacherLName("IDs do not match, teacher not updated");
            return teacher;
        }
        teacherDao.updateTeacher(teacher);
        return teacher;
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteTeacherById(int id) {
        //YOUR CODE STARTS HERE
        teacherDao.deleteTeacher(id);
        //YOUR CODE ENDS HERE
    }
}
