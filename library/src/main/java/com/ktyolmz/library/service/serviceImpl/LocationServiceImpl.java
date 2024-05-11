package com.ktyolmz.library.service.serviceImpl;

import com.ktyolmz.library.entity.Location;
import com.ktyolmz.library.exception.BookException;
import com.ktyolmz.library.repository.LocationRepository;
import com.ktyolmz.library.service.BookService;
import com.ktyolmz.library.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final BookService bookService;
    @Override
    public Location getLocation(Long bookId) {

        if (!bookService.existById(bookId)){
            throw new BookException("Can not find book");
        }

         return locationRepository.findByBookId(bookId).orElseThrow(
                () -> new BookException("Book location can not find."));
    }
}
