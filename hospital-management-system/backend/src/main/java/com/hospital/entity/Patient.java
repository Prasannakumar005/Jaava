package com.hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;
    
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String phone;
    
    @Column(name = "emergency_contact")
    private String emergencyContact;
    
    @Column(name = "emergency_phone")
    private String emergencyPhone;
    
    @Embedded
    private Address address;
    
    @Column(name = "blood_group")
    private String bloodGroup;
    
    @ElementCollection
    @CollectionTable(name = "patient_allergies", joinColumns = @JoinColumn(name = "patient_id"))
    @Column(name = "allergy")
    private Set<String> allergies = new HashSet<>();
    
    @ElementCollection
    @CollectionTable(name = "patient_chronic_conditions", joinColumns = @JoinColumn(name = "patient_id"))
    @Column(name = "condition")
    private Set<String> chronicConditions = new HashSet<>();
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalRecord> medicalRecords = new ArrayList<>();
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prescription> prescriptions = new ArrayList<>();
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Admission> admissions = new ArrayList<>();
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Billing> bills = new ArrayList<>();
    
    @Column(name = "insurance_provider")
    private String insuranceProvider;
    
    @Column(name = "insurance_policy_number")
    private String insurancePolicyNumber;
    
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;
    
    @Column(name = "patient_id_number", unique = true)
    private String patientIdNumber;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
