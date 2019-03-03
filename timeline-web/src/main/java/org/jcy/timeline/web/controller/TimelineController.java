package org.jcy.timeline.web.controller;

import org.jcy.timeline.web.model.FetchRequest;
import org.jcy.timeline.web.model.FetchResponse;
import org.jcy.timeline.web.model.RegisterRequest;
import org.jcy.timeline.web.model.RegisterResponse;
import org.jcy.timeline.web.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class TimelineController {

	@Autowired
	private TimelineService timelineService;

	/**
	 * Start auto update a specific timeline.
	 *
	 * @param principal the name of principal is the registered session id.
	 */
	@MessageMapping("/startAutoUpdate")
	public void startAutoUpdate(Principal principal) {
		if (principal != null) {
			timelineService.startAutoUpdate(principal.getName());
		}
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/**
	 * Register a timeline.
	 *
	 * @param request request
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public RegisterResponse register(@RequestBody RegisterRequest request, HttpSession session) {
		return timelineService.register(session.getId(), request.getUri(), request.getProjectName());
	}

	/**
	 * Fetch New Commits.
	 *
	 * @param request request with websocket session id.
	 */
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseBody
	public FetchResponse fetchNew(@RequestBody FetchRequest request) {
		return timelineService.fetchNew(request.getId());
	}

	/**
	 * Fetch More Commits.
	 *
	 * @param request request with websocket session id.
	 */
	@RequestMapping(value = "/more", method = RequestMethod.POST)
	@ResponseBody
	public FetchResponse fetchMore(@RequestBody FetchRequest request) {
		return timelineService.fetchMore(request.getId());
	}

}