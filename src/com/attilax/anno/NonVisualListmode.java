package com.attilax.anno;
 

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Marker annotation for properties which are non-visual, and so should not appear (by default) inside a {@link
 * BeanModel}. The annotation may be placed on either the getter or the setter method or on the field.
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RUNTIME)
@Documented

public @interface NonVisualListmode {

}
