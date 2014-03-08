/*
 * Copyright 2014 Zoltán Reegn zoltan.reegn@gmail.com
 * All rights reserved.
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
 */
package struts.cdi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.chain.commands.servlet.CreateAction;
import org.apache.struts.chain.commands.util.ClassUtils;
import org.apache.struts.chain.contexts.ActionContext;

public class CreateCdiAction extends CreateAction {
	
	private static final Log log = LogFactory.getLog(CreateCdiAction.class);
	
    protected Action createAction(ActionContext context, String type) throws Exception {
        log.info("Initialize action of type: " + type);
        Class<?> cls = ClassUtils.getApplicationClass(type);
        return (Action) BeanLookup.lookup(cls);
    }
}
