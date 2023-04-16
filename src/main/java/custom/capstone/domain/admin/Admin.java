package custom.capstone.domain.admin;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Admin {
    @Id @GeneratedValue
    @Column(name = "admin_code", length = 10)
    private String code;

    private Long id;

    @Column(length = 150)
    private String password;

    @Column(length = 20)
    private String name;

//    @Enumerated(EnumType.STRING)
//    private Auth auth;
}
