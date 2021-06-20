# Spring Boot Project   
Spring Boot를 활용하여 Product에 대한 CRUD 메소드를 제공하는 REST API를 구현  

<br>

# 목차
- [데이터베이스 설정](#데이터베이스-설정)  
  
- [Rest API Method](#Rest-API-Method)  
  
- [API Request and Response Message](#API-Request-and-Response-Message)  
  
- [Product REST API URL Mapping Info](#Product-REST-API-URL-Mapping-Info)  
  
<br>


# 데이터베이스 설정

#### Database 이름

:dolphin: **MySQL Workbench**

<img width="218" alt="db_table" src="https://user-images.githubusercontent.com/43202607/85773632-09a4f780-b759-11ea-82f2-c75c33600363.png">

<br>

* **`helloProductDB-1771015`** 이름의 새로운 스키마 생성

<br>

:page_facing_up: **application.properties**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/helloProductDB-1771015?characterEncoding=UTF-8&serverTimezone=Asia/Seoul
spring.datasource.username=root
spring.datasource.password=csedbadmin
```

* 설정 파일에 데이터베이스 정보 추가
  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; spring.datasource.url=jdbc:mysql://localhost:3306/**`helloProductDB-1771015`**?characterEncoding=UTF-8&serverTimezone=Asia/Seoul

***
 
#### Product Table 이름

:page_facing_up: **Product.java**

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name = "productTable")
public class Product implements Serializable {

	/**
	 * send to client side in JSON format
	 **/
	private static final long serialVersionUID = -8613449854766783122L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productId")
	private int id;

	@NonNull
	private String name;

	@NonNull
	private String category;

	private int price;

	@NonNull
	private String manufacturer;

	private int unitInStock;

	private String description;

	public Product(Product product) {
		this.name = product.name;
		this.category = product.category;
		this.price = product.price;
		this.manufacturer = product.manufacturer;
		this.unitInStock = product.unitInStock;
		this.description = product.description;
	}

}
```

* Product 클래스에 **`@Table(name="productTable')`** 어노테이션 추가

* Hibernate에 의해 Product 객체가 데이터베이스 테이블로 매핑

<br>

:page_facing_up: **application.properties**

```properties
# always create new tables
spring.jpa.hibernate.ddl-auto	=create

spring.datasource.initialization-mode=always

# to write field names and table names in 'Camel Case' in the database
spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

* 웹 애플리케이션이 실행될때마다 데이터베이스를 초기화하고 테이블을 새로 생성

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **`spring.jpa.hibernate.ddl-auto	=create`**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **`spring.datasource.initialization-mode=always`**

* 별도의 설정 없이 Product에 대한 테이블 이름을 **`@Table(name='productTable')`** 어노테이션으로 설정했을 경우, 데이터베이스에 스네이크 표기법(Snake Case)으로 테이블 이름 부여

* @Table 어노테이션의 속성으로 준 이름과 동일하게 카멜 표기법(Camel Case)을 따른 이름으로 테이블이 생성되도록 설정

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **`spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl`**

<br>

:dolphin: **MySQL Workbench**

<img width="219" alt="db_table" src="https://user-images.githubusercontent.com/43202607/85777819-eda35500-b75c-11ea-9ab1-516cde9ae11d.png">

* 애플리케이션 실행 후, 생성된 테이블 확인

***

#### **data.sql를 이용하여 미리 10개 이상의 Product 생성**

:page_facing_up: **data.sql**

```sql
insert into productTable(name, category, price, manufacturer, unitInStock, description) values ("MacBook Pro", "computer", 2399, "Apple", 100, "Power. Moves.")
insert into productTable(name, category, price, manufacturer, unitInStock, description) values ("MacBook Air", "computer", 999, "Apple", 200, "Light. Speed.")
insert into productTable(name, category, price, manufacturer, unitInStock, description) values ("iMac", "computer", 2299, "Apple", 150, "Pretty. Freaking powerful.")
insert into productTable(name, category, price, manufacturer, unitInStock, description) values ("Galaxy Book Fles", "computer", 450, "Samsung", 300, "QLED brilliance in a powerful 2-in-1 PC")
insert into productTable(name, category, price, manufacturer, unitInStock, description) values ("Odyssey Monitor", "appliance", 1199, "Samsung", 250,  "Wider View. Winning Play")
insert into productTable(name, category, price, manufacturer, unitInStock, description) values ("French Door Refrigerator", "appliance", 2699, "Samsung", 50, "It’s all on your fridge")
insert into productTable(name, category, price, manufacturer, unitInStock, description) values ("SmartThings Cam", "appliance", 89, "Samsung", 10, "Full HD camera monitors your home for 24 hours.")
insert into productTable(name, category, price, manufacturer, unitInStock, description) values ("QLED", "appliance", 9883, "Samsung", 5, "8K display")
insert into productTable(name, category, price, manufacturer, unitInStock, description) values ("Nike Free Run 5.0", "sundries", 90, "NIKE", 500, "Running Shoes")
insert into productTable(name, category, price, manufacturer, unitInStock, description) values ("nike air zoom pegasus 37", "sundries", 115, "NIKE", 500, "Speed Shoes")
```

<br>

:dolphin: **MySQL Workbench**

<img width="1397" alt="records" src="https://user-images.githubusercontent.com/43202607/85778739-d9138c80-b75d-11ea-887e-f174e08fe28d.png">

***

<br><br><br>

# Rest API Method

### :information_source: Main URL: http://localhost:9090/api/v1/

| HTTP Method | URL | Operation |
|:---|:---|:---|
| `POST` | http://localhost:9090/api/v1/products | Create new product |
| `GET` | http://localhost:9090/api/v1/products | Get full list of products |
| `GET` | http://localhost:9090/api/v1/products/{id} | Get details of products with id=N |
| `GET` | http://localhost:9090/api/v1/products/category/{category} | Fetch all products of a category |
| `PUT` | http://localhost:9090/api/v1/products/{id} | Modify values of product with id=N |
| `DELETE` | http://localhost:9090/api/v1/products/{id} | Delete product with id=N |

***

<br><br><br>

# API Request and Response Message

### Create new product

:arrow_right: **Request**

<img width="1400" alt="create_req" src="https://user-images.githubusercontent.com/43202607/85836045-f9c8fa00-b7d0-11ea-99e8-3667c01848a3.png">

* HTTP `POST` `http://localhost:9090/api/v1/products` URL로 Request 전송

* 추가하고자 하는 Product의 정보를 Request Message의 Body 부분에 JSON 형식으로 작성

:balloon: Request에 작성된 JSON 형식의 상품 정보는 서버측에서 **`역직렬화(Deserialization)`** 하여 자바 객체 형태로 처리하고 데이터베이스에 저장

<br><br>

:arrow_left: **Response Message**

<img width="1400" alt="create_resp" src="https://user-images.githubusercontent.com/43202607/85836206-3f85c280-b7d1-11ea-93d7-02bc006538dc.png">

* 추가된 상품이 정상적으로 데이터베이스에 저장되었을 경우, 상태 코드 `201 Created`와 Body에 `JSON 형식의 추가된 상품 내용`이 리턴

<br><br>

:dolphin: **MySQL Workbench**

<img width="1175" alt="create-result" src="https://user-images.githubusercontent.com/43202607/85837965-c76ccc00-b7d3-11ea-8145-618ca4f72609.png">

* 요청 후 데이터베이스에서 추가된 상품을 확인 가능

<br><br>

***

### Get full list of products

:arrow_right: **Request**

<img width="1400" alt="full_req" src="https://user-images.githubusercontent.com/43202607/85836619-d783ac00-b7d1-11ea-9100-e87d5068ded3.png">

* HTTP `GET` `http://localhost:9090/api/v1/products` URL로 Request 전송

<br><br>

:arrow_left: **Response Message**

<img width="1400" alt="full-resp" src="https://user-images.githubusercontent.com/43202607/85836887-47923200-b7d2-11ea-98d4-57e969f93a59.png">

<img width="1400" alt="full-resp2" src="https://user-images.githubusercontent.com/43202607/85836901-4e20a980-b7d2-11ea-8b07-8e590e1c3770.png">

<img width="1400" alt="full-resp3" src="https://user-images.githubusercontent.com/43202607/85836907-51b43080-b7d2-11ea-8eaf-73df93b436a8.png">

* 요청이 처리되었을 경우 상태 코드 `200 OK` 와 Body에 `JSON 형식의 모든 상품 내용`이 리턴

:balloon: 서버측에서는 데이터베이스에서 조회한 레코드를 자바 객체로 처리한 후 클라이언트에게 응답할 때 객체를 **`직렬화(Serialization)`** 하여 전송

<br><br>

***

### Get details of products with id=N

:arrow_right: **Request**

<img width="1400" alt="product-req" src="https://user-images.githubusercontent.com/43202607/85837235-c12a2000-b7d2-11ea-884b-d16332448989.png">

* HTTP `GET` `http://localhost:9090/api/v1/products/2` URL로 Request 전송

* `id=2`인 상품을 조회

<br><br>

:arrow_left: **Response Message**

<img width="1400" alt="product-resp" src="https://user-images.githubusercontent.com/43202607/85837253-c9825b00-b7d2-11ea-99dc-f69c85418a41.png">

* 요청이 처리되었을 경우 상태 코드 `200 OK`와 Body에 `JSON 형식의 id가 2인 상품 내용`이 리턴

<br><br>

:dolphin: **MySQL Workbench**

<img width="1175" alt="product_res" src="https://user-images.githubusercontent.com/43202607/85837845-94c2d380-b7d3-11ea-99cb-e958b36a942c.png">

* 데이터베이스에 저장된 id가 2인 상품과 요청에 대한 응답으로 받은 상품 정보가 동일하다는 것을 확인

<br><br>

***

### Fetch all products of a category

:arrow_right: **Request**

<img width="1400" alt="cate-req" src="https://user-images.githubusercontent.com/43202607/85837567-2716a780-b7d3-11ea-9c6a-260059a50f7b.png">

* HTTP `GET` `http://localhost:9090/api/v1/products/category/appliance` URL로 Request 전송

* `category=appliance`인 상품들을 모두 조회

<br><br>

:arrow_left: **Response Message**

<img width="1400" alt="cate-resp" src="https://user-images.githubusercontent.com/43202607/85837606-3138a600-b7d3-11ea-9d35-0fd72070af3e.png">

* 요청이 처리되었을 경우 상태 코드 `200 OK` 와 Body에 `JSON 형식의 카테고리가 "appliance" 상품들의 내용`이 리턴

<br><br>

:dolphin: **MySQL Workbench**

<img width="1175" alt="cate-result" src="https://user-images.githubusercontent.com/43202607/85838085-fd11b500-b7d3-11ea-89a3-d8569ba3e764.png">

* 데이터베이스에 저장된 category가 appliance인 상품들과 요청에 대한 응답으로 받은 상품들의 정보가 동일하다는 것을 확인

<br><br>

***

### Modify values of product with id=N

:arrow_right: **Request**

<img width="1400" alt="modi-req" src="https://user-images.githubusercontent.com/43202607/85838531-af497c80-b7d4-11ea-802b-ece2909d653f.png">

* HTTP `PUT` `http://localhost:9090/api/v1/products/8` URL로 Request 전송

* `id=8`인 상품의 **price** 값을 **9883**에서 **8000**으로 수정

<br><br>

:arrow_left: **Response Message**

<img width="1400" alt="modfi-resp" src="https://user-images.githubusercontent.com/43202607/85838533-afe21300-b7d4-11ea-9264-1d7828a63b5f.png">

* 요청이 처리되었을 경우 상태 코드 `200 OK` 와 Body에 `JSON 형식의 id가 8인 상품이 수정된 내용`이 리턴

<br><br>

:dolphin: **MySQL Workbench**

**Before Request**

<img width="1175" alt="pre-modify" src="https://user-images.githubusercontent.com/43202607/85838519-ab1d5f00-b7d4-11ea-865a-b41d6e67e3e5.png">

<br>

**After Request**

<img width="1175" alt="after-modi" src="https://user-images.githubusercontent.com/43202607/85838535-afe21300-b7d4-11ea-89e6-54c51cee7962.png">

* 요청 후 데이터베이스에서 id가 8인 상품의 가격이 수정되었음을 확인

<br><br>

***

### Delete product with id=N

:arrow_right: **Request**

<img width="1400" alt="del-req" src="https://user-images.githubusercontent.com/43202607/85851515-c8105d00-b7e9-11ea-9918-2804817be9fd.png">

* HTTP `DELETE` `http://localhost:9090/api/v1/products/3` URL로 Request 전송

* `id=3`인 상품을 삭제

<br><br>

:arrow_left: **Response Message**

<img width="1400" alt="del-resp" src="https://user-images.githubusercontent.com/43202607/85851518-c8105d00-b7e9-11ea-8289-a4d60f9efdba.png">

* 요청이 처리되었을 경우 상태 코드 `204 No Content`가 리턴

<br><br>

:dolphin: **MySQL Workbench**

**Before Request**

<img width="1175" alt="before-del" src="https://user-images.githubusercontent.com/43202607/85851505-c47cd600-b7e9-11ea-8007-6269b17ef234.png">

<br>

**After Request**

<img width="1175" alt="after-del" src="https://user-images.githubusercontent.com/43202607/85851521-c8a8f380-b7e9-11ea-8eb2-5c822d42a06f.png">

* 데이터베이스에서 id가 3인 상품이 삭제되었음을 확인

<br><br>

***

<br><br><br>

# Product REST API URL Mapping Info

:page_facing_up: **pom.xml**

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>			
</dependency>
```

* **`pom.xml`** 에 Actuator 라이브러리를 추가

* REST API를 이용하여 애플리케이션을 모니터링하고 추적 가능

<br><br>

:page_facing_up: **application.properties**

```properties
# active Spring Boot Actuator
management.endpoints.web.exposure.include=mappings
```

* 메핑 기능을 활성화하기 위해 **`application.properties`** 에 설정 정보 추가

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; **`management.endpoints.web.exposure.include=mappings`** 

* 애플리케이션의 URL 조회 가능

<br><br>

:star2: **URL 확인**

<img width="1680" alt="mapping-header" src="https://user-images.githubusercontent.com/43202607/85857356-a9639380-b7f4-11ea-9153-88e793ae0d8b.png">

<img width="1680" alt="mapping3" src="https://user-images.githubusercontent.com/43202607/85855829-d82c3a80-b7f1-11ea-86e1-2ee56e8858d4.png">

<img width="1680" alt="mapping2" src="https://user-images.githubusercontent.com/43202607/85855863-e5492980-b7f1-11ea-9f2e-b1f477dc8b87.png">

<img width="1680" alt="mapping1" src="https://user-images.githubusercontent.com/43202607/85855872-e712ed00-b7f1-11ea-8ba7-858a788b58a1.png">


* 애플리케이션에 정의된 Request Mapping 정보 조회

* URL을 처리하는 컨트롤러와 메서드 정보를 조회 가능

<br><br>



