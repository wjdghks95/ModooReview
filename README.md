# 모두의 리뷰

### 🤔아이디어 선정 동기

세상에는 우리가 물건을 하나 사서 그것을 사용하려고 할때 수많은 정보들에 의해 쉽게 결정을 내리기 어려울것이다. 그럴때마다 우리는 먼저 그 물건을 사용해본 사람들의 후기를 참고하여 선택의 폭을 좁히고 결정을 하게 된다. 만약 그런 후기들을 한곳에 모아 많은 사람들과 공유한다면 우리의 선택은 더 빠르고 정확해지지 않을까?

### 🚩목표
내가 내돈으로 직접 주고 산 제품이나 경험과 같은 모든 것들의 좋았던 점 또는 아쉬웠던 점을 사람들과 공유할 수 있는 웹 애플리케이션 개발

### ✏️요구사항
| NO | 요구사항명 | 요구사항 상세설명 | 비고 |
| --- | --- | --- | --- |
| 1 | 회원가입 | - 회원가입시 휴대폰 번호, 이메일 아이디, 비밀번호, 이름, 닉네임, 주소를 입력해야 한다.<br>- 휴대폰 번호를 입력후 sms 인증을 통한 본인확인이 필요하다.<br>- 이메일 아이디는 중복될 수 없다.<br>- 비밀번호는 특수문자를 포함한 문자와 숫자 8~12자로 구성한다.<br>- 닉네임은 중복될 수 없다.<br>- 주소는 우편번호, 주소, 상세주소로 입력하고 상세주소는 입력하지 않아도 된다. | https://coolsms.co.kr/ 를 통한 sms 문자 메시지 발송 |
| 2 | 로그인 (일반 회원) | - 일반 회원의 경우 아이디와 비밀번호로 로그인할 수 있도록 한다. |  |
| 3 | 로그인(소셜 회원) | - 소셜 회원의 경우 [네이버], [카카오], [구글] 3가지 소셜 수단으로 로그인 할 수 있도록 한다. |  |
| 4 | 아이디 찾기 (일반 회원) | - 일반 회원의 경우 아이디 찾기 기능을 제공<br>- 본인 이름, 휴대폰 번호, 이메일 주소를 입력하고 일치하는 경우 아이디 정보 제공 |  |
| 5 | 비밀번호 재설정 (일반 회원) | - 일반 회원의 경우 비밀번호 재설정 기능을 제공<br>- 본인 아이디, 이메일 주소를 입력하고 일치할 경우 비밀번호 재설정 인증 메일 발송<br>- 인증 메일을 통해 비밀번호를 재설정 하도록 함 |  |
| 6 | 검색 | - 키워드를 입력하여 게시글을 검색할 수 있도록 한다. |  |
| 7 | Home - 이달의 리뷰 | - 관리자가 선택한 게시글이 보이도록 한다. |  |
| 8 | Home - 베스트 리뷰 | - 좋아요가 많은 순서로 게시글이 보이도록 한다. |  |
| 9 | Home - 최신순, 인기순 | - 버튼을 클릭하여 게시글을 등록일순, 조회순으로 보이도록 한다.  |  |
| 10 | Contents | - 게시글들은 페이지를 넘겨가며 볼 수 있도록 한다. |  |
| 11 | Contents - 카테고리 | - 카테고리는 [전체], [식품], [미용/코스메틱], [패션/잡화], [출산/육아], [디지털/가전], [스포츠/건강], [반려동물], [맛집], [여행/숙박], [문화], [인테리어], [기타] 로 구성되어 있다.<br>- 카테고리별로 게시글을 볼 수 있도록 한다.<br>- 카테고리는 슬라이드로 넘겨 선택할 수 있도록 한다. |  |
| 12 | Contnets - View | - View 는 [갤러리뷰], [리스트뷰], [보드뷰] 3가지로 구성한다.<br>- [갤러리뷰]는 게시글들의 사진, 제목, 평점을 보여준다.<br>- [리스트뷰]는 게시글들의 번호, 제목, 내용, 평점, 등록일, 수정일을 보여준다.<br>- [보드뷰]는 게시글들의 제목과 태그들을 보여준다. |  |
| 13 | 글쓰기 | - 게시글은 한명의 회원이 여러 게시글을 작성할 수 있다.<br>- 게시글 작성시 사진, 제목, 내용을 입력해야 하고, 카테고리를 선택할 수 있으며, 평점을 부여할 수 있다.<br>- 사진은 한장 또는 여러장의 사진을 등록할 수 있도록 한다.<br>- 사진이 여러장인 경우 슬라이드로 넘겨가며 확인할 수 있도록 한다.<br>- 사진을 여러장 등록하는 경우 썸네일로 사용할 사진을 선택해야한다.<br>- 게시글에 관련된 태그를 추가할 수 있도록 한다. |  |
| 14 | 게시글 - 좋아요 | - 로그인한 유저가 게시글에 좋아요 버튼을 눌러 좋아요한 게시글 목록을 조회할 수 있도록 한다. |  |
| 15 | 게시글 - 댓글 | - 로그인한 유저가 게시글에 댓글을 등록할 수 있도록 한다. |  |
| 16 | 게시글 - 수정, 삭제 | - 게시글을 등록한 유저는 게시글을 수정하거나 삭제할 수 있다. |  |
| 17 | 팔로우 | - 로그인한 유저는 다른 유저를 팔로우 하여 팔로잉 목록을 조회할 수 있도록 한다. |  |
| 18 | 프로필 변경 | - 로그인한 유저는 프로필 페이지에서 자신의 프로필 이미지와 닉네임을 변경할 수 있도록 한다. |  |
| 19 | 회원탈퇴 | - 로그인한 유저는 경고문 확인 후 사이트에서 회원을 탈퇴할 수 있도록 한다. |  |
| 20 | 프로필 확인 | - 모든 유저는 사이트내에 회원으로 등록된 유저들의 프로필을 확인할 수 있도록 한다.<br>- 프로필에서는 회원의 닉네임, 팔로워 수, 팔로잉 수, 회원등급, 등록한 게시글 목록, 좋아요한 목록들을 볼 수 있도록 한다. |  |
| 21 | 회원등급 | - 회원등급은 [브론즈], [실버], [골드], [플래티넘], [다이아]로 구성되어 있다.<br>- 게시글이 10개 이하인 경우 브론즈,<br> 게시글이 50개 이하인 경우 실버,<br> 게시글이 100개 이하인 경우 골드,<br> 게시글이 100개 이상이고 팔로워가 50명 이상인 경우 플래티넘,<br> 게시글이 100개 이상이고 팔로워가 100명 이상인 경우 다이아 등급이 부여된다. |  |

### 💡주요 기능
- ✅ SNS 로그인
- ✅ 게시글 검색 및 카테고리별 분류
- ✅ 팔로우
- ✅ 게시글 좋아요
- ✅ 휴대폰 인증 

### ✔️ERD
<img src=/ERD/Rol_ERD.png>

### 🔨사용기술
<div style="display: flex; align-items: center; margin-bottom: 12px;">
<strong style="display: inline-block; margin-right: 6px;">Frontend: </strong> 
<img src="https://img.shields.io/badge/HTML-E34F26?style=for-the-badge&logo=HTML5&logoColor=white" style="display: inline-block; margin-right: 6px;">
<img src="https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=CSS3&logoColor=white" style="display: inline-block; margin-right: 6px;">
<img src="https://img.shields.io/badge/JAVASCRIPT-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white" style="display: inline-block; margin-right: 6px;">
<img src="https://img.shields.io/badge/jQUERY-0769AD?style=for-the-badge&logo=jQuery&logoColor=white" style="display: inline-block; margin-right: 6px;">
</div>

<div style="display: flex; align-items: center; margin-bottom: 12px;">
<strong style="display: inline-block; margin-right: 6px;">Backend: </strong> 
<img src="https://img.shields.io/badge/JAVA-5283a3?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyBzdHlsZT0iY29sb3I6IHdoaXRlIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAzODQgNTEyIj48IS0tISBGb250IEF3ZXNvbWUgRnJlZSA2LjEuMSBieSBAZm9udGF3ZXNvbWUgLSBodHRwczovL2ZvbnRhd2Vzb21lLmNvbSBMaWNlbnNlIC0gaHR0cHM6Ly9mb250YXdlc29tZS5jb20vbGljZW5zZS9mcmVlIChJY29uczogQ0MgQlkgNC4wLCBGb250czogU0lMIE9GTCAxLjEsIENvZGU6IE1JVCBMaWNlbnNlKSBDb3B5cmlnaHQgMjAyMiBGb250aWNvbnMsIEluYy4gLS0+PHBhdGggZD0iTTI3Ny43NCAzMTIuOWM5LjgtNi43IDIzLjQtMTIuNSAyMy40LTEyLjVzLTM4LjcgNy03Ny4yIDEwLjJjLTQ3LjEgMy45LTk3LjcgNC43LTEyMy4xIDEuMy02MC4xLTggMzMtMzAuMSAzMy0zMC4xcy0zNi4xLTIuNC04MC42IDE5Yy01Mi41IDI1LjQgMTMwIDM3IDIyNC41IDEyLjF6bS04NS40LTMyLjFjLTE5LTQyLjctODMuMS04MC4yIDAtMTQ1LjhDMjk2IDUzLjIgMjQyLjg0IDAgMjQyLjg0IDBjMjEuNSA4NC41LTc1LjYgMTEwLjEtMTEwLjcgMTYyLjYtMjMuOSAzNS45IDExLjcgNzQuNCA2MC4yIDExOC4yem0xMTQuNi0xNzYuMmMuMSAwLTE3NS4yIDQzLjgtOTEuNSAxNDAuMiAyNC43IDI4LjQtNi41IDU0LTYuNSA1NHM2Mi43LTMyLjQgMzMuOS03Mi45Yy0yNi45LTM3LjgtNDcuNS01Ni42IDY0LjEtMTIxLjN6bS02LjEgMjcwLjVhMTIuMTkgMTIuMTkgMCAwIDEtMiAyLjZjMTI4LjMtMzMuNyA4MS4xLTExOC45IDE5LjgtOTcuM2ExNy4zMyAxNy4zMyAwIDAgMC04LjIgNi4zIDcwLjQ1IDcwLjQ1IDAgMCAxIDExLTNjMzEtNi41IDc1LjUgNDEuNS0yMC42IDkxLjR6TTM0OCA0MzcuNHMxNC41IDExLjktMTUuOSAyMS4yYy01Ny45IDE3LjUtMjQwLjggMjIuOC0yOTEuNi43LTE4LjMtNy45IDE2LTE5IDI2LjgtMjEuMyAxMS4yLTIuNCAxNy43LTIgMTcuNy0yLTIwLjMtMTQuMy0xMzEuMyAyOC4xLTU2LjQgNDAuMkMyMzIuODQgNTA5LjQgNDAxIDQ2MS4zIDM0OCA0MzcuNHpNMTI0LjQ0IDM5NmMtNzguNyAyMiA0Ny45IDY3LjQgMTQ4LjEgMjQuNWExODUuODkgMTg1Ljg5IDAgMCAxLTI4LjItMTMuOGMtNDQuNyA4LjUtNjUuNCA5LjEtMTA2IDQuNS0zMy41LTMuOC0xMy45LTE1LjItMTMuOS0xNS4yem0xNzkuOCA5Ny4yYy03OC43IDE0LjgtMTc1LjggMTMuMS0yMzMuMyAzLjYgMC0uMSAxMS44IDkuNyA3Mi40IDEzLjYgOTIuMiA1LjkgMjMzLjgtMy4zIDIzNy4xLTQ2LjkgMCAwLTYuNCAxNi41LTc2LjIgMjkuN3pNMjYwLjY0IDM1M2MtNTkuMiAxMS40LTkzLjUgMTEuMS0xMzYuOCA2LjYtMzMuNS0zLjUtMTEuNi0xOS43LTExLjYtMTkuNy04Ni44IDI4LjggNDguMiA2MS40IDE2OS41IDI1LjlhNjAuMzcgNjAuMzcgMCAwIDEtMjEuMS0xMi44eiIgZmlsbD0id2hpdGUiPjwvcGF0aD48L3N2Zz4=&logoColor=white" style="display: inline-block; margin-right: 6px;">
<img src="https://img.shields.io/badge/SPRING-6DB33F?style=for-the-badge&logo=Spring&logoColor=white" style="display: inline-block; margin-right: 6px;">
<img src="https://img.shields.io/badge/SPRING BOOT-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white" style="display: inline-block; margin-right: 6px;">
<img src="https://img.shields.io/badge/SPRING SECURITY-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white" style="display: inline-block; margin-right: 6px;">
<img src="https://img.shields.io/badge/THYMELEAF-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white" style="display: inline-block; margin-right: 6px;">
<img src="https://img.shields.io/badge/SPRING DATA JPA-6DB33F?style=for-the-badge&logo=&logoColor=white" style="display: inline-block; margin-right: 6px;">
<img src="https://img.shields.io/badge/QUERYDSL-008FC7?style=for-the-badge&logo=&logoColor=white" style="display: inline-block; margin-right: 6px;">
<img src="https://img.shields.io/badge/GRADLE-02303A?style=for-the-badge&logo=Gradle&logoColor=white" style="display: inline-block; margin-right: 6px;">
<img src="https://img.shields.io/badge/JUNIT5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white" style="display: inline-block; margin-right: 6px;">
</div>

<div style="display: flex; align-items: center; margin-bottom: 12px;">
<strong style="display: inline-block; margin-right: 6px;">Database: </strong> 
<img src="https://img.shields.io/badge/MARIADB-003545?style=for-the-badge&logo=MariaDB&logoColor=white" style="display: inline-block; margin-right: 6px;">
</div>

### ⚡Advanced Feature
#### OAuth2.0을 이용한 SNS 로그인
- **SecurityConfig**
    ```java
    @Configuration
    @RequiredArgsConstructor
    public class SecurityConfig {
        private final OAuth2UserService oAuth2UserService;
        
        .and()
        .oauth2Login() // OAuth2기반의 로그인인 경우
        .loginPage("/login") // 인증이 필요한 URL에 접근시 해당 페이지로 이동
        .defaultSuccessUrl("/") // 로그인 성공하면 "/" 으로 이동
        .failureUrl("/login") // 로그인 실패 시
        .userInfoEndpoint() // 로그인 성공 후 사용자 정보를 가져온다
        .userService(oAuth2UserService); //사용자 정보를 처리할 때 사용
    }
    ```

- **CustomOAuth2UserService**: OAuth2UserService 인터페이스를 구현하여 OAuth2 로그인 시 RegistrationId 에 따라 정보를 가지고옴
    ```java
    @Service
    @RequiredArgsConstructor
    public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

        private final PasswordEncoder passwordEncoder;
        private final MemberRepository memberRepository;

        @Override
        public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
            OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService(); // DefaultOAuth2UserService 를 통해 User 정보를 가져와야 하기 때문에 대리자 생성
            OAuth2User oAuth2User = delegate.loadUser(userRequest); // User 정보를 가지고옴

            OAuth2UserInfo oAuth2UserInfo = null; // OAuth2 로그인시 회원정보를 가져오는 인터페이스

            if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
                // 구글 로그인 요청
                oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
            } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
                // 네이버 로그인 요청
                oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
            } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
                // 카카오 로그인 요청
                oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
            }

            ...

            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(member.getRole().value()));

            return new MemberContext(member, roles, oAuth2User.getAttributes());
        }
    }
    ```

- **MemberContext**: UserDetails 인터페이스를 구현한 MemberContext에 OAuth2User 인터페이스를 다중 상속 하여 일반 유저와 OAuth2 로그인 유저의 정보를 가지고 오는 객체를 통합

    ```java
    @Getter
    public class MemberContext implements UserDetails, OAuth2User {

        private Member member;
        private List<GrantedAuthority> roles;
        private Map<String, Object> attributes;

        public MemberContext(Member member, List<GrantedAuthority> roles) {
            this.member = member;
            this.roles = roles;
        }

        public MemberContext(Member member, List<GrantedAuthority> roles, Map<String, Object> attributes) {
            this.member = member;
            this.roles = roles;
            this.attributes = attributes;
        }

        @Override
        public Map<String, Object> getAttributes() {
            return attributes;
        }

        // 계정의 권한 목록
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return roles;
        }

        // 계정의 비밀번호
        @Override
        public String getPassword() {
            return member.getPassword();
        }

        // 계정의 고유한 값
        @Override
        public String getUsername() {
            return member.getEmail();
        }

        // 계정의 만료 여부
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        // 계정의 잠김 여부
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        // 비밀번호 만료 여부
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        // 계정의 활성화 여부
        @Override
        public boolean isEnabled() {
            return true;
        }

        // 계정의 고유한 값
        @Override
        public String getName() {
            return member.getEmail();
        }
    }
    ```

- **OAuth2UserInfo**: OAuth2 로그인시 회원정보를 가져오는 인터페이스
    ```java
    public interface OAuth2UserInfo {
        String getPhone();
        String getEmail();
        String getName();
        String getNickname();
        String getZipcode();
        String getAddress();
        String getDetailAddress();
        String getPicture();
    }
    ```

- GoogleUserInfo
    
    ```java
    public class GoogleUserInfo implements OAuth2UserInfo{
    
        private Map<String, Object> attributes;
    
        public GoogleUserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
        }
    
        @Override
        public String getPhone() {
            return null;
        }
    
        @Override
        public String getEmail() {
            return (String) attributes.get("email");
        }
    
        @Override
        public String getName() {
            return (String) attributes.get("name");
        }
    
        @Override
        public String getNickname() {
            return null;
        }
    
        @Override
        public String getZipcode() {
            return null;
        }
    
        @Override
        public String getAddress() {
            return null;
        }
    
        @Override
        public String getDetailAddress() {
            return null;
        }
    
        @Override
        public String getPicture() {
            return (String) attributes.get("picture");
        }
    }
    ```
- NaverUserInfo
    ```java
    public class NaverUserInfo implements OAuth2UserInfo {
        private Map<String, Object> attributes;
    
        public NaverUserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
        }
    
        @Override
        public String getPhone() {
            return (String) attributes.get("mobile");
        }
    
        @Override
        public String getEmail() {
            return (String) attributes.get("email");
        }
    
        @Override
        public String getName() {
            return (String) attributes.get("name");
        }
    
        @Override
        public String getNickname() {
            return (String) attributes.get("nickname");
        }
    
        @Override
        public String getZipcode() {
            return null;
        }
    
        @Override
        public String getAddress() {
            return null;
        }
    
        @Override
        public String getDetailAddress() {
            return null;
        }
    
        @Override
        public String getPicture() {
            return (String) attributes.get("profile_image");
        }
    }
    ```
- KakaoUserInfo
    ```java
    public class KakaoUserInfo implements OAuth2UserInfo{
    
        private Map<String, Object> attributes;
    
        public KakaoUserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
        }
    
        @Override
        public String getPhone() {
            return null;
        }
    
        @Override
        public String getEmail() {
            Map<String, Object> kakaoAccount = getKakaoAccount();
            return (String) kakaoAccount.get("email");
        }
    
        @Override
        public String getName() {
            return null;
        }
    
        @Override
        public String getNickname() {
            Map<String, Object> kakaoAccount = getKakaoAccount();
            Map<String, Object> profile = getProfile(kakaoAccount);
            return (String) profile.get("nickname");
        }
    
        @Override
        public String getZipcode() {
            return null;
        }
    
        @Override
        public String getAddress() {
            return null;
        }
    
        @Override
        public String getDetailAddress() {
            return null;
        }
    
        @Override
        public String getPicture() {
            Map<String, Object> kakaoAccount = getKakaoAccount();
            Map<String, Object> profile = getProfile(kakaoAccount);
            return (String) profile.get("profile_image_url");
        }
    
        private Map<String, Object> getProfile(Map<String, Object> kakaoAccount) {
            return (Map<String, Object>) kakaoAccount.get("profile");
        }
    
        private Map<String, Object> getKakaoAccount() {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            return kakaoAccount;
        }
    }
    ```
  
#### PersistentTokenBasedRememberMeServices 방식 Remember-me 로그인 구현
- **SecurityConfig**
    ```java
    // Remember-me
    .and()
    .rememberMe()
    .tokenValiditySeconds(3600) // 쿠키 만료 시간 (Default 14일)
    .rememberMeServices(rememberMeServices(tokenRepository())); // PersistentTokenBasedRememberMeServices (DB 저장 방식) 등록

    // 사용자 정의 RememberMeServices
    public RememberMeServices rememberMeServices(PersistentTokenRepository tokenRepository) {
        return new FormRememberMeService("rememberMeKey", userDetailsService, tokenRepository);
    }

    // PersistentTokenBasedRememberMeServices 를 위한 저장소
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource); // DataSource 설정
        return jdbcTokenRepository;
    }
    ```
- **PersistentTokenBasedRememberMeServices** : DB에 저장된 토큰과 사용자가 request header에 담아서 보낸 토큰을 비교하여 인증 수행 (영구적인 방법)
    ```java
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource); // DataSource 설정
        return jdbcTokenRepository;
    }
    ```
- **JdbcTokenRepositoryImpl**: PersistentTokenRepository 인터페이스를 구현하고 있는 클래스
    - JdbcTokenRepositoryImpl 는 내부적으로 DB 테이블(persistent_logins)을 이용하는데, 이 테이블은 수동으로 만들어주어야함
    - JPA를 사용하기 때문에 직접 만들지 않고, JPA 객체로 정의 **(PersistentLogins)**
        ```java
        @Entity
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public class PersistentLogins {

            @Id
            @Column(length = 64)
            private String series;

            @Column(length = 64)
            private String username;

            @Column(length = 64)
            private String token;

            @Column(name = "last_used", length = 64)
            private Date lastUsed;
        }
        ```

### 🔥개선사항
- AOP를 이용한 보안, 예외처리
- 지연로딩과 조회 성능 최적화
- 관리자 페이지 (이달의 리뷰)