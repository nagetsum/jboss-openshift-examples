package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OomController {
    @RequestMapping("/oom")
    public String oom() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100000000; i++) {
            list.add("message" + i);
        }
        return "Greetings from Spring Boot!";
    }
}
