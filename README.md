# soodagram-springboot(Instagram Clone)
## 프로젝트 소개
스프링 프레임워크를 사용해서 작성한 프로젝트를 스프링부트로 이관 후, 백엔드에 필요한 기능을 수정/보완 해서 개발 중인 프로젝트입니다.
이전 프로젝트는 [Soodagram-spring](https://github.com/wer080/soodagram.git) 에서 확인할 수 있습니다.    
기능 구현을 추가하면서 무엇을 추가했는지에 대한 변경점들을 기록을 병행하겠습니다. 

### 스프링에서 스프링부트 이관
큰 변경점은 View의 구성이 JSP 에서 Thymeleaf 로 옮겼습니다.
스프링부트 진영에서 지원하는 템플릿 엔진에서 여러가지가 있지만 Thymeleaf를 중점으로 지원을 하고 있어서 Thymeleaf를 선정했습니다.  
```jsp
<div class="col-md-3">
  <c:set var="eqFlag" value="false" /> 				
  <c:forEach items="${followingList}" var="followingList">
    <c:if test="${followerList.userEmail eq followingList.userEmail && not eqFlag}">
      <c:set var="eqFlag" value="true" />
    </c:if>
  </c:forEach>   				                           
<c:choose>
  <c:when test="${eqFlag}">  
    <button type="button" id="followBtn_${followerList.userEmail}" class="followBtnAcc btn btn-default">팔로잉</button>   
    <c:set var="eqFlag" value="false" />                       					                        			
  </c:when>	
  <c:otherwise>
    <button type="button" id="followBtn_${followerList.userEmail}" class="followBtnAcc btn btn-primary">팔로우</button>
    <c:set var="eqFlag" value="false" />
  </c:otherwise>  	                       			
  </c:choose>	
</div>   
```
기존 JSP + JSTL의 경우 다소 복잡해보이는 코드를 이용해서 구현한 반면,
```html
<div class="col-md-3"> 
   <button th:if="${#lists.contains(followingList, followerList) }" type="button" th:id="'followBtn_' + ${followerList.userEmail}" class="followBtnAcc btn btn-default">팔로잉</button> 
   <button th:unless="${#lists.contains(followingList, followerList) }" type="button" th:id="'followBtn_' + ${followerList.userEmail}" class="followBtnAcc btn btn-primary">팔로우</button>    
</div>
```
Thymeleaf의 경우 템플릿 엔진에서 제공하는 기능을 이용하여 간단하게 구현이 가능했습니다.    
이외의 설정도 xml 기반으로 작성된 파일(root-context.xml, servlet-context.xml, web.xml)은 'application.properties' 및 java 파일로 대체되었습니다.     
<img src="https://user-images.githubusercontent.com/65437310/116869098-0ae16380-ac4b-11eb-940a-991f9017f5aa.png">

### Spring Security 적용
기존 스프링을 이용한 프로젝트는 보안에 관해서는 BCryptPasswordEncoder를 이용해서 암호화 및 복호화를 통해 사용자를 인증, Interceptor를 통한 비로그인 회원의 접근을 차단하는 가장 기본적인
설정으로만 이루어져 있었습니다. 
스프링 부트로 이관하면서, 스프링 시큐리티를 적용하였습니다. 보안 관련된 설정은 Security 폴더에 관리를 하고 있습니다.    
<img src="https://user-images.githubusercontent.com/65437310/122647256-49c96b00-d15e-11eb-9df5-f2477859c342.png">    
로그인에 관한 별도의 커스텀핸들러를 통해 로그인 성공 시, 쿠키를 등록하거나(추후에 로그인 유지 기능 사용), 실패시 해당 원인에 대한 예외를 표시함으로써, 사용자에게 피드백을 줄 수 있도록 구성하였습니다. 
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomLoginSuccessHandler customLoginSuccessHandler;	
	@Autowired
	private CustomLoginFailureHandler customLoginFailureHandler;	
	@Autowired
	private AccountService accountService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().antMatchers("/css/**", "/dist/**", "/fonts/**", "/images/**", "/js/**").permitAll()
								.antMatchers("/login","/regist", "/user/regist/**", "/login/**").permitAll()
								.anyRequest().authenticated()
								.and()
								.formLogin().loginPage("/login").loginProcessingUrl("/login").usernameParameter("userEmail").passwordParameter("userPw")
								.successHandler(customLoginSuccessHandler)
								.failureHandler(customLoginFailureHandler)
								.permitAll()
								.and().logout().logoutSuccessUrl("/login")
								.and().httpBasic();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountService).passwordEncoder(passwordEncoder());
	}	
}
```    
프론트단에서 Ajax 통신에 필요한 csrf 토큰 인증을 같이 보내기 위해서 head 부분에 ajaxPrefilter 를 통해 ajax 통신 전에 같이 csrf 토큰을 보내도록 작성했습니다. 
```html
<meta name="_csrf" th:content="${_csrf.token}"/>
<meta name="_csrf_header" th:content="${_csrf.headerName}" />
<script>
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			
			$.ajaxPrefilter(function(options){
				if(!options.beforeSend) {
					options.beforeSend = function(xhr) {
						xhr.setRequestHeader(header, token);
					}	
				}				
			});
</script>
```

### Mybatis에서 JPA로 변경 (진행중)
기존 스프링에서는 Mybatis를 이용하여 다수의 Mapper 작성으로 DB에 접근하는 쿼리문을 직접 작성했습니다.     
반면 JPA로 변경하면서 기존의 VO 및 DTO 등을 제거, 새롭게 엔티티를 작성 및 DTO를 개편하고 컨트롤러부터 서비스 까지 해당 객체에 맞춰서 전부 새롭게 작성하였습니다. 
이와 관련하여 TDD 에 대한 공부도 병행하기로 계획하여, 각 유닛 테스트를 작성을 시도했습니다. 
