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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class FindingEventById {

        @Test
        void works() throws Exception {
            mockMvc
                    .perform(get("/events/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(
                            """
                                    {
                                      "id": 1,
                                      "name": "Brunch",
                                      "begin": "2022-01-01T08:00:00",
                                      "end": "2022-01-01T11:00:00"
                                    }
                                     """
                    ));
        }

        @Test
        void fails_for_invalid_event_id() throws Exception {
            mockMvc
                    .perform(get("/events/404"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DirtiesContext
    class SavingEvent {

        @Test
        void works() throws Exception {
            var json = """
                    {
                      "name":"new",
                      "begin":"2022-01-02T19:30:00",
                      "end":"2022-01-02T20:00:00"
                    }
                    """;
            var request = post("/events")
                    .contentType(APPLICATION_JSON)
                    .content(json);

            var resource = "/events/5";
            mockMvc.perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "http://localhost" + resource))
                    .andExpect(content().json(json)).andReturn();
            mockMvc.perform(get(resource))
                    .andExpect(status().isOk())
                    .andExpect(content().json(json));
        }

        @Test
        void fails_if_end_before_begin() throws Exception {
            var json = """
                    {
                      "name":"invalid",
                      "end":"2022-01-02T19:30:00",
                      "begin":"2022-01-02T20:00:00"
                    }
                    """;
            var request = post("/events")
                    .contentType(APPLICATION_JSON)
                    .content(json);

            mockMvc.perform(request)
                    .andExpect(status().isBadRequest());
        }
    }
}
