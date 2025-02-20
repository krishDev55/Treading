package com.trade.service;//package com.trade.service;
//
//import com.trade.modal.User;
//import com.trade.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CustomUserDetailService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Retrieve user from the repository based on email
//        User user = userRepository.findByEmail(username);
//
//        // If the user doesn't exist, throw an exception
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//
//        // Create authority based on the user's role
//        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList();
//
//        // Return the user details along with authorities (roles)
//        return new org.springframework.security.core.userdetails.User(user.getEmail(),
//                user.getPassword(), authorityList);
//    }
//}


import com.trade.domain.USER_ROLE;
import com.trade.modal.User;
import com.trade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	  System.out.println("User is2 "+username);
    	User user = userService.getUserByEmail(username);
        
        System.out.println("User is1 "+user);
        
        System.out.println("User is "+user);
        if (user == null) {
            throw new UsernameNotFoundException("user not found with email"+username);
        }
        USER_ROLE role = user.getRole();

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.toString()));


        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
