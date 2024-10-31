package com.ealanta.proto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.protobuf.BaeldungTraining.Course;

@RestController
@Slf4j
public class CourseController {

  @Autowired
  CourseRepository courseRepo;

  @GetMapping("/courses/{id}")
  Course customer(@PathVariable Integer id) {
    return courseRepo.getCourse(id);
  }

  @PostMapping("/courses")
  Course customer(@RequestBody Course course, HttpServletRequest request) {

    Assert.isTrue(MimeType.valueOf(request.getContentType())
            .isCompatibleWith(MimeType.valueOf("application/x-protobuf")), "bad content type");

    log.info("POST : the content-type is {}", request.getContentType());
    return course;
  }

}