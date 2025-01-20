package kr.co.ipdisk.dundunhsk.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import kr.co.ipdisk.dundunhsk.entity.PostDTO;

@Service
public class PostService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PostDTO getPost(String boardTable, Long postId) {
        // 추후에 테이블 접두사 설정 시 변경될 부분
        String searchPostQuery = String.format("SELECT * FROM %s WHERE id = ?", boardTable);
        
        return jdbcTemplate.queryForObject(searchPostQuery, new BeanPropertyRowMapper<>(PostDTO.class), postId);
    }
}
