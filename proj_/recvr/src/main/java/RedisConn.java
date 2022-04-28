import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;

public class RedisConn {

	public static void main (String[] args) throws IOException {
		// 1. Create config object
		Config config = new Config();
		config.useClusterServers()
				.addNodeAddress("redis://127.0.0.1:7181");

// or read config from file
		config = Config.fromYAML(new File("config-file.yaml"));
// 2. Create Redisson instance

// Sync and Async API
		RedissonClient redisson = Redisson.create(config);

// Reactive API
		RedissonReactiveClient redissonReactive = redisson.reactive();

// RxJava3 API
		RedissonRxClient redissonRx = redisson.rxJava();
// 3. Get Redis based implementation of java.util.concurrent.ConcurrentMap
	}
}
