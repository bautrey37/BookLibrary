package com.tartu.library.book.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tartu.library.LibraryApplication;
import com.tartu.library.book.application.dto.BookEntryDTO;
import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.domain.model.BookItem;
import com.tartu.library.book.domain.model.BookStatus;
import com.tartu.library.book.domain.repository.BookEntryRepository;
import com.tartu.library.book.domain.repository.BookItemRepository;
import com.tartu.library.person.application.dto.PersonDTO;
import com.tartu.library.person.domain.model.Person;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

  @Autowired BookEntryRepository bookEntryRepository;

  @Autowired BookItemRepository bookItemRepository;

  @Autowired PersonRepository personRepository;

  @Autowired ObjectMapper mapper;

  @Autowired private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  /** Book Entry and Person should not be duplicated. Only one instance by name is allowed. */
  @Test
  public void testCreateBook() throws Exception {
    BookEntryDTO bookEntryDTO =
        BookEntryDTO.builder()
            .bookName("test book")
            .author("test")
            .publishDate(LocalDate.now())
            .build();
    PersonDTO personDTO = PersonDTO.of("Test User", "test@test.com");
    BookItemDTO bookItemDTO =
        BookItemDTO.builder()
            .serialNumber("1234")
            .bookInfo(bookEntryDTO)
            .owner(personDTO)
            .status(BookStatus.AVAILABLE)
            .build();

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
    assertThat(bookEntryRepository.findAll().get(0).getNumberOfBookItems()).isEqualTo(3);
    assertThat(bookItemRepository.findAll().size()).isEqualTo(3);
    assertThat(personRepository.findAll().size()).isEqualTo(1);
  }

  @Test
  public void testBookItemsByBookEntry() throws Exception {
    BookEntry entry = BookEntry.of("test book", "test author", LocalDate.now());
    Person owner = Person.of("test name", "test@email.com");
    BookItem item = BookItem.of(entry, owner, "123456");
    entry = bookEntryRepository.save(entry);
    personRepository.save(owner);
    bookItemRepository.save(item);

    BookEntry entry2 = BookEntry.of("another testy book name", "test author", LocalDate.now());
    BookItem item2 = BookItem.of(entry2, owner, "789456");
    bookEntryRepository.save(entry2);
    personRepository.save(owner);
    bookItemRepository.save(item2);

    MvcResult result =
        mockMvc
            .perform(get(String.format("%s/%s/items", apiPath, entry.getId().toString())))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andReturn();

    JsonNode responseDTOs =
        mapper
            .readTree(result.getResponse().getContentAsString())
            .get("_embedded")
            .get("bookItemDTOList");
    assertThat(responseDTOs.toString()).doesNotContain(entry2.getBookName());

    BookItemDTO itemDTO = mapper.readValue(responseDTOs.get(0).toString(), BookItemDTO.class);
    assertThat(itemDTO.getLinks("borrow")).isNotNull();
    assertThat(itemDTO.getBookInfo().getBookName()).isEqualTo(entry.getBookName());
  }
}
