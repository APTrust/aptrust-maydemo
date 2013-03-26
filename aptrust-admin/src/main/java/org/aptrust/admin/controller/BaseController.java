package org.aptrust.admin.controller;

import org.aptrust.admin.exception.UninializedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @author Daniel Bernstein 
 * 
 */
public class BaseController {

    protected static final String INSTITUTION_ROOT_PATH = "/html/{institutionId}";

    @ExceptionHandler(value=UninializedException.class)
    public ModelAndView handleException (UninializedException ex) {
      ModelAndView mav = new ModelAndView("exception");
      String message = ex.getMessage();
      mav.addObject("description", message);
      return mav;
    }

}
