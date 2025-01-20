package kr.co.ipdisk.dundunhsk.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Getter
@Setter
@Table(name = "board_data")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_name")
    private String tableName;
    
    @Column(name = "board_name")
    private String boardName;

    @Column(name = "board_description")
    private String boardDescription;

    @Column(name = "created_date")
    private LocalDate createdDate;

    // 엔터티가 저장될 때 현재 시간으로 설정
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now(); // 현재 시간 설정
    }
}