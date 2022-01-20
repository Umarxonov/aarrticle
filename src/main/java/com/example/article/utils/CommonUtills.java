package com.example.article.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CommonUtills {
    public static void validatePageNumberAndSize(int page, int size) throws IllegalAccessException {
        if (page < 0) {
            throw new IllegalAccessException("Sahifa soni noldan kam bo'lishi mumkin emas.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new IllegalAccessException("Sahifa soni " + AppConstants.MAX_PAGE_SIZE + " dan ko'p bo'lishi mumkin emas.");
        }
    }

    public static Pageable getPageableByCreatedAtDesc(int page, int size) throws IllegalAccessException {
        validatePageNumberAndSize(page, size);
        return PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    }

    public static Pageable getPageableByCreatedAtAsc(int page, int size) throws IllegalAccessException {
        validatePageNumberAndSize(page, size);
        return PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
    }

    public static Pageable getPageableByIdDesc(int page, int size) throws IllegalAccessException {
        validatePageNumberAndSize(page, size);
        return PageRequest.of(page, size, Sort.Direction.DESC, "id");
    }

    public static Pageable getPageableByIdAsc(int page, int size) throws IllegalAccessException {
        validatePageNumberAndSize(page, size);
        return PageRequest.of(page, size, Sort.Direction.ASC, "id");
    }

    public static Pageable simplePageable(int page, int size) throws IllegalAccessException {
        validatePageNumberAndSize(page, size);
        return PageRequest.of(page, size);
    }

    public static Timestamp getDateFromString(String date) throws ParseException {
       return new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime());

    }

}
