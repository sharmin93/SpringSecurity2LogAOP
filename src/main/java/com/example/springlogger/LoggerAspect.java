package com.example.springlogger;


import com.example.springlogger.controller.UserController;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
    static org.apache.log4j.Logger logger = Logger.getLogger(UserController.class);
}
