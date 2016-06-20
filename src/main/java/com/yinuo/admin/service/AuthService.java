package com.yinuo.admin.service;

import com.yinuo.admin.repository.dao.StaffDao;
import com.yinuo.admin.repository.po.Staff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2016-05-10
 */
@Service
public class AuthService implements UserDetailsService, Serializable {
    static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    StaffDao staffDao;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("you have got username {} to authenticate", username);
        final Staff staff = staffDao.findByUsername(username);
        if (staff == null) {
            throw new UsernameNotFoundException(String.format("Staff %s does not exist!", username));
        }
        final List<String> authorities = staffDao.findAuthoritiesByUid(staff.getId());
//        if (authorities.stream().filter(auth -> "ROLE_ADMIN".equals(auth)).count() == 0) {
//            throw new UsernameNotFoundException("Admin system only allows ADMIN role to access");
//        }
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities.stream().map(auth -> (GrantedAuthority) () -> auth).collect(Collectors.toList());
            }

            @Override
            public String getPassword() {
                return staff.getPasswordHash();
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
