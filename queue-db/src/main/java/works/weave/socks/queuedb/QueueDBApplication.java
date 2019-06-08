package works.weave.socks.queuedb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QueueDBApplication {
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(QueueDB.class, args);
	}
}
