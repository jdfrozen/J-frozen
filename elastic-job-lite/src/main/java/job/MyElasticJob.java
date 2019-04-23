package job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * @author: Frozen
 * @create: 2019-04-22 17:22
 * @description:
 **/
public class MyElasticJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        switch (shardingContext.getShardingItem()) {
            case 0: {
                System.out.println("当前分片：" + shardingContext.getShardingItem() + "=====" + "参数："
                        + shardingContext.getShardingParameter() + " =====" + Thread.currentThread());
                break;
            }
            case 1: {
                System.out.println("当前分片：" + shardingContext.getShardingItem() + "=====" + "参数："
                        + shardingContext.getShardingParameter() + " =====" + Thread.currentThread());
                break;
            }
            case 2: {
                System.out.println("当前分片：" + shardingContext.getShardingItem() + "=====" + "参数："
                        + shardingContext.getShardingParameter() + " =====" + Thread.currentThread());
                break;
            }
            default: {
                System.out.println("当前分片：" + shardingContext.getShardingItem() + "=====" + "参数："
                        + shardingContext.getShardingParameter() + " =====" + Thread.currentThread());
                break;
            }
        }
    }
}
