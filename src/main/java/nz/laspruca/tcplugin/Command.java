package nz.laspruca.tcplugin;

import javax.annotation.*;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	@Nonnull
	String name() default  "";
}
