package com.kathir.Inventory.Management.System.service;

import com.kathir.Inventory.Management.System.entity.Category;
import com.kathir.Inventory.Management.System.exception.ResourceNotFoundException;
import com.kathir.Inventory.Management.System.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}