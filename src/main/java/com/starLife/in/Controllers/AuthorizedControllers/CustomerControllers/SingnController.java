package com.starLife.in.Controllers.AuthorizedControllers.CustomerControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.starLife.in.Entity.CaptureCode;
import com.starLife.in.repository.CaptureRepository;


@Controller
public class SingnController {
    @Autowired
    private CaptureRepository captureRepository;

    @GetMapping("/signin")
    public String openSignInpage(Model m) {

        m.addAttribute("title", "Login page");

        return "CustomerLogin";
    }

    @GetMapping("/fintakebank")
    public String openHomepage() {
        return "fintakebank";
    }

    // method for failed login
    @GetMapping("/logininfailed")
    public String getMethodName(Model m) {
        CaptureCode capture2 = this.captureRepository.findByCaptureno(1);
        String capture = capture2.getCaptureCode();
        m.addAttribute("capture", capture);

        m.addAttribute("title", "login form");
        return "login";
    }

}
