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
@Table(name = "phone_data")
public class PhoneData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 13)
    @Column(name = "phone", length = 13)
    private String phone;

}
