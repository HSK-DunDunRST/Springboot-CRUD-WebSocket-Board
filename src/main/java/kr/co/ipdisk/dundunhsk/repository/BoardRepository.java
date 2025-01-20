package kr.co.ipdisk.dundunhsk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.ipdisk.dundunhsk.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

    Optional<Board> findByTableName(String tableName);
    Optional<Board> deleteByTableName(String tableName);
}
