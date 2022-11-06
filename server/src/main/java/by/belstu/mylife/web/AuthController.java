package by.belstu.mylife.web;

import by.belstu.mylife.entity.User;
import by.belstu.mylife.payload.request.LoginRequest;
import by.belstu.mylife.payload.request.SignupRequest;
import by.belstu.mylife.payload.response.JWTTokenSuccessResponse;
import by.belstu.mylife.payload.response.MessageResponse;
import by.belstu.mylife.security.JWTTokenProvider;
import by.belstu.mylife.security.SecurityConstants;
import by.belstu.mylife.services.MailService;
import by.belstu.mylife.services.UserService;
import by.belstu.mylife.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors))
            return errors;

        if (!userService.isActiveUser(loginRequest.getUsername()))
            return new ResponseEntity<>("User is not activated", HttpStatus.FORBIDDEN);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<Object> activateUser(@PathVariable String code) {
        if (userService.activateUser(code))
            return ResponseEntity.ok(new MessageResponse("User activated successfully"));
        return ResponseEntity.ok(new MessageResponse("User activation code not found"));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequest signupRequest, BindingResult result) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors))
            return errors;

        User user = userService.createUser(signupRequest);
        String message = String.format("%s, please activate your account: http://localhost:8080/api/auth/activate/%s",
                user.getUsername(), user.getActivationCode());
        mailService.sendMail(user.getEmail(), "Activation code", message);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
}
