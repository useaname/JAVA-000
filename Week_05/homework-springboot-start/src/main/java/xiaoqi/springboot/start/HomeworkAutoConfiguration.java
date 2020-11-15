package xiaoqi.springboot.start;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Homework 自动装配
 */
@Configuration
@ConditionalOnProperty(prefix = "homework", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnResource(resources = "META-INF/spring.factories")
@ConditionalOnNotWebApplication
@ConditionalOnExpression("${homework.enabled:true}")
public class HomeworkAutoConfiguration {

    /**
     * ISchool Bean
     * @return
     */
    @Bean
    public ISchool defaultSchool() {
        return new School();
    }

    /**
     * Student Bean
     * @return
     */
    @Bean
    public Student defaultStudent() {
        return new Student();
    }

    /**
     * Klass Bean
     * @return
     */
    @Bean
    public Klass defaultKlass() {
        return new Klass();
    }

}
