package telran.java2022.student.dao;

import java.util.List;
import java.util.Optional;

import telran.java2022.student.model.Student;

public interface StudentRepository {
	Student save(Student student);

	Optional<Student> findById(int id);

	void deleteById(int id);

	List<Student> findStudentsByName(String name);

	List<Student> findStudentsByExamScore(String exam, int score);
}
