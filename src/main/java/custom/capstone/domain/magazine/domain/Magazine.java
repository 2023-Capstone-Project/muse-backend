package custom.capstone.domain.magazine.domain;

import custom.capstone.domain.admin.Admin;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Magazine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mgz_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "admin_code")
    private Admin admin;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private int views;
}
