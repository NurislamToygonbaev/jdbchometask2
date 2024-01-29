package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Job {
    private Long id;
    private String position;
    private String profession;
    private String description;
    private int experience;
    private int employeeId;

    public Job(String position, String profession, String description, int experience, int employeeId) {
        this.position = position;
        this.profession = profession;
        this.description = description;
        this.experience = experience;
        this.employeeId = employeeId;
    }
}
