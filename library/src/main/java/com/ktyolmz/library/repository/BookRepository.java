package com.ktyolmz.library.repository;

import com.ktyolmz.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByTitleIn(List<String> titleList);

    boolean existsByTitleIn(Set<String> titleList);

}
