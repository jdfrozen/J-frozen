package job;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @author: Frozen
 * @create: 2019-04-22 17:24
 * @description:
 **/
public class JobDemo {

    private static CoordinatorRegistryCenter createRegistryCenter() {

        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration("127.0.0.1:2181", "elastic-job-frozen");
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        regCenter.init();
        return regCenter;
    }

    private static LiteJobConfiguration createJobConfiguration() {

        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("jobdemo", "0/5 * * * * ?", 3)
                .shardingItemParameters("0=A,1=A,2=B").failover(true).misfire(true).build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig,
                MyElasticJob.class.getCanonicalName());
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true)
                .build();
        return simpleJobRootConfig;
    }

    public static void main(String[] args) {
        new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
    }
}
