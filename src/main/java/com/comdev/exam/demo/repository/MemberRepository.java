package com.comdev.exam.demo.repository;

import com.comdev.exam.demo.vo.Member;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

        @Update("""
                        <script>
                                UPDATE `member`
                                <set>
                                        updateDate = NOW(),
                                        <if test="password != null">
                                        loginPw = #{password},
                                        </if>
                                        <if test="name != null">
                                        name = #{name},
                                        </if>
                                        <if test="nickname != null">
                                        nickname = #{nickname},
                                        </if>
                                        <if test="email != null">
                                        email = #{email},
                                        </if>
                                        <if test="cellphoneNo != null">
                                        cellphoneNo = #{cellphoneNo},
                                        </if>
                                </set>
                                WHERE id = #{id}
                        </script>
                                                """)
        public void modify(int id, String password, String name, String nickname,
                        String cellphoneNo, String email);
}
