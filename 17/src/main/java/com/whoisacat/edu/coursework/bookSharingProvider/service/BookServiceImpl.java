package com.whoisacat.edu.coursework.bookSharingProvider.service;

import com.whoisacat.edu.coursework.bookSharingProvider.domain.Author;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Book;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.Genre;
import com.whoisacat.edu.coursework.bookSharingProvider.domain.User;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookAndUserDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.BookDetailDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.dto.VisitingPlaceDTO;
import com.whoisacat.edu.coursework.bookSharingProvider.repository.BookRepository;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHOBookAlreadyExists;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHOBookNotFoundException;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHODataAccessException;
import com.whoisacat.edu.coursework.bookSharingProvider.service.exception.WHOSameHoldersException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService{

    private final BookRepository repository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final VisitingPlaceService visitingPlaceService;
    private final UserService userService;

    public BookServiceImpl(BookRepository repository, AuthorService authorService,
            GenreService genreService, VisitingPlaceService visitingPlaceService,
            UserService userService){
        this.repository = repository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.visitingPlaceService = visitingPlaceService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Book addBook(String bookString,String authorString,String genreString){
        Author author = authorService.findByNameOrCreate(authorString);
        Genre genre = genreService.findByNameOrCreate(genreString);
        List<Book> existedBooks = repository.findByTitleContainsAndAuthorIdAndGenreId(bookString,author.getId(),genre.getId());
        if(!existedBooks.isEmpty()){
            throw new WHOBookAlreadyExists();
        }
        Book book = repository.save(new Book(null,bookString,author,genre));
        if(book == null){
            throw new WHODataAccessException("bookDaoInsertHasReturnedNull");
        }
        return book;
    }

    @Override
    public long getBooksCount(){
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findByAuthorId(long id){
        return repository.getByAuthorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long id){
        return repository.findById(id);
    }

    @Override
    public Book update(BookDTO dto){
        Author author = new Author(dto.getAuthorId(),dto.getAuthorTitle());
        author = authorService.update(author);
        Genre genre = new Genre(dto.getGenreId(),dto.getGenreTitle());
        genre = genreService.update(genre);

        Book book = new Book(dto.getId(),dto.getTitle(),author,genre);
        return repository.save(book);
    }

    @Override
    public void delete(long id){
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override public Page<BookAndUserDTO> findOtherPeoplesBooksInUsersCities(Pageable pageable, String text) {
        String email = userService.getUsernameFromSecurityContext();
        return repository.getBooks(pageable, email, text, false);
    }

    @Transactional(readOnly = true)
    @Override public Page<BookAndUserDTO> findOwnBooks(PageRequest pageable, String text) {
        String email = userService.getUsernameFromSecurityContext();
        return repository.getBooks(pageable, email, text, true);
    }

    @Override public Page<BookDTO> findOwnBooks(PageRequest pageable) {
        String email = userService.getUsernameFromSecurityContext();
        return repository.getOwnBooks(email, pageable);
    }

    @Override public BookDetailDTO findBookDetailInfo(Long bookId) {
        User user = userService.getCurrentUser();
        Book book = repository.getById(bookId);
        List<VisitingPlaceDTO> places = visitingPlaceService.toDTO(user.getVisitingPlaces());
        return new BookDetailDTO(book.getId(), book.getTitle(), book.getAuthor().getTitle(),
                book.getGenre().getTitle(), String.join(" ", user.getFirstName(), user.getLastName()),
                user.getEmail(), places);
    }

    @Transactional
    @Override public void takeABookingRequest(Long id) {
        User newHolder = userService.getCurrentUser();
        Book book = repository.findById(id).orElseThrow(WHOBookNotFoundException::new);
        User previousHolder = userService.getByBook(book);
        if (previousHolder.getId().equals(newHolder.getId())) {
            throw new WHOSameHoldersException();
        }
        previousHolder.getBooks().remove(book);
        userService.save(previousHolder);
        newHolder.getBooks().add(book);
        userService.save(newHolder);
    }
}
