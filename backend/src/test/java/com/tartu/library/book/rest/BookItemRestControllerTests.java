package com.tartu.library.book.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tartu.library.LibraryApplication;
import com.tartu.library.book.application.dto.BookEntryDTO;
import com.tartu.library.book.application.dto.BookItemDTO;
import com.tartu.library.book.domain.model.BookEntry;
import com.tartu.library.book.domain.model.BookItem;
import com.tartu.library.book.domain.model.BookStatus;
import com.tartu.library.book.domain.model.BorrowLog;
import com.tartu.library.book.domain.repository.BookEntryRepository;
import com.tartu.library.book.domain.repository.BookItemRepository;
import com.tartu.library.book.domain.repository.BorrowLogRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LibraryApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BookItemRestControllerTests {
  private final String apiPath = "/api/book/item";

  @Autowired BookEntryRepository bookEntryRepository;

  @Autowired BookItemRepository bookItemRepository;

  @Autowired PersonRepository personRepository;

  @Autowired BorrowLogRepository borrowLogRepository;

  @Autowired ObjectMapper mapper;

  @Autowired private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void testBorrowBook() throws Exception {
    BookEntry entry = BookEntry.of("test book", "test author", LocalDate.now());
    Person owner = Person.of("test name", "test@email.com");
    BookItem item = BookItem.of(entry, owner, "123456");
    bookEntryRepository.save(entry);
    owner = personRepository.save(owner);
    item = bookItemRepository.save(item);

    MvcResult result =
        mockMvc
            .perform(
                patch(String.format("%s/%s/borrow", apiPath, item.getId().toString()))
                    .param("person_uuid", owner.getId().toString()))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andReturn();

    BookItemDTO responseDTO =
        mapper.readValue(result.getResponse().getContentAsString(), BookItemDTO.class);

    assertThat(responseDTO.getStatus()).isEqualTo(BookStatus.BORROWED);
    assertThat(responseDTO.getLinks("return")).isNotNull();

    assertThat(borrowLogRepository.findAll().size()).isEqualTo(1);
    BorrowLog log = borrowLogRepository.findAll().get(0);
    assertThat(log.getItem().getSerialNumber()).isEqualTo(item.getSerialNumber());
    assertThat(log.getBorrower()).isEqualTo(owner);
  }

  @Test
  public void testDeleteBookItem() throws Exception {
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

    // create book
    MvcResult bookItem =
        mockMvc
            .perform(
                post("/api/book")
                    .content(mapper.writeValueAsString(bookItemDTO))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful())
            .andReturn();
    BookItemDTO response_bookItemDTO =
        mapper.readValue(bookItem.getResponse().getContentAsString(), BookItemDTO.class);
    System.out.println(response_bookItemDTO);

    // delete book item
    mockMvc
        .perform(delete(apiPath + "/" + response_bookItemDTO.getId()))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());

    assertThat(bookEntryRepository.findAll().size()).isEqualTo(1);
    assertThat(bookItemRepository.findAll().size()).isEqualTo(0);
  }
}
