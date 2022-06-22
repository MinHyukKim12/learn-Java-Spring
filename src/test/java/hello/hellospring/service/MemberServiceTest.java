package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    // 각 메소드들이 끝날때마다 실행되는 메소드
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore(); // db값 clear
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        // ctrl + alt + v : 객체 name = 생성
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));// 왼쪽 IllegalStateException 예외가 터져야 함
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        // try-catch를 쓸 수도 있지만 애매(?) 함
//        try {
//            memberService.join(member2); // 똑같은 spring 이름이 중복이므로 예외가 떠야함
//            fail();
//        }catch (IllegalStateException e){
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }


        //then

    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}