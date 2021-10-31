package site.heaven96.example;


import cn.beecp.boot.EnableDataSourceMonitor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDataSourceMonitor
public class ToolsExampleWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsExampleWebApplication.class, args);
    }

}
