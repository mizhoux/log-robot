package xyz.mizhoux.logrobot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * SimpleController
 *
 * @author 之叶
 * @date   2019/10/20
 */
@RestController
public class SimpleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/divide/{a}/{b}")
    public int divide(@PathVariable int a, @PathVariable int b) {
        logger.info("SimpleController.divide start, a = {}, b = {}", a, b);

        try {
            return a / b;
        } catch (Exception ex) {
            logger.error("SimpleController.divide start, a = {}, b = {}", a, b, ex);
        }

        return Integer.MIN_VALUE;
    }
}
