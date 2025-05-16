package fun.lizard.texas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.SocketTimeoutException;

@Controller
public class ExceptionHandlingController {

    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    @ExceptionHandler({SocketTimeoutException.class})
    public String getApiTimeoutErrorPage(ModelMap modelMap) {
        return "timeout";
    }
}
