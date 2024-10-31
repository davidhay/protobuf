package com.baeldung.protobuf.convert;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.google.protobuf.Message;

public class ProtobufUtilUnitTest {

  public static String PROTOBUF_TEXT_FORMAT = """      
      fields {
        key: "boolean"
        value {
          bool_value: true
        }
      }
      fields {
        key: "color"
        value {
          string_value: "gold"
        }
      }
      fields {
        key: "object"
        value {
          struct_value {
            fields {
              key: "a"
              value {
                string_value: "b"
              }
            }
            fields {
              key: "c"
              value {
                string_value: "d"
              }
            }
          }
        }
      }
      fields {
        key: "string"
        value {
          string_value: "Hello World"
        }
      }
      """;
  public static String JSON_STR = """
      {
        "boolean": true,
        "color": "gold",
        "object": {
          "a": "b",
          "c": "d"
        },
        "string": "Hello World"
      }""";

  @Test
  public void givenJson_convertToProtobuf() throws IOException {
    Message protobuf = ProtobufUtil.fromJson(JSON_STR);
    assertTrue(protobuf.toString().contains("key: \"boolean\""));
    assertTrue(protobuf.toString().contains("string_value: \"Hello World\""));
    assertThat(protobuf.toString()).isEqualTo(PROTOBUF_TEXT_FORMAT);
  }

  @Test
  public void givenProtobuf_convertToJson() throws IOException {
    Message protobuf = ProtobufUtil.fromJson(JSON_STR);
    String json = ProtobufUtil.toJson(protobuf);
    assertTrue(json.contains("\"boolean\": true"));
    assertTrue(json.contains("\"string\": \"Hello World\""));
    assertTrue(json.contains("\"color\": \"gold\""));
  }
}