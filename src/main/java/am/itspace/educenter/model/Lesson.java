package am.itspace.educenter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue
    @Column
    private int id;
    @Column
    private String name;
    @Column(name = "lecturer_name")
    private String lecturerName;
    @Column
    private double duration;
    @Column
    private double price;
    @ManyToMany(mappedBy = "lessons")
    private List<User> users;
}
