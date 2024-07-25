# 1. Swagger
 * [Swagger UI 바로가기](https://august-cascade-385602.uw.r.appspot.com/swagger-ui/index.html)



# 2. Database Replication 설정
   - Database Replication은 실시간 복제본 데이터베이스 서버를 운용하는 것을 의미함
   - 기준이 되는 서버가 Master 서버 복제된 서버를 Replica 서버라 칭함
   - Replica 서버는 읽기 전용 속성으로 운영되며 이를 이용해 readonly 여부에 따라 DataSource 를 변경하여 DB Connection을 변경함
   - @Transactional annotaion의 readonly 속성을 사용하여 read/write이면 Master로 readonly인 경우 Slave로 DataSource를 결정해서 connection 함

> 관련 소스
   - [application.yml](https://github.com/playground-PLYG/playground/blob/master/src/main/resources/application.yml#L25)
   : master/slave jdbc 관련 설정
   - [DataSourceConfig.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/config/DataSourceConfig.java)
   : yaml 파일에 설정한 jdbc 설정 정보로 DataSource를 반환하는 bean 생성
   - [RoutingDataSource.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/config/RoutingDataSource.java)
   : Transaction의 readonly여부에 따라 master/slave 판단
   - [RoutingDataSourceConfig.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/config/RoutingDataSourceConfig.java)
   : LazyConnection으로 RoutingDataSource에서 판단된 DataSource로 connection



# 3. secret manager
   - git, svn 등등 형상 관리 되는 프로젝트의 yaml, properties에 서버의 중요 정보가 노출되어있음
   - yaml, properties에 적용 돼야 할 중요 정보를 별도의 영역인 cloud에서 관리하고 application runtime 시점에 해당 정보를 조회함
   - Google Cloud Platform (GCP), Amazon Web Services (AWS) 의 Secert Manager, Vault 등이 있음
   - GCP의 Secret Manager를 적용함
   - application.yml의 spring.cloud.gcp.secretmanager.credentials.location 설정이 적용되지 않아서 추후 확인 및 테스트 필요
   - 임시로 GOOGLE_APPLICATION_CREDENTIALS 시스템 변수 설정이 필요함
     - Confluence에 개발 환경 - Google Secret Manager 환경 변수 참조

> 관련 소스
   - [application.yml](https://github.com/playground-PLYG/playground/blob/master/src/main/resources/application.yml#L21)
   : ${sm://jdbc-url} 와 같은 형태로 사용
   - [build.gradle](https://github.com/playground-PLYG/playground/blob/master/build.gradle#L40)
   : spring-cloud-gcp-starter, spring-cloud-gcp-starter-secretmanager

> 참고 링크
   - <https://spring-gcp.saturnism.me/app-dev/cloud-services/secret-management>
   - <https://codelabs.developers.google.com/codelabs/cloud-spring-cloud-gcp-secret-manager#1>



# 4. 로그
   - Spring boot에는 logback이 기본으로 설정되어있음
   - 로그 추적을 위해 JwtFilter에서 userId가 있는 경우 MDC에 put하고 application.yml의 log formatter에 userId 찍도록 추가
   - 로그에 표출되지 말아야 할 민감정보를 masking 처리해서 로그에 표출하도록 BaseEntity, BaseDto에서 ToString 구현
   - 모든 Entity나 DTO/VO들은 BaseEntity, BaseDto를 상속받아야 함


> 관련 소스
   - [JwtFilter.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/filter/JwtFilter.java#L67)
   : MDC 셋팅
   - [application.yml](https://github.com/playground-PLYG/playground/blob/master/src/main/resources/application.yml#L59)
   : default pattern에 userId 추가
   - [BaseEntity.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/entity/BaseEntity.java)
   : 민감정보 로그에 표출되지 않도록 마스킹 처리를 위해 ToString Override
   - [BaseDto.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/model/BaseDto.java)
   : 민감정보 로그에 표출되지 않도록 마스킹 처리를 위해 ToString Override



# 5. Redis
   - Redis는 인메모리 데이터 구조 저장소로서 사용
   - Spring Data Redis에서 제공하는 RedisTemplate을 사용하는 방법과 RedisRepository 인터페이스를 사용하는 방법이 있음
     - RedisRepository의 장점
         - 간편한 사용
             - RedisRepository는 Spring Data Redis 프로젝트의 일부로 제공
             - Redis와의 상호 작용을 단순화시켜줍니다. CRUD (생성, 읽기, 업데이트, 삭제) 작업을 수행하는 메서드를 제공하므로 Redis와의 상호 작용이 간단하고 직관적
         - 자동 매핑
             - RedisRepository는 객체와 Redis 사이의 자동 매핑을 제공 객체를 Redis에 저장하거나 Redis에서 가져올 때, 자동으로 직렬화 및 역직렬화를 처리하여 개발자가 추가적인 작업을 할 필요 없음
     - RedisRepository의 단점
         - 제한된 기능
             - RedisRepository는 간단한 CRUD 작업을 처리하는 데 유용하지만, 고급 Redis 기능을 사용하려는 경우에는 제한적일 수 있음
             - 더 복잡한 Redis 작업을 수행해야하는 경우에는 RedisTemplate을 사용하는 것이 더 적합
     - RedisTemplate의 장점
         - 유연성
             - RedisTemplate은 Redis의 모든 기능을 사용할 수 있도록 다양한 메서드를 제공
             - Redis의 데이터 유형에 따라 다양한 작업을 수행할 수 있으며, 개발자가 필요한 작업을 직접 구현할 수 있음
         - 고급 기능
             - RedisTemplate은 Redis의 pub/sub (게시/구독) 메커니즘, 트랜잭션, 파이프라이닝 등과 같은 고급 기능을 지원하며 이러한 기능을 사용하여 더 복잡한 Redis 작업을 수행할 수 있음
     - RedisTemplate의 단점
         - 사용의 복잡성
             - RedisTemplate은 더 많은 유연성을 제공하지만, 사용의 복잡성도 높을 수 있음
             - 개발자는 직접 Redis 데이터 유형 및 직렬화에 대한 처리를 구현해야 하므로, 더 많은 작업과 코드를 작성해야 함
         - 성능 고려 사항
             - RedisTemplate은 개발자가 직접 코드를 작성하므로, 최적화 및 성능 고려 사항을 고려해야 할 수 있음
             - Redis의 성능을 최대로 활용하기 위해 RedisTemplate을 사용할 때는 성능 테스트와 최적화가 필요할 수 있음



> 관련 소스
   - [application-local.yml](https://github.com/playground-PLYG/playground/blob/master/src/main/resources/application-local.yml#L18)
   : redis 접속 정보 셋팅
   - [RedisConfig.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/config/RedisConfig.java)
   : 기본 client로 Lettuce를 사용하기 위해 LettuceConnectionFactory를 활용해 redis 접속 환경 구성
   - [CacheConfig.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/config/CacheConfig.java)
   : redis를 Cache로 활용하기 위해 CacheManager 설정 구성
   - [RedisController.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/api/sample/RedisController.java)
   : redis 활용 샘플 api
   - [MemberService.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/api/playground/service/playgroundService.java#L88)
   : redis 활용 캐싱 api




# 6. Excel 다운로드
   - Excel 다운로드를 위해 Apache POI를 사용함
   - Workbook은 SXSSFWorkbook을 사용함
   - SXSSFWorkbook은 스트림 기반으로 동작하며 데이터를 메모리에 모두 로드하지 않고 생성자 생성 시 설정 한 rowAccessWindowSize만 메모리에서 작업을 수행하기 때문에 Out of Memory(OOM)를 방지함
   - rowAccessWindowSize가 넘어가는 경우 임시 파일에 flush 처리를 함
   - Mybatis를 사용 할 경우 Mybatis에서 제공하는 ResultHandler를 활용하면 전체 데이터의 List<VO>를 생성하지 않기 때문에 성능상의 이점이 있음


> 관련 소스
   - [ExcelDown.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/annotation/ExcelDown.java)
   : Excel 다운로드를 위해 해더, width, 순서 등 설정
   - [ExcelDownUtil.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/utils/ExcelDownUtil.java)
   : Excel 다운로드를 처리를 하는 Util
   - [ExcelController.java](https://github.com/playground-PLYG/playground/blob/master/src/main/java/com/playground/api/sample/ExcelController.java)
   : Excel 다운로드를 샘플 api







------------------------------------------------------------


> DB접속정보 등 기타 정보는 Confluence 개발환경 페이지에 기록함









