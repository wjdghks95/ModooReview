package com.io.rol.security.token;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Rmemeber Me 관련 엔티티
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersistentLogins {

    @Id
    @Column(length = 64)
    private String series;

    @Column(length = 64)
    private String username;

    @Column(length = 64)
    private String token;

    @Column(name = "last_used", length = 64)
    private Date lastUsed;
}
