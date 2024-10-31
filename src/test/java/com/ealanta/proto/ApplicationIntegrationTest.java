package com.ealanta.proto;

import com.baeldung.protobuf.BaeldungTraining.Course;
import com.googlecode.protobuf.format.JsonFormat;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.MimeType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DirtiesContext
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTest {

  public static final String PROTOBUF = "application/x-protobuf";

  @Autowired
  private RestTemplate restTemplate;

  @LocalServerPort
  private int port;

  @Test
  public void whenUsingRestTemplate_thenSucceed() {
    ResponseEntity<Course> course = restTemplate.getForEntity(getUrlForCourse1(), Course.class);
    course.getHeaders().getContentType().isCompatibleWith(MimeType.valueOf(PROTOBUF));
    assertResponse(course.toString());
  }

  @Test
  public void whenUsingHttpClient_thenSucceed() throws IOException {
    InputStream responseStream = executeHttpRequest(getUrlForCourse1());
    String jsonOutput = convertProtobufMessageStreamToJsonString(responseStream);
    assertResponse(jsonOutput);
  }

  private InputStream executeHttpRequest(String url) throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet request = new HttpGet(url);
    HttpResponse httpResponse = httpClient.execute(request);

    var contentType = httpResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    assertThat(contentType, containsString(PROTOBUF));
    return httpResponse.getEntity().getContent();
  }

  private String convertProtobufMessageStreamToJsonString(InputStream protobufStream) throws IOException {
    JsonFormat jsonFormat = new JsonFormat();
    Course course = Course.parseFrom(protobufStream);
    return jsonFormat.printToString(course);
  }

  private void assertResponse(String response) {
    assertThat(response, containsString("id"));
    assertThat(response, containsString("course_name"));
    assertThat(response, containsString("REST with Spring"));
    assertThat(response, containsString("student"));
    assertThat(response, containsString("first_name"));
    assertThat(response, containsString("last_name"));
    assertThat(response, containsString("email"));
    assertThat(response, containsString("john.doe@baeldung.com"));
    assertThat(response, containsString("richard.roe@baeldung.com"));
    assertThat(response, containsString("jane.doe@baeldung.com"));
    assertThat(response, containsString("phone"));
    assertThat(response, containsString("number"));
    assertThat(response, containsString("type"));
  }

  private String getUrlForCourse1() {
    return "http://localhost:" + port + "/courses/1";
  }
  private String getUrlForCourse() {
    return "http://localhost:" + port + "/courses";
  }

  @Test
  void testPostCourse() {
    Course sentCourse = Course.newBuilder().setId(1).setCourseName("REST with Spring")
        .addAllStudent(DataUtil.createTestStudents()).build();

    ResponseEntity<Course> response = restTemplate.postForEntity(getUrlForCourse(), sentCourse, Course.class);
    assertThat(response.getHeaders().getContentType().isCompatibleWith(MimeType.valueOf(PROTOBUF)),is(true));
    Course receivedCourse = response.getBody();

    assertThat(receivedCourse,is(equalTo(sentCourse)));


  }
}