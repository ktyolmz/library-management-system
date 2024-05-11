package com.ktyolmz.library.service;

import com.ktyolmz.library.entity.Location;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {
    Location getLocation(Long bookId);
}
