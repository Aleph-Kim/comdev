package com.sbs.exam.demo.repository;

import com.sbs.exam.demo.vo.Member;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberRepository {

        @Insert("""
                        INSERT INTO `member`
                        SET regDate = NOW(),
                        updateDate = NOW(),
                        loginId = #{loginId},
                        loginPw = #{loginPw},
                        `name` = #{name},
                        nickname = #{nickname},
                        cellphoneNo = #{cellphoneNo},
                        email = #{email}
                        			""")
        public void join(@Param("loginId") String loginId, @Param("loginPw") String loginPw, @Param("name") String name,
                        @Param("nickname") String nickname, @Param("cellphoneNo") String cellphoneNo,
                        @Param("email") String email);

        @Select("Select Last_Insert_Id()")
        public int LastInsterId();

        @Select("Select * from Member where loginId = #{loginId}")
        public Member SearchUserLoginId(@Param("loginId") String loginId);

        @Select("Select * from member where id = #{id}")
        public Member searchUserId(@Param("id") int id);
}
