package com.example.monolithfinancialsystem.persistence.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 500)
    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Size(min = 8, max = 500)
    @NotNull
    @Column(name = "password", nullable = false, length = 500)
    private String password;

}
