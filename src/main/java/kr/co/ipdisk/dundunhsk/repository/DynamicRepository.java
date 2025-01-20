package kr.co.ipdisk.dundunhsk.repository;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class DynamicRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    // 동적 테이블 - 게시판 생성
    public void createDynamicBoard(String boardTableName) throws SQLException{
        try {
            // 게시글 저장을 위한 게시판 테이블 생성
            String createTableQuery = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "board_id BIGINT NOT NULL, " +
                "post_subject VARCHAR(255) NOT NULL, " +
                "post_content TEXT NOT NULL, " +
                "post_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "post_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                "create_user VARCHAR(70), " +
                "view_count INT DEFAULT 0, " +
                "post_link1 TEXT, " +
                "post_link2 TEXT, " +
                "post_link3 TEXT, " +
                "post_isPinned BOOLEAN DEFAULT FALSE, " +
                "FOREIGN KEY (board_id) REFERENCES board_data(id) ON DELETE CASCADE" +
                ");", "tbl_" + boardTableName ); // `boardTableName`은 테이블 이름이 들어갈 변수 (tbl_***)
                jdbcTemplate.execute(createTableQuery); // SQL Query 실행
        } catch (Exception exception) { // 예외 처리: 로그를 기록하거나 사용자에게 알림
            exception.printStackTrace(); // 콘솔에 오류 출력 (개발 중에는 유용할 수 있음)
            throw new SQLException("[Dynamic_Repo]>> Table 생성에 실패하였습니다.");
        }
    }

    // 동적 테이블 - 게시판 삭제
    public void deleteDynamicBoard(String boardTableName) throws SQLException{
        // * Debug 전용 출력문
        // System.out.println("Delete boardTableName: " + boardTableName);
        try {
            // 테이블 이름을 동적으로 생성 (SQL Injection 방지용으로 백틱 사용)
            String deleteTableQuery = String.format(
            "DROP TABLE IF EXISTS `%s`;", "tbl_" + boardTableName);
            // SQL 실행
            jdbcTemplate.update(deleteTableQuery);
            // * Debug 전용 출력문
            System.out.println("SQL Execute PASS!");
        } catch (Exception exception) {
            // * Debug 전용 출력문
            System.out.println("[Dynamic_Repo]>> Exception 발생: " + exception.getMessage());
            exception.printStackTrace();
            throw new SQLException("[Dynamic_Repo]>> Table 삭제에 실패하였습니다.");
        }
    }
    
    // 동적 테이블 - 채팅방 생성
    public void createDynamicChatRoom(String chatTableName) throws SQLException{
        // 채팅방 데이터 테이블 생성 쿼리문
        try {
            String createTableQuery = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                "chat_id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "room_id BIGINT NOT NULL, " +
                "sender_name VARCHAR(255) NOT NULL, " +
                "send_message TEXT NOT NULL, " +
                "send_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (room_id) REFERENCES room_data(room_id) ON DELETE CASCADE" +
                ");", "chat_" + chatTableName);
            // 쿼리문 실행 시도
            jdbcTemplate.execute(createTableQuery); // SQL Query 실행
        } catch (Exception exception) {
            // * Debug 전용 출력문
            System.out.println("[Dynamic_Repo]>> Exception 발생: " + exception.getMessage());
            exception.printStackTrace();
            throw new SQLException("[Dynamic_Repo]>> Table 생성에 실패하였습니다.");
        }
    }


}