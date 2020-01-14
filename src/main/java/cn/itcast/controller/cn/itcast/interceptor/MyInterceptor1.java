package cn.itcast.controller.cn.itcast.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
public class MyInterceptor1 implements HandlerInterceptor{

    /**
     * 预处理，controller方法执行前
     * return true 放行，执行下一个拦截器，如果没有，执行controller中的方法
     * return false不放行,也就是不执行controller中的处理方法,直接中request,response来设置一个返回页面
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */

   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor1执行了...前1111");
        return true;
        //如果不放行的话,也就是controller的处理方法不执行,就用request,response来自己定义一个响应页面,如下

       /*System.out.println("MyInterceptor1执行了...前1111");
       request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response);
       return false;*/

    }

   /**
     * 后处理方法，controller方法执行后，但是是在页面返回语句 return之前
    * 一般用于逻辑的判断,比如用户是否登录,没有登录的话 就跳转登录页面,就不放心了
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor1执行了...后1111");
        //在后处理这里也可以自己指定跳转的页面,执行了这个跳转页面之后,controller处理方法中的return也就不执行了
        // request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request,response);
    }


   /**
     * controller处理方法中的return语句执行后,该方法会执行,由于页面已经跳转了,所以在afterCompletion里就跳不了页面了
    * 一般可以用于释放资源,比如关闭流等
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("MyInterceptor1执行了...最后1111");
    }

}












