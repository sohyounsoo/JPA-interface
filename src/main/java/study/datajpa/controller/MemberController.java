package study.datajpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 스프링 부트가 도메인 클래스 컨버터를 자동으로 지원해줌 하지만 실무에서는 권장하지 않음 pk값이 공개되기 때문에
    // 조회용으로만 사용해야 되는 제한이 있음
    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5, sort = "username") Pageable pageable) {
//        Page<Member> page = memberRepository.findAll(pageable);
//        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
//        return map;
        return memberRepository.findAll(pageable)
//                .map(member -> new MemberDto(member)); //메서드 레퍼런스 가능
                .map(MemberDto::new);
    }

    @PostConstruct
    public void init() {
        for(int i=0; i<10; i++) {
            memberRepository.save(new Member("user"+ i, i));
        }
    }
}
