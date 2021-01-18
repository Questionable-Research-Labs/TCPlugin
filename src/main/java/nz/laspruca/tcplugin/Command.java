package nz.laspruca.tcplugin;

import javax.annotation.*;
import java.lang.annotation.*;

/**
 * Annotation that tells java the command that any CommandExecutor is responsible for
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	@Nonnull
	String name() default  "";
}
