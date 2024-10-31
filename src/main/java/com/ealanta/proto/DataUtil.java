package com.ealanta.proto;

import com.baeldung.protobuf.BaeldungTraining.Course;
import com.baeldung.protobuf.BaeldungTraining.Student;
import com.baeldung.protobuf.BaeldungTraining.Student.PhoneNumber;
import com.baeldung.protobuf.BaeldungTraining.Student.PhoneType;
import java.util.Arrays;
import java.util.List;

public class DataUtil {

  public static List<Student> createTestStudents() {
    PhoneNumber phone1 = createPhone("123456", PhoneType.MOBILE);
    Student student1 = createStudent(1, "John", "Doe", "john.doe@baeldung.com", Arrays.asList(phone1));

    PhoneNumber phone2 = createPhone("234567", PhoneType.LANDLINE);
    Student student2 = createStudent(2, "Richard", "Roe", "richard.roe@baeldung.com", Arrays.asList(phone2));

    PhoneNumber phone3_1 = createPhone("345678", PhoneType.MOBILE);
    PhoneNumber phone3_2 = createPhone("456789", PhoneType.LANDLINE);
    Student student3 = createStudent(3, "Jane", "Doe", "jane.doe@baeldung.com", Arrays.asList(phone3_1, phone3_2));

    return Arrays.asList(student1, student2, student3);
  }

  public static Student createStudent(int id, String firstName, String lastName, String email, List<PhoneNumber> phones) {
    return Student.newBuilder().setId(id).setFirstName(firstName).setLastName(lastName).setEmail(email).addAllPhone(phones).build();
  }

  public static PhoneNumber createPhone(String number, PhoneType type) {
    return PhoneNumber.newBuilder().setNumber(number).setType(type).build();
  }

  public static Course createCourse(int id, String courseName, List<Student> students) {
    return Course.newBuilder().setId(id).setCourseName(courseName).addAllStudent(students).build();
  }
}
