package com.core.carryOn.member.domain;

import com.core.carryOn.Location.domain.Location;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

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
    private String email;

    @Field("nickname")
    private String nickname;

    @Field("password")
    private String password;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("last_login_at")
    private LocalDateTime lastLoginAt;

    @OneToMany(mappedBy = "recently_searched")
    private List <Location> recentlySearched;
}
