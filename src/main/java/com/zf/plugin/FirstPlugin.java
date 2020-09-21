package com.zf.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.Properties;

/**
 * @author zhengfan
 * @create 2020-09-22 上午12:21
 *
 * Intercepts 完成插件签名   告诉mybatis 当前插件拦截那个对象 那个方法
 */
@Intercepts({
        @Signature(type = StatementHandler.class,method ="parameterize",args = Statement.class )
})
public class FirstPlugin  implements Interceptor {


    /**
     *     拦截目标对象的目标方法的执行
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("intercept方法" + invocation.getMethod().getName());
        //执行目标方法
        Object proceed = invocation.proceed();
        // 返回执行后的值
        return proceed;
    }

    /***
     *
     * @param target  目标对象   包装该对象就是为目标对象创建一个代理对象
     * @return
     */

    @Override
    public Object plugin(Object target) {
       //mybatis 的 wrap 方法 就是将当前Interceptor 包装我们目标对象
        System.out.println("plugin方法将要包装的对象" + target);
        Object wrap = Plugin.wrap(target, this);
        //返回 创建的 代理对象
        return wrap;
    }

    /***
     *  将插件在注册时的properties 设置进来
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的信息"+properties);
    }
}
