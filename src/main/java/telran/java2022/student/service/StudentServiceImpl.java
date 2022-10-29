package telran.java2022.student.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import telran.java2022.student.dao.StudentRepository;
import telran.java2022.student.dto.ScoreDto;
import telran.java2022.student.dto.StudentCreateDto;
import telran.java2022.student.dto.StudentDto;
import telran.java2022.student.dto.StudentUpdateDto;
import telran.java2022.student.model.Student;

@Component
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentRepository studentRepository;

	@Override
	public Boolean addStudent(StudentCreateDto studentCreateDto) {
		if(studentRepository.findById(studentCreateDto.getId()).isPresent()) {
			return false;
		}
		Student student = new Student(studentCreateDto.getId(), 
				studentCreateDto.getName(), studentCreateDto.getPassword());
		studentRepository.save(student);
		return true;
	}

	@Override
	public StudentDto findStudent(Integer id) {
		Student student = studentRepository.findById(id).orElse(null);
		return student == null ? null : StudentDto.builder()
											.id(student.getId())
											.name(student.getName())
											.scores(student.getScores())
											.build();
	}

	@Override
	public StudentDto removeStudent(Integer id) {
		Student student = studentRepository.findById(id).orElse(null);
		studentRepository.deleteById(id);
		return student == null ? null : StudentDto.builder()
				.id(student.getId())
				.name(student.getName())
				.scores(student.getScores())
				.build();
	}

	@Override
	public StudentCreateDto updateStudent(Integer id, StudentUpdateDto studentUpdateDto) {
		Student student = studentRepository.findById(id).orElse(null);
		if (student == null || studentUpdateDto == null)
			return null;
		student.setName(studentUpdateDto.getName());
		student.setPassword(studentUpdateDto.getPassword());
		return StudentCreateDto.builder().
				id(id).
				name(studentUpdateDto.getName()).
				password(studentUpdateDto.getPassword()).
				build();
	}

	@Override
	public Boolean addScore(Integer id, ScoreDto scoreDto) {
		// TODO Auto-generated method stub
		Student student = studentRepository.findById(id).orElse(null);
		if (student == null || scoreDto == null)
			return false;
		return student.addScore(scoreDto.getExamName(), scoreDto.getScore());
	}

	@Override
	public List<StudentDto> findStudentsByName(String name) {
		List<Student> students = studentRepository.findStudentsByName(name);
		if ((students == null || students.size() == 0))
			return null;
		List<StudentDto> result = students.stream().map(student -> StudentDto.builder()
				.id(student.getId())
				.name(student.getName())
				.scores(student.getScores())
				.build()).collect(Collectors.toList());
		return result;
	}

	@Override
	public Long getStudentsNamesQuantity(List<String> names) {
		Long result = names.stream().mapToLong(name -> studentRepository.findStudentsByName(name).size()).sum();
		return result;
	}

	@Override
	public List<StudentDto> getStudentsByExamScore(String exam, Integer score) {
		List<Student> students = studentRepository.findStudentsByExamScore(exam,score);
		if (students == null || students.size() == 0)
			return null;
		List<StudentDto> result = students.stream().map(student -> StudentDto.builder()
				.id(student.getId())
				.name(student.getName())
				.scores(student.getScores())
				.build()).collect(Collectors.toList());
		return result;
	}

}
