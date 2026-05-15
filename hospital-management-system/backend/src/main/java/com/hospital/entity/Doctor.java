package com.hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Doctor extends User {
    
    @Column(name = "license_number", unique = true)
    private String licenseNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    
    @NotBlank(message = "Specialization is required")
    @Column(nullable = false)
    private String specialization;
    
    @Column(name = "sub_specialization")
    private String subSpecialization;
    
    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;
    
    @Column(name = "consultation_fee")
    private Double consultationFee;
    
    @Column(name = "follow_up_fee")
    private Double followUpFee;
    
    @Column(columnDefinition = "TEXT")
    private String qualifications;
    
    @Column(columnDefinition = "TEXT")
    private String bio;
    
    @ElementCollection
    @CollectionTable(name = "doctor_languages", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "language")
    private List<String> languages = new ArrayList<>();
    
    @Column(name = "rating")
    private Double rating = 0.0;
    
    @Column(name = "total_ratings")
    private Integer totalRatings = 0;
    
    @Column(name = "is_available_for_online")
    private Boolean isAvailableForOnline = false;
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AvailabilitySlot> availabilitySlots = new ArrayList<>();
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prescription> prescriptions = new ArrayList<>();
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalRecord> medicalRecords = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "doctor_status")
    private DoctorStatus status = DoctorStatus.ACTIVE;
    
    public enum DoctorStatus {
        ACTIVE, ON_LEAVE, RETIRED, SUSPENDED
    }
}
