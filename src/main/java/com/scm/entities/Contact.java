package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contact {

    @Id
    
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private String pictures;
    @Column(length = 1000)
    private String description;
    private boolean favourite=false;
    private String email;
    private String websiteLinks;
    private String linkdin;

    private String cloudnaryImagePublicId;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
private List<ScocialLinks> socialLinks = new ArrayList<>();


}
