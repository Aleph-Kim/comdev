package com.sbs.exam.demo.repository;

import com.sbs.exam.demo.vo.Board;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BoardRepository {
    @Select("""
            SELECT *
            FROM board AS B
            WHERE B.id = #{id};
            """)
    Board getBoard(@Param("id") int id);
}
