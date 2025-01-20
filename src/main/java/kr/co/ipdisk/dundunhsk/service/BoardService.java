package kr.co.ipdisk.dundunhsk.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.co.ipdisk.dundunhsk.entity.Board;
import kr.co.ipdisk.dundunhsk.repository.BoardRepository;
import kr.co.ipdisk.dundunhsk.repository.DynamicRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardService {

    // 의존성 주입
    @Autowired
    private final BoardRepository boardRepository;

    @Autowired
    private final DynamicRepository dynamicRepository;
    
    // 게시판 전체 조회
    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    // 특정 게시판 조회 (parameter : tableName)
    public Optional<Board> findByTableName(String tableName) {
        return boardRepository.findByTableName(tableName);
    }

    // 게시판 생성 (parameter : tableName)
    public Board boardCreate(Board board){
        try {
            dynamicRepository.createDynamicBoard(board.getTableName());
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new IllegalArgumentException("게시판 생성에 실패하였습니다.");
        }
        return boardRepository.save(board);
    }

    // 게시판 수정
    public void modifyBoard(String tableName, String boardName){
        Optional<Board> findBoard = boardRepository.findByTableName(tableName);
        if (findBoard.isPresent()) {
            Board board = findBoard.get();
            board.setBoardName(boardName);
            boardRepository.save(board);
        } else {
            throw new IllegalArgumentException("해당 게시판을 찾을 수 없습니다.");
        }
    }
    
    // 게시판 삭제 (parameter : tableName)
    @Transactional
    public void boardDelete(String tableName){
        try {
            dynamicRepository.deleteDynamicBoard(tableName);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new IllegalArgumentException("게시판 삭제에 실패하였습니다");
        }
        boardRepository.deleteByTableName(tableName);
    }
}
