# soodagram-springboot(Instagram Clone)
## 프로젝트 소개
스프링 프레임워크를 사용해서 작성한 프로젝트를 스프링부트로 이관 후, 백엔드에 필요한 기능을 수정/보완 해서 개발 중인 프로젝트입니다.
이전 프로젝트는 [Soodagram-spring](https://github.com/wer080/soodagram.git) 에서 확인할 수 있습니다.    
기능 구현을 추가하면서 readme를 업데이트 하겠습니다.

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
