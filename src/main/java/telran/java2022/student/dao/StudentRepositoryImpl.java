package telran.java2022.student.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import telran.java2022.student.model.Student;

@Component
public class StudentRepositoryImpl implements StudentRepository {
	Map<Integer, Student> students = new ConcurrentHashMap<>();

	@Override
	public Student save(Student student) {
		students.put(student.getId(), student);
		return student;
	}

	@Override
	public Optional<Student> findById(int id) {
		return Optional.ofNullable(students.get(id));
	}

	@Override
	public void deleteById(int id) {
		students.remove(id);

	}

	@Override
	public List<Student> findStudentsByName(String name) {
		return students.values().stream().filter(student -> student.getName().equals(name)).collect(Collectors.toList());
	}

	@Override
	public List<Student> findStudentsByExamScore(String exam, int score) {
	return students.values().stream().filter(student -> student.getScores().get(exam) == score).collect(Collectors.toList());
	}


}
