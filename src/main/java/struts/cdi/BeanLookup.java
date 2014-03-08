/*
 * Copyright 2014 Zoltán Reegn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package struts.	cdi;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

public class BeanLookup {

	public static <T> T lookup(Class<T> cls, Annotation... annotations) {
		BeanManager bm = BeanManagerLookup.getBeanManager();
		return new BeanLookup(bm).lookupInstance(cls, annotations);
	}

	private final BeanManager bm;

	BeanLookup(BeanManager bm) {
		this.bm = bm;
	}

	@SuppressWarnings("unchecked")
	<T> T lookupInstance(Class<T> cls, Annotation... annotations) {
		Set<Bean<?>> beans = bm.getBeans(cls, annotations);
		Bean<?> bean = bm.resolve(beans);
		CreationalContext<?> context = bm.createCreationalContext(bean);
		return (T) bm.getReference(bean, cls, context);
	}
}
