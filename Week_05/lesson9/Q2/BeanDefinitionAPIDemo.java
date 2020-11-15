package lesson9.Q2;

import domian.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * BeanDefinition 方式
 *  1.命名
 *  2.使用 Spring bean 名称生成规则
 */
public class BeanDefinitionAPIDemo {
    public static void main(String[] args) {

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder
                .addPropertyValue("id", System.currentTimeMillis())
                .addPropertyValue("name", "cxq")
                .addPropertyValue("desc"," BeanDefinitionBuilder 方式");

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.registerBeanDefinition("cxq", beanDefinitionBuilder.getBeanDefinition());
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), applicationContext);
        applicationContext.refresh();
        System.out.println(applicationContext.getBeansOfType(User.class));
        applicationContext.close();

    }
}
