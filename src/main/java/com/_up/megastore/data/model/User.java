package com._up.megastore.data.model;

import com._up.megastore.data.enums.Role;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String fullName;

    @NonNull
    private String email;

    @NonNull
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Address> addresses = Collections.emptyList();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Order> orders = Collections.emptyList();

    private boolean deleted = false;

    @Id
    private final UUID userId = UUID.randomUUID();

    public static String convertPhoneNumber(String phoneNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "AR");
            return phoneUtil.format(numberProto, PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            throw new RuntimeException(e);
        }
    }
}