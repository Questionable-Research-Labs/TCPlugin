package nz.laspruca.tcplugin;

import reactor.util.annotation.*;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	@NonNull
	String name() default  "";
}
