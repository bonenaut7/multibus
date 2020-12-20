package by.fxg.multibus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Post {
	public Class<? extends BusMail> mail() default BusMail.class;
	public boolean receiveCanceled() default false;
}
