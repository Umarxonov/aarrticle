package com.example.article.payload;


import com.example.article.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    private String name;

    private boolean active;
    private CategoryDto parentDto;
    private Category parent;
}
