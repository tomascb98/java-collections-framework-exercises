package com.epam.rd.autotasks.collections.map;

import java.util.*;

public class BooksCatalog {
    private static final String EOL = "\n";
    private Map<Author, List<Book>> catalog;

    public BooksCatalog() {
        this.catalog = new TreeMap<>();
    }

    public BooksCatalog(Map<Author, List<Book>> catalog) {
        if(catalog == null) throw new NullPointerException();
        this.catalog = new TreeMap<>(catalog);
    }

    /**
     * Returns a List of books of the specified author.
     *
     * @param author the author of books to search for.
     * @return a list of books or {@code null}
     * if there is no such author in the catalog.
     */
    public List<Book> findByAuthor(Author author) {
        if(author == null) throw new NullPointerException();
        return catalog.getOrDefault(author, null);
    }

    /**
     * @return the string representation of all authors
     * separated by the current operating system {@code lineSeparator}.
     */
    public String getAllAuthors() {
        Set<Author> authors = catalog.keySet();
        StringBuilder sb = new StringBuilder();
        for (Author author: authors) {
            sb.append(author.toString()).append("\n");
        }
        return sb.substring(0, sb.length()-1);
    }

    /**
     * Searches for pairs of (author, book) by the book title.
     * The pair must be included in the resulting map if the
     * book title contains the specified string matched ignore case.
     * All authors of the book must be specified in the
     * book authors list.
     *
     * @param pattern the string to search for in the book title.
     * @return the map which contains all found books and their authors.
     * It must be sorted by titles of books, if the titles match,
     * by increasing cost.
     */
    public Map<Book, List<Author>> findAuthorsByBookTitle(String pattern) {
        if(pattern == null) throw new NullPointerException();
        Set<Map.Entry<Author, List<Book>>> setCatalog = this.catalog.entrySet();
        Map<Book, Set<Author>> authorsByCoincidence = new HashMap<>();


        for (Map.Entry<Author, List<Book>> authorBooks: setCatalog) {
            for (Book book : authorBooks.getValue()) {
               if(book.getTitle().toLowerCase().contains(pattern)){
                   if(authorsByCoincidence.containsKey(book)){
                       authorsByCoincidence.get(book).add(authorBooks.getKey());
                   } else {
                       Set<Author> setToAdd = new TreeSet<>();
                       setToAdd.add(authorBooks.getKey());
                       authorsByCoincidence.put(book, setToAdd);
                   }
                }
            }
        }

        Map<Book, List<Author>> authorsByBook = new TreeMap<>();
        for (Map.Entry<Book, Set<Author>> book: authorsByCoincidence.entrySet()) {
            authorsByBook.put(book.getKey(), new ArrayList<>(book.getValue()));
        }

        return authorsByBook;
    }

    /**
     * Searches for all books whose genre list contains the specified string.
     * The book must be included in the resulting list if at least
     * one genre of the book contains the specified pattern ignoring case.
     *
     * @param pattern the string to search for in the book genre list.
     * @return a set of books sorted using natural ordering.
     * @see Book class.
     */
    public Set<Book> findBooksByGenre(String pattern) {
        if(pattern == null) throw new NullPointerException();
        Set<Map.Entry<Author, List<Book>>> bookSetByAuthors = this.catalog.entrySet();
        Set<Book> booksByGenre = new TreeSet<>();
        for (Map.Entry<Author, List<Book>> booksByAuthor : bookSetByAuthors) {
            for(Book book : booksByAuthor.getValue()){
                for (String genre : book.getGenres()) {
                    if(genre.contains(pattern)){
                        booksByGenre.add(book);
                        break;
                    }
                }
            }
        }
        return booksByGenre;
    }

    /**
     * Searches for authors of the specified book.
     *
     * @param book the book.
     * @return a list of authors of the specified book.
     * @throws NullPointerException if the parameter is {@code null}
     */
    public List<Author> findAuthorsByBook(Book book) {
        if(book == null) throw new NullPointerException();
        Set<Map.Entry<Author, List<Book>>> bookSetByAuthors = this.catalog.entrySet();
        Set<Author> authorsByBook = new TreeSet<>();
        for (Map.Entry<Author, List<Book>> booksByAuthor : bookSetByAuthors) {
            for(Book currentBook : booksByAuthor.getValue()){
                if(currentBook.equals(book)){
                    authorsByBook.add(booksByAuthor.getKey());
                    break;
                }
            }
        }
        return List.copyOf(authorsByBook);
    }

    @Override
    public String toString() {
        Set<Map.Entry<Author, List<Book>>> bookSetByAuthors = this.catalog.entrySet();
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<Author, List<Book>> booksByAuthor : bookSetByAuthors) {
            sb.append(booksByAuthor.getKey().toString())
                    .append("=[");
            int i = 0;
            for (Book book : booksByAuthor.getValue()) {
                sb.append(book);
                if (i == booksByAuthor.getValue().size() - 1) sb.append("]");
                else sb.append(", ");
                i++;
            }
            sb.append(", ");
        }
        return sb
                .replace(sb.length() - 2 , sb.length(),"")
                .append("}").toString();
    }
}
