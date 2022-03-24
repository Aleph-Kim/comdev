package com.comdev.exam.demo.service;

import com.comdev.exam.demo.repository.MemberRepository;
import com.comdev.exam.demo.util.Ut;
import com.comdev.exam.demo.vo.Member;
import com.comdev.exam.demo.vo.ResultData;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ResultData<Member> join(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
            String email) {

        Member member = SearchUserLoginId(loginId);
        if (member != null) {
            return ResultData.from("F-7", Ut.f("%s는 이미 사용중인 아이디 입니다.", loginId));
        }
        memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);

        return ResultData.from("S-1", Ut.f("환영합니다 %s님, 회원가입에 성공했습니다.", loginId), "member", member);
    }

    public int LastInsertId() {
        return memberRepository.LastInsterId();
    }

    public Member SearchUserLoginId(String loginId) {
        return memberRepository.SearchUserLoginId(loginId);
    }

    public Member searchUserId(int id) {
        return memberRepository.searchUserId(id);
    }

    public ResultData<Member> doModify(int id, String password, String name, String nickname,
            String cellphoneNo, String email) {

        memberRepository.modify(id, password, name, nickname, cellphoneNo, email);

        return ResultData.from("S-1", "회원정보가 수정 되었습니다.");
    }
}