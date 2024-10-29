package com.javamajor.backend.Repository;

import com.javamajor.backend.Entity.Category;
import com.javamajor.backend.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionsRepository extends JpaRepository<Question,Integer> {
    List<Question> findAllByCategory(Category category);
}
