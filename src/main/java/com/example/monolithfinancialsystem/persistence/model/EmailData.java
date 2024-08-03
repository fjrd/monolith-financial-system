package com.example.monolithfinancialsystem.persistence.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Accessors(chain = true)
@Table(name = "email_data")
public class EmailData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 200)
    @Column(name = "email", length = 200)
    private String email;

}
