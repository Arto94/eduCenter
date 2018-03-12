package am.itspace.educenter.repository;

import am.itspace.educenter.model.Lesson;
import am.itspace.educenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    List<Lesson> findAllByUsersIsContaining(User user);
}
