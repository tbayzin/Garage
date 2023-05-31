No Database or additional configuration needed. 
Endpoints can be tested either Postman or Swagger UI.
Postman Collection can be find <a href="https://drive.google.com/file/d/1iQSnp6GW5yNkuHI4exyWRt4hs7KCWcpA/view?usp=sharing">here</a> and import into Postman as a collection.

<b> GET</b> - http://localhost:8082/api/v1/status

<b> POST</b> - http://localhost:8082/api/v1/park  <br>

```json
{
  "color": "red",
  "type": "jeep",
  "plateNumber": "34TK200"
}
```
 <br>

<b> DELETE</b> - http://localhost:8082/api/v1/leave/06ABC340

<b>Swagger Url:</b>  http://localhost:8082/swagger-ui/index.html

![image](https://raw.githubusercontent.com/tbayzin/Garage/master/sw.png)


Java 17  <br>
Maven   <br>
Junit   <br>
Swagger  <br>
Lombok  <br>  <br>


Below stands demonstration of couple requests.

![image](https://raw.githubusercontent.com/tbayzin/Garage/master/1.png)    <br>

![image](https://raw.githubusercontent.com/tbayzin/Garage/master/2th.png)  <br>



