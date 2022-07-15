package nure.knt.aop.db.logging;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Aspect
@PropertySource("classpath:application.properties")
public class LoggingScriptForDataBase {
    private final boolean isWork;

    public LoggingScriptForDataBase(@Value("${mysql.show.script}") boolean isWork) {
        this.isWork = isWork;
    }


    @AfterReturning(pointcut = "execution(String concatScripts(String...))", returning ="str")
    public void showConcatScript(String str){
        if(isWork){
            System.out.println(str);
        }
    }
}
