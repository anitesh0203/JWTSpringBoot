package delegate;

import models.AuthenticationRequest;
import models.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import services.UserDetailsServiceImpl;
import utils.JwtUtil;

@RestController
public class GreetClass {

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/user")
    public String greetUser() {
        return "hello user";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> greetAdmin(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
                    authenticationRequest.getPassword()));
        }catch(BadCredentialsException ex) {
            throw new Exception("Incorrect username or password",ex);
        }

        final String jwt = jwtUtil.generateToken(userDetailsService.loadUserByUsername(authenticationRequest.getUserName()));
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
