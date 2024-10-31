package com.ealanta.proto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.baeldung.protobuf.BaeldungTraining.Course;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
		return new RestTemplate(Arrays.asList(hmc));
	}

	@Bean
	ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	@Bean
	public CourseRepository createTestCourses() {
		Map<Integer, Course> courses = new HashMap<>();

		Course course1 = DataUtil.createCourse(1, "REST with Spring", DataUtil.createTestStudents());
		Course course2 = DataUtil.createCourse(2, "Learn Spring Security", new ArrayList<>());

		courses.put(course1.getId(), course1);
		courses.put(course2.getId(), course2);

		return new CourseRepository(courses);
	}

}