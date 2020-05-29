package com.tartu.library.book.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tartu.library.LibraryApplication;
import com.tartu.library.book.application.dto.BookItemDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LibraryApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BookEntryRestControllerTests {
  private final String apiPath = "/api/book";

  @Autowired ObjectMapper mapper;

  @Autowired private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void testCreateBook() throws Exception {

    BookItemDTO bookItemDTO = BookItemDTO.of("1234", null, null);
    mockMvc
        .perform(
            post(apiPath)
                .content(mapper.writeValueAsString(bookItemDTO))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());
    }
}
