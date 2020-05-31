package com.tartu.library.book.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tartu.library.LibraryApplication;
import com.tartu.library.book.application.dto.BookEntryDTO;
import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.domain.repository.BookEntryRepository;
import com.tartu.library.book.domain.repository.BookItemRepository;
import com.tartu.library.person.application.dto.PersonDTO;
import com.tartu.library.person.domain.repository.PersonRepository;
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

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
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

  @Autowired
  BookEntryRepository bookEntryRepository;

  @Autowired
  BookItemRepository bookItemRepository;

  @Autowired
  PersonRepository personRepository;

  @Autowired ObjectMapper mapper;

  @Autowired private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  /**
   * Book Entry and Person should be be duplicated. Only one instance by name is allowed.
   */
  @Test
  public void testCreateBook() throws Exception {
    BookEntryDTO bookEntryDTO = BookEntryDTO.of(null, "test book", "test", LocalDate.now());
    PersonDTO personDTO = PersonDTO.of("Test User", "test@test.com");
    BookItemDTO bookItemDTO = BookItemDTO.of("1234", bookEntryDTO, personDTO);

    // create book 1
    mockMvc
        .perform(
            post(apiPath)
                .content(mapper.writeValueAsString(bookItemDTO))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());

    // create book 2
    mockMvc
            .perform(
                    post(apiPath)
                            .content(mapper.writeValueAsString(bookItemDTO))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());

    // create book 3
    mockMvc
            .perform(
                    post(apiPath)
                            .content(mapper.writeValueAsString(bookItemDTO))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());

    assertThat(bookEntryRepository.findAll().size()).isEqualTo(1);
    assertThat(bookItemRepository.findAll().size()).isEqualTo(3);
    assertThat(personRepository.findAll().size()).isEqualTo(1);
    }
}
