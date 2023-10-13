package com.water;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class WaterBackendApplicationTests {

    /**
     * 根据时间戳生成唯一id
     */
    @Test
    public void test() {
        // 创建Snowflake实例
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);

        // 生成唯一ID
        for (int i = 0;i < 5;i++) {
            long id = snowflake.nextId();

            // 将ID转换为字符串形式，并在前面补零，使其长度达到14位
            String idString = String.format("%5d", id);
            System.out.println(idString);
        }
    }


}
