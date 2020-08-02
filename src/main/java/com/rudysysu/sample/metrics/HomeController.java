package com.rudysysu.sample.metrics;

import com.rudysysu.sample.metrics.model.User;
import io.prometheus.client.Counter;
import io.prometheus.client.spring.web.PrometheusTimeMethod;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final static Logger LOGGER = Logger.getLogger(HomeController.class);

    private static final Counter user_register_counter = Counter.build()
            .name("user_counter")
            .labelNames("status")
            .help("Show user added and removed counts")
            .register();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @PrometheusTimeMethod(name = "home_controller_get_time", help = "time")
    public String home(Locale locale, Model model) {
        user_register_counter.labels("get").inc();
        LOGGER.info("Home Page Requested, locale = " + locale);
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);
        model.addAttribute("serverTime", formattedDate);

        return "home";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @PrometheusTimeMethod(name = "home_controller_post_time", help = "time")
    public String user(@Validated User user, Model model) {
        LOGGER.info("User Page Requested");

        user_register_counter.labels("add").inc();

        model.addAttribute("user", user);
        return "user";
    }
}
