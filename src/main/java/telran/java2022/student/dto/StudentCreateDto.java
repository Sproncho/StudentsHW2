package telran.java2022.student.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentCreateDto {
	Integer id;
	String name;
	String password;
}
