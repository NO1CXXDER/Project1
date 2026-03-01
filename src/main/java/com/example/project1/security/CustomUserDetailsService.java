package com.example.project1.security;

import com.example.project1.entity.User;
import com.example.project1.entity.UserRepository;
import com.example.project1.exception.ApplicationException;
import com.example.project1.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service //이 클래스를 스프링의 서비스 빈으로 등록한다
@Transactional(readOnly = true) //트랜잭션이란 데이터베이스의 상태를 변화시키기 위해 수행하는 작업의 단위이다. SQL문을 이용해  데이터베이스에 접근하는 것을 의미한다. readOnly = true는 이 서비스의 모든 메서드가 데이터베이스를 읽기만 하고 변경하지 않는다는 것을 나타낸다. 이렇게 하면 트랜잭션 관리자가 최적화할 수 있다. 예를 들어, 데이터베이스 연결을 더 효율적으로 관리하거나, 읽기 전용 트랜잭션을 사용하여 성능을 향상시킬 수 있다.
@RequiredArgsConstructor //이 클래스의 모든 final 필드에 대한 생성자를 자동으로 생성한다. 예를 들어, private final UserRepository userRepository; 라는 필드가 있다면, @RequiredArgsConstructor는 public CustomUserDetailsService(UserRepository userRepository) { this.userRepository = userRepository; } 와 같은 생성자를 자동으로 만들어준다. 이렇게 하면 의존성 주입이 편리해진다.
public class CustomUserDetailsService implements UserDetailsService {//UserDetailsService 인터페이스를 구현하는 클래스이다. 스프링 시큐리티가 이해할 수 있게 만든다
    private final UserRepository userRepository;//UserRepository 인터페이스의 인스턴스를 주입받는다. 이 리포지토리는 데이터베이스에서 사용자 정보를 조회하는 데 사용된다.

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        return new CustomUserDetails(user);

    }
}
