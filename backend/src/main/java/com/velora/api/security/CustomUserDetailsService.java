package com.velora.api.security;
import com.velora.api.repository.UserRepository; import org.springframework.security.core.userdetails.*; import org.springframework.stereotype.Service; import org.springframework.security.core.authority.SimpleGrantedAuthority; import java.util.List;
@Service public class CustomUserDetailsService implements UserDetailsService {
 private final UserRepository users; public CustomUserDetailsService(UserRepository users){this.users=users;}
 @Override public UserDetails loadUserByUsername(String email){var u=users.findByEmail(email.toLowerCase()).orElseThrow(()->new UsernameNotFoundException("User not found")); return new org.springframework.security.core.userdetails.User(u.getEmail(),u.getPasswordHash(),List.of(new SimpleGrantedAuthority("ROLE_"+u.getRole().name())));}
}
