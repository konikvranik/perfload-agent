package com.mgmtp.perfload.agent.util;


import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.text.StrBuilder;
import org.objectweb.asm.Type;

import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static org.apache.commons.lang.StringUtils.substringAfterLast;
import static org.apache.commons.lang.StringUtils.substringBeforeLast;

/**
 * Utility class for class names.
 *
 * @since 1.3.0
 * @author rnaegele
 */
public class ClassNameUtils {
	public static String computeFullyQualifiedMethodName(final String className, final String methodName, final Type[] argumentTypes) {
		StrBuilder sb = new StrBuilder(50);
		sb.append(abbreviatePackageName(className));
		sb.append('.');
		sb.append(methodName);
		sb.append('(');
		for (int i = 0; i < argumentTypes.length; ++i) {
			sb.appendSeparator(", ", i);
			sb.append(abbreviatePackageName(argumentTypes[i].getClassName()));
		}
		sb.append(')');
		return sb.toString();
	}

	public static String abbreviatePackageName(String className) {
		if (className.contains(".")) {
			String packageName = substringBeforeLast(className, ".");
			String simpleClassName = substringAfterLast(className, ".");
			List<String> list = from(Splitter.on('.').splitToList(packageName)).transform(new Function<String, String>() {
				@Override
				public String apply(String input) {
					return input.substring(0, 1);
				}
			}).toList();
			return Joiner.on('.').join(list) + '.' + simpleClassName;
		} else {
			return className;
		}
	}
}
