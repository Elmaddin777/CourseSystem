package security.jwtwithdb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import security.jwtwithdb.entity.JwtRequest;
import security.jwtwithdb.entity.JwtResponse;
import security.jwtwithdb.service.JwtService;

@RequiredArgsConstructor
@RestController
public class JwtController {
    private final JwtService jwtService;

    @PostMapping("login")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest) throws Exception {

        return jwtService.createJwtToken(jwtRequest);
    }
}
