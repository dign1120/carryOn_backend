package com.core.carryOn.member.domain;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "member")
public class Member {
    @Id
    @Field("_id")
    private String id;

    @Field("provider")
    private String provider;

    @Field("provider_id")
    private String providerId;

    @Field("email")
    @Indexed(unique = true)
    private String email;

    @Field("nickname")
    @Indexed(unique = true)
    private String nickname;

    @Field("password")
    private String password;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("last_login_at")
    private LocalDateTime lastLoginAt;

    @Field("authority")
    private String authority;
}
