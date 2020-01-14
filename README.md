# SpringMVC_Study04
springmvc中拦截器的使用,其实和spring中AOP相似,都是对方法的增强,拦截器是只对处理方法的拦截,也就是在处理方法执行前后进行一些操作,
只拦截处理方法,这点和serverlet过滤器有所不同,过滤器会拦截所有资源,比如/.jsp等等

这个项目是在基本配置上,加上了对于拦截器的配置,需要在springmvc.xml中添加相关配置,拦截器过程:

1 自定义拦截器需要继承HandlerInterceptor接口,该接口中定义了三个方法,都已有其默认实现:

      1 preHandle(...): 该方法在处理器方法实际执行之前执行
      2 postHandle(...): 该方法在处理器方法实际执行完毕以后执行
      3 afterCompletion(...): 该方法在整个请求处理完成后执行
      其中preHandle(..)方法返回一个boolean值,可以通过这个方法来决定是否继续执行处理链中的部件。当方法返回 true时，
      处理器链会继续执行；若方法返回 false， DispatcherServlet即认为拦截器自身已经完成了对请求的处理（比如说，已经渲染了一个合适的视图），
      那么其余的拦截器以及执行链中的其他处理器就不会再被执行了。
      
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

2 向Spring容器中注入拦截器(在springmvc.xml文件中添加相关配置)

<!--配置拦截器-->
    <mvc:interceptors>
        <!--配置拦截器-->
        <mvc:interceptor>
            <!--要拦截的具体的方法-->
            <mvc:mapping path="/user/*"/>
            <!--不要拦截的方法,上面配置了要拦截的方法,剩下的就是不拦截的,所以这个就不用配了
            <mvc:exclude-mapping path=""/>
            -->
            <!--配置拦截器对象-->
            <bean class="cn.itcast.controller.cn.itcast.interceptor.MyInterceptor1" />
        </mvc:interceptor>

        <!--配置第二个拦截器-->
        <mvc:interceptor>
            <!--要拦截的具体的方法,下面说明是所有的处理方法都要拦截-->
            <mvc:mapping path="/**"/>
            <!--不要拦截的方法
            <mvc:exclude-mapping path=""/>
            -->
            <!--配置拦截器对象-->
            <bean class="cn.itcast.controller.cn.itcast.interceptor.MyInterceptor2" />
        </mvc:interceptor>
    </mvc:interceptors>
    3 处理类方法不需要做任何更改
