package liubomyr.stepanenko.bookstore;

import java.math.BigDecimal;
import liubomyr.stepanenko.bookstore.model.Book;
import liubomyr.stepanenko.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("The Book");
            book.setAuthor("Ryan Gosling");
            book.setIsbn("ISBN 5-02-013850-1");
            book.setPrice(BigDecimal.TEN);

            Book secondBook = new Book();
            secondBook.setTitle("Amazing Stories For Kids");
            secondBook.setAuthor("Ann Hathaway");
            secondBook.setIsbn("ISBN 5-02-013850-2");
            secondBook.setPrice(BigDecimal.valueOf(12));

            bookService.save(book);
            bookService.save(secondBook);

            System.out.println(bookService.findAll());
        };
    }

}
