package at.htlstp.restevents;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class FindingEventsFromPerson {

        @Test
        void works() throws Exception {
            mockMvc
                    .perform(get("/persons/events?personId=1"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(
                            """
                                    [
                                      {
                                        "id": 1
                                      },
                                      {
                                        "id": 2
                                      }
                                    ]
                                     """
                    ));
        }

        @Test
        void fails_for_invalid_person_id() throws Exception {
            mockMvc
                    .perform(get("/persons/events?personId=404"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DirtiesContext
    class AddingEventsToPerson {

        @Test
        public void works() throws Exception {
            var json = """
                    {
                      "id": 3
                    }
                    """;
            var request = post("/persons/1/events")
                    .contentType(APPLICATION_JSON)
                    .content(json);
            mockMvc.perform(request);

            mockMvc.perform(get("/persons/events?personId=1"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                            [
                              {
                                "id": 1
                              },
                              {
                                "id": 2
                              },
                              {
                                "id": 3,
                                "name": "Opera"
                              }
                            ]
                             """));
        }

        @Test
        void fails_for_invalid_person_id() throws Exception {
            var json = """
                    {
                      "id": 1
                    }
                    """;
            var request = post("/persons/404/events")
                    .contentType(APPLICATION_JSON)
                    .content(json);
            mockMvc
                    .perform(request)
                    .andExpect(status().isNotFound());
        }

        /**
         * Person 3 ist zu dieser Zeit in der Oper
         */
        @Test
        void fails_for_conflicting_events() throws Exception {
            var json = """
                    {
                      "id": 4
                    }
                    """;
            var request = post("/persons/3/events")
                    .contentType(APPLICATION_JSON)
                    .content(json);
            mockMvc
                    .perform(request)
                    .andExpect(status().isBadRequest());
        }
    }
}
