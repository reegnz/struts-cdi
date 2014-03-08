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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.apache.struts.util.RequestUtils;

public class TilesCdiRequestProcessor extends TilesRequestProcessor {

	@Override
	protected Action processActionCreate(HttpServletRequest request,
			HttpServletResponse response, ActionMapping mapping)
			throws IOException {
		String type = mapping.getType();
		Action action = null;
		synchronized (actions) {
			action = (Action) actions.get(mapping.getType());
			if (action == null) {
				try {
					action = createAction(type);
					action.setServlet(servlet);
				} catch (Exception e) {
					log.error(
							getInternal().getMessage("actionCreate",
									mapping.getPath()), e);
					response.sendError(
							HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							getInternal().getMessage("actionCreate",
									mapping.getPath()));
				}
			}
		}
		return action;
	}
	
	private Action createAction(String type) throws ClassNotFoundException {
		Class<?> cls = RequestUtils.applicationClass(type);
		return (Action) BeanLookup.lookup(cls);
	}
}
