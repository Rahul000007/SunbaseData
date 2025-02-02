package org.assignment.sunbasedataassignment.jwtService;

import org.assignment.sunbasedataassignment.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    public String generateToken(String username, String password) {

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("user not found");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        String token = jwtUtil.generateToken(userDetails);
        return token;
    }
}
