package com.ktyolmz.library.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@AllArgsConstructor
public class BorrowBookRequest {
    private Long id;
    private List<String> titleList;
}
