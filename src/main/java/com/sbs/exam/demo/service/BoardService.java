package com.sbs.exam.demo.service;

import com.sbs.exam.demo.repository.BoardRepository;
import com.sbs.exam.demo.vo.Board;

import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {

        this.boardRepository = boardRepository;
    }

    public Board getBoard(int boardId) {
        return boardRepository.getBoard(boardId);
    }

}
